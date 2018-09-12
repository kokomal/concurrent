package yuanjun.chen.misc;

import java.util.concurrent.locks.LockSupport;
import org.junit.Test;
import yuanjun.chen.common.BadPractice;

public class TestObjWait {

    /**
     * @Title: testIllegalMonitorState
     * @Description: 如果不进行同步操作就调用wait/notify方法必然报监视器异常
     * @throws Exception
     * @return: void
     */
    @Test
    public void testIllegalMonitorState() throws Exception {
        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    obj.wait(); // 需要同步
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sum);
            }
        });
        A.start();
        // 睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        obj.notify();
    }

    @Test
    public void testNoIllegalMonitorState() throws Exception {
        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    synchronized (obj) {
                        obj.wait(); // 需要同步
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sum);
            }
        });
        A.start();
        // 睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        synchronized (obj) {
            obj.notify();
        }
    }

    /**
     * @Title: testLockSupport
     * @Description: 采用LockSupport挂起本线程
     * @throws Exception
     * @return: void
     */
    @Test
    public void testLockSupport() throws Exception {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });
        A.start();
        // 睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        LockSupport.unpark(A);
    }


    @Test
    @BadPractice
    public void testHang() throws Exception {
        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    synchronized (obj) {
                        System.out.println("begin to wait");
                        obj.wait(2000); // 这里的wait会hang住
                        System.out.println("wait over");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sum);
            }
        });
        Thread.sleep(1000);
        A.start();
        synchronized (obj) { // 大概率notify在先，wait在后
            System.out.println("begin to notify");
            obj.notify();
        }
    }

    @Test
    public void testNoHang() throws Exception {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running---");
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });
        A.start();
        LockSupport.unpark(A);
    }
    
    public static void main(String[] args) throws Exception {
       
    }
}

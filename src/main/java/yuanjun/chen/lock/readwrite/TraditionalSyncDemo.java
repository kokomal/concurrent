/**
 * @Title: TraditionalSyncDemo.java
 * @Package: yuanjun.chen.lock.readwrite
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月15日 下午2:39:56
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.lock.readwrite;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: TraditionalSyncDemo
 * @Description: 传统synchronized不能实现同时读写
 * @author: 陈元俊
 * @date: 2018年8月15日 下午2:39:56
 */
public class TraditionalSyncDemo {

    /**
     * @Title: testSerialSyncRead
     * @Description: 串行用sync方式读取 ，排他
     * @throws Exception
     */
    public static void testSerialSyncRead(Pie pie, int len) throws Exception {
        Thread[] ts = new Thread[len];
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            ts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (pie) {
                        try {
                            pie.watch();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            ts[i].start();
        }
        for (int i = 0; i < len; i++) {
            ts[i].join();
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("all done and the time is " + (t2 - t1) + "ms");
    }

    public static void main(String[] args) throws Exception {
        Pie pie = new Pie("tasty");
        testReadWriteLock(pie, 5);
        // testSerialSyncRead(pie, 5);
    }

    /**
     * @Title: testReadWriteLock
     * @Description: 用读写锁进行并行读 读写互斥，读读共享
     * @throws Exception
     * @return: void
     */
    public static void testReadWriteLock(Pie pie, int len) throws Exception {
        ReadWriteLock rwl = new ReentrantReadWriteLock(); // 可重入读写锁
        Thread[] ts = new Thread[len];
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            ts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        rwl.readLock().lock();
                        pie.watch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        rwl.readLock().unlock();
                    }
                }
            });
            ts[i].start();
        }
        for (int i = 0; i < len; i++) {
            ts[i].join();
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("all done and the time is " + (t2 - t1) + "ms");
    }
}

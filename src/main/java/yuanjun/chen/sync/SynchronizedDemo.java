/**
 * @Title: SynchronizedDemo.java
 * @Package: yuanjun.chen.sync
 * @Description: java内置锁的demo
 * @author: 陈元俊
 * @date: 2018年7月26日 下午12:27:08
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

/**
 * @ClassName: SynchronizedDemo
 * @Description: java内置锁的demo
 * @author: 陈元俊
 * @date: 2018年7月26日 下午12:27:08
 */
public class SynchronizedDemo {
    private static final Logger logger = Logger.getLogger(SynchronizedDemo.class);
    public static enum SyncWay {
        SYNC_METHOD, SYNC_MEMBER, SYNC_OBJECT, SYNC_CLASS;
    }
    public static class PiePlate implements Runnable {
        private Integer pieNumber;
        private SyncWay syncWay;
        private Object commonLock = new Object();

        public PiePlate(Integer pieNumber, SyncWay syncWay) {
            super();
            this.pieNumber = pieNumber;
            this.syncWay = syncWay;
        }

        public void run() {
            switch (this.syncWay) {
                case SYNC_METHOD: {
                    eat1();
                    break;
                }
                case SYNC_MEMBER: {
                    eat2();
                    break;
                }
                case SYNC_OBJECT: {
                    eat3();
                    break;
                }
                case SYNC_CLASS: {
                    eat4();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        // 同步方法，非常安全，但性能会打折扣，尤其是方法内的业务比较冗长时
        synchronized public void eat1() {
            acquireOnePie();
        }

        // 锁定成员变量
        public void eat2() {
            synchronized (this.pieNumber) {
                acquireOnePie();
            }
        }

        // 锁定Object锁
        public void eat3() {
            synchronized (commonLock) {
                acquireOnePie();
            }
        }

        // 锁定类锁
        public void eat4() {
            synchronized (PiePlate.class) {
                acquireOnePie();
            }
        }

        private void acquireOnePie() {
            if (pieNumber > 0) {
                //logger.info(Thread.currentThread().getName()  + " has " + pieNumber + " pieces!");
                System.out.println(Thread.currentThread().getName()  + " has " + pieNumber + " pieces!");
                pieNumber--;
                System.out.println(Thread.currentThread().getName()  + " eats one pie and the pie has " + pieNumber + " pieces!");
            } else {
                //logger.info(Thread.currentThread().getName() + " grabs the pie plate but found no pie left");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int pieNumber = 100;
        int nEaters = 300;
        SyncWay syncway = SyncWay.SYNC_MEMBER;
        PiePlate plate = new PiePlate(pieNumber, syncway);
        ExecutorService tp = Executors.newFixedThreadPool(nEaters); // 不要自己写new Thread[].start,会出现不可预知的并发怪异场景
        for (int i = 0; i < nEaters; i++) {
            tp.submit(new Thread(plate));
        }
        tp.shutdown();
    }
}

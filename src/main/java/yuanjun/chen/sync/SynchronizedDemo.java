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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.common.BadPractice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: SynchronizedDemo
 * @Description: java内置锁的demo
 * @author: 陈元俊
 * @date: 2018年7月26日 下午12:27:08
 */
public class SynchronizedDemo {
    private static final Logger logger = LogManager.getLogger(SynchronizedDemo.class);

    public static void main(String[] args) throws Exception {
        int pieNumber = 10;
        int nEaters = 30;
        SyncWay syncway = SyncWay.SYNC_MEMBER;
        PiePlate plate = new PiePlate(pieNumber, syncway);
        ExecutorService tp = Executors.newFixedThreadPool(nEaters);
        for (int i = 0; i < nEaters; i++) {
            tp.submit(new Thread(plate));
        }
        tp.shutdown();
        logger.info("finally " + plate.pieNumber);
    }

    public enum SyncWay {
        SYNC_METHOD, SYNC_MEMBER, SYNC_OBJECT, SYNC_CLASS;
    }

    public static class PiePlate implements Runnable {
        private Integer pieNumber;
        private SyncWay syncWay;
        private Object commonLock = new Object();

        public PiePlate(Integer pieNumber, SyncWay syncWay) {
            this.pieNumber = pieNumber;
            this.syncWay = syncWay;
        }

        public Integer getPieNumber() {
            return pieNumber;
        }

        public void run() {
            switch (this.syncWay) {
                case SYNC_METHOD: {
                    eat1();
                    break;
                }
                case SYNC_MEMBER: {
                    try {
                        eat2();
                    } catch (Exception e) {
                    }
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

        /**
         * 同步方法，非常安全，但性能会打折扣，尤其是方法内的业务比较冗长时.
         */
        public synchronized void eat1() {
            acquireOnePie();
        }

        /**
         * 锁定成员变量,注意此处绝不可以对变化的Integer对象进行加锁 Integer的自动装箱机制，会指向新的地址，从而导致锁定失效！！！.
         */
        @BadPractice
        public void eat2() throws Exception {
            synchronized (this.pieNumber) {
                acquireOnePie();
            }
        }

        /**
         * 锁定Object锁.
         */
        public void eat3() {
            synchronized (commonLock) {
                acquireOnePie();
            }
        }

        /**
         * 锁定类锁.
         */
        public void eat4() {
            synchronized (PiePlate.class) {
                acquireOnePie();
            }
        }

        private void acquireOnePie() {
            if (pieNumber > 0) {
                logger.info(Thread.currentThread().getName() + " has " + pieNumber + " pieces!");
                pieNumber--;
                logger.info(Thread.currentThread().getName() + " eats one pie and the pie has " + pieNumber + " pieces!");
            }
        }
    }
}

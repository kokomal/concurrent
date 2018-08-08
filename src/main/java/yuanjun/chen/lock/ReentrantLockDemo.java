/**
 * @Title: ReentrantLockDemo.java
 * @Package: yuanjun.chen.lock
 * @Description: 重入锁的测试demo
 * @author: 陈元俊
 * @date: 2018年7月30日 上午10:13:05
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: ReentrantLockDemo
 * @Description: 重入锁的测试demo
 * @author: 陈元俊
 * @date: 2018年7月30日 上午10:13:05
 */
public class ReentrantLockDemo {
    private static final Logger logger = LogManager.getLogger(ReentrantLockDemo.class);

    public enum LockWay {
        INSTANT, TRYLOCK, TRYLOCKNOWAIT, LOCKINTERRUPT;
    }

    /** TreasureBox总共含有jewelleries个资源， 每次ransack窃取10个资源 注意Callable和Runnable是两个不同的接口， 需要根据返回值进行不同的实现. */
    public static class TreasureBox implements Callable<Long>, Runnable {
        /** 线程共享显式锁. */
        private final ReentrantLock key = new ReentrantLock();
        /** 珠宝. */
        private Long jewelleries;
        private LockWay lockway;

        public Long getJewelleries() {
            return jewelleries;
        }

        public TreasureBox(Long jewelleries, LockWay lockway) {
            this.jewelleries = jewelleries;
            this.lockway = lockway;
        }

        public Long ransack_trylock() {
            boolean res = false;
            try {
                if (LockWay.TRYLOCKNOWAIT.equals(this.lockway)) {
                    res = key.tryLock();
                } else {
                    res = key.tryLock(10L, TimeUnit.MILLISECONDS); // 最多等10ms
                }
                if (res) {
                    return ransack();
                } else {
                    logger.warn("try lock failed instantly!");
                }
            } catch (Exception e) {
            } finally {
                if (res) {
                    key.unlock(); // 如果未能获得锁，不能贸然unlock
                }
            }
            return 0L;
        }

        public Long ransack_lock() {
            try {
                key.lock();
                return ransack();
            } catch (Exception e) {
            } finally {
                key.unlock();
            }
            return 0L;
        }

        public Long ransack_lockinetrruptibly() {
            try {
                key.lockInterruptibly();
                ransack();
                Thread.sleep(1000); // 这里模拟一下事务处理过程，可能先操作了jewelleries但还没有来得及返回
                return jewelleries;
            } catch (InterruptedException e) {
                logger.info("compensation begins!");
                jewelleries += 10L; // 打断并在返回前补偿
                return jewelleries;
            } catch (Exception ex) {
            } finally {
                key.unlock();
            }
            return 0L;
        }

        private Long ransack() {
            long ran = 10L;
            if (this.jewelleries > ran) {
                this.jewelleries -= ran;
                logger.info("ransacked " + ran + " and jewelleries left =" + this.jewelleries);
            } else {
                logger.warn("jewelleries less than ran, show all " + this.jewelleries);
                this.jewelleries = 0L;
            }
            return this.jewelleries;
        }

        public Long call() throws Exception {
            switch (this.lockway) {
                case INSTANT: default:
                    {
                    return ransack_lock();
                }
                case TRYLOCK:
                case TRYLOCKNOWAIT: {
                    return ransack_trylock();
                }
                case LOCKINTERRUPT: {
                    return ransack_lockinetrruptibly();
                }
            }
        }

        public void run() {
            switch (this.lockway) {
                case INSTANT: default:
                    {
                    ransack_lock();
                    break;
                }
                case TRYLOCKNOWAIT:
                case TRYLOCK: {
                    ransack_trylock();
                    break;
                }
                case LOCKINTERRUPT: {
                    ransack_lockinetrruptibly();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {}
}

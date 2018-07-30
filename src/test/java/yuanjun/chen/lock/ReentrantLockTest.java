/**
 * @Title: ReentrantLockTest.java
 * @Package: yuanjun.chen.lock
 * @Description: 可重入锁测试
 * @author: 陈元俊
 * @date: 2018年7月30日 下午2:47:46
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.lock.ReentrantLockDemo.LockWay;
import yuanjun.chen.lock.ReentrantLockDemo.TreasureBox;

/**
 * @ClassName: ReentrantLockTest
 * @Description: 可重入锁测试
 * @author: 陈元俊
 * @date: 2018年7月30日 下午2:47:46
 */
public class ReentrantLockTest {
    private static final Logger logger = LogManager.getLogger(ReentrantLockTest.class);
    @SuppressWarnings("unchecked")
    @Test
    public void testLock() throws Exception, ExecutionException {
        int nPirates = 100;
        Callable<Long> tb = new TreasureBox(1200l, LockWay.INSTANT);
        ExecutorService tp = Executors.newFixedThreadPool(nPirates);
        Object[] fs = new Object[nPirates]; // Future是接口，不可能new，只能先用Object暂存
        for (int i = 0; i < nPirates; i++) {
            fs[i] = tp.submit(tb); // submit有多个参数
        }
        tp.shutdown();
        // isShutDown当调用shutdown()方法后返回为true。
        // isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true
        while (true) {
            if (tp.isTerminated()) { // isTerminated必须前置shutdown，否则会一直等待！
                logger.info("finally jewelleries left " + ((TreasureBox) tb).getJewelleries());
                for (Object ff : fs) {
                    logger.info("res=>" + ((Future<Long>) ff).get()); // 注意返回不一定是有序的
                }
                break;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTryLock() throws Exception {
        int nPirates = 100;
        Callable<Long> tb = new TreasureBox(1200l, LockWay.TRYLOCK);
        ExecutorService tp = Executors.newFixedThreadPool(nPirates);
        Object[] fs = new Object[nPirates]; // Future是接口，不可能new，只能先用Object暂存
        for (int i = 0; i < nPirates; i++) {
            fs[i] = tp.submit(tb); // submit有多个参数
        }
        tp.shutdown();
        while (true) {
            if (tp.isTerminated()) { // isTerminated必须前置shutdown，否则会一直等待！
                logger.info("finally jewelleries left " + ((TreasureBox) tb).getJewelleries());
                for (Object ff : fs) {
                    logger.info("res=>" + ((Future<Long>) ff).get()); // 注意返回不一定是有序的
                }
                break;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTryLockNoWait() throws Exception {
        int nPirates = 100;
        Callable<Long> tb = new TreasureBox(1200l, LockWay.TRYLOCKNOWAIT);
        ExecutorService tp = Executors.newFixedThreadPool(nPirates);
        Object[] fs = new Object[nPirates]; // Future是接口，不可能new，只能先用Object暂存
        for (int i = 0; i < nPirates; i++) {
            fs[i] = tp.submit(tb); // submit有多个参数
        }
        tp.shutdown();
        while (true) {
            if (tp.isTerminated()) { // isTerminated必须前置shutdown，否则会一直等待！
                logger.info("finally jewelleries left " + ((TreasureBox) tb).getJewelleries());
                for (Object ff : fs) {
                    logger.info("res=>" + ((Future<Long>) ff).get()); // 注意返回不一定是有序的
                }
                break;
            }
        }
    }
    
    @Test
    public void testTryLockInterrupt1() throws Exception {
        TreasureBox tb = new TreasureBox(10000l, LockWay.LOCKINTERRUPT);
        Thread tl = new Thread(tb);
        tl.start();
        Thread.sleep(500); // 线程执行任务1000ms，在500ms时打断
        tl.interrupt(); // 如果还没有返回，会进入catch语句块，执行补偿
        tl.join();
        logger.info("finally jewelleries " + tb.getJewelleries()); 
    }
    
    @Test
    public void testTryLockInterrupt2() throws Exception {
        TreasureBox tb = new TreasureBox(10000l, LockWay.LOCKINTERRUPT);
        Thread tl = new Thread(tb);
        tl.start();
        Thread.sleep(1500); // 线程执行任务1000ms，在1500ms时打断
        tl.interrupt(); // 如果interrupt的时候线程已经结束，那么interrupt本身也没有意义了
        tl.join();
        logger.info("finally jewelleries " + tb.getJewelleries()); 
    }
}

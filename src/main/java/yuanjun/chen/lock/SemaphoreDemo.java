/**
 * @Title: SemaphoreDemo.java
 * @Package: yuanjun.chen.lock
 * @Description:信号量demo
 * @author: 陈元俊
 * @date: 2018年7月30日 下午3:39:16
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.common.Train;

/**
 * @ClassName: SemaphoreDemo
 * @Description: 信号量demo
 * @author: 陈元俊
 * @date: 2018年7月30日 下午3:39:16
 */
public class SemaphoreDemo {
    private static final Logger logger = LogManager.getLogger(SemaphoreDemo.class);

    public static class SodorIsland implements Runnable {
        public static AtomicInteger counts = new AtomicInteger(0);
        private Semaphore trainRails;
        private Train train;

        public SodorIsland(Semaphore trainRails, Train train) {
            super();
            this.train = train;
            this.trainRails = trainRails;
        }

        // 每次处理耗时2000ms
        private void polishCar() throws Exception {
            logger.info("WELCOME! " + train + ", the available trainRails = " 
                    + trainRails.availablePermits()); // 这个available不一定准确了,因为semaphore不排他
            train.setFuel((float) (train.getFuel() * 1.2));
            train.setCarriages(train.getCarriages() + 1);
            Thread.sleep(2000); // 模拟polishcar的耗时
            counts.incrementAndGet();
            logger.info("GOODBYE! " + train);
        }

        public void run() {
            boolean res = false;
            try {
                // logger.info("prepare parking " + train);
                res = trainRails.tryAcquire(1000, TimeUnit.MILLISECONDS);
                if (res) {
                    polishCar();
                } else {
                    logger.info("failed parking " + train);
                }
            } catch (InterruptedException e) {
                logger.info("InterruptedException failed " + train);
            } catch (Exception ex) {
            } finally {
                if (res) {
                    trainRails.release();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int capacity = 10;
        int cars = 25;
        Semaphore sema = new Semaphore(capacity);
        // Train thomas = new Train("thomas", (float) 99.0, 10);
        // Train gordon = new Train("gordon", (float) 88.0, 9);
        // Train percy = new Train("percy", (float) 77.0, 8);
        // Train henry = new Train("henry", (float) 66.0, 7);
        // Train james = new Train("james", (float) 55.0, 6);
        ExecutorService tp = Executors.newFixedThreadPool(cars);
        // tp.execute(new SodorIsland(sema, thomas));
        // tp.execute(new SodorIsland(sema, gordon));
        // tp.execute(new SodorIsland(sema, percy));
        // tp.execute(new SodorIsland(sema, henry));
        // tp.execute(new SodorIsland(sema, james));

        for (int i = 0; i < cars; i++) {
            Train tr = Train.genUUTrain();
            tp.execute(new SodorIsland(sema, tr));
        }

        tp.shutdown();
        while (true) {
            if (tp.isTerminated()) { // isTerminated必须前置shutdown，否则会一直等待！
                logger.info("all tasks end");
                break;
            }
        }
    }
}

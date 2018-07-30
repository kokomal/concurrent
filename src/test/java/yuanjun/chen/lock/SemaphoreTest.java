/**  
 * @Title: SemaphoreTest.java   
 * @Package: yuanjun.chen.lock   
 * @Description: 信号量测试    
 * @author: 陈元俊     
 * @date: 2018年7月30日 下午5:32:49   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.common.Train;
import yuanjun.chen.lock.SemaphoreDemo.SodorIsland;

/**   
 * @ClassName: SemaphoreTest   
 * @Description: 信号量测试  
 * @author: 陈元俊 
 * @date: 2018年7月30日 下午5:32:49  
 */
public class SemaphoreTest {
    private static final Logger logger = LogManager.getLogger(SemaphoreTest.class);
    @Test
    public void testSemaphore() throws Exception {
        int capacity = 70;
        int cars = 250;
        Semaphore sema = new Semaphore(capacity);
        ExecutorService tp = Executors.newFixedThreadPool(cars);
        for (int i = 0; i < cars; i++) {
            Train tr = Train.genUUTrain();
            tp.execute(new SodorIsland(sema, tr));
            Thread.sleep(10);
        }
        tp.shutdown();
        while (true) {
            if (tp.isTerminated()) { // isTerminated必须前置shutdown，否则会一直等待！
                logger.info("all tasks end " + SodorIsland.counts);
                break;
            }
        }
    }
}

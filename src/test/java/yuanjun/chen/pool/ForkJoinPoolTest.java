/**
 * @Title: ForkJoinPoolTest.java
 * @Package: yuanjun.chen.pool
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月4日 下午1:18:15
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @ClassName: ForkJoinPoolTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年9月4日 下午1:18:15
 */
public class ForkJoinPoolTest {
    
    @Test
    public void testSimplePrint() throws Exception {
        PrintTask task = new PrintTask(0, 100);
        // 创建实例，并执行分割任务
        ForkJoinPool pool = ForkJoinPool.commonPool(); // 不太推荐用new，而是用工厂
        pool.submit(task);
        // 线程阻塞，等待所有任务完成
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }

}

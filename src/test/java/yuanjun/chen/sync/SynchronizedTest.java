/**
 * @Title: SynchronizedTest.java
 * @Package: yuanjun.chen.sync
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年7月26日 下午2:06:26
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.sync.SynchronizedDemo.PiePlate;
import yuanjun.chen.sync.SynchronizedDemo.SyncWay;

/**
 * @ClassName: SynchronizedTest
 * @Description: 同步测试类
 * @author: 陈元俊
 * @date: 2018年7月26日 下午2:06:26
 */
public class SynchronizedTest {
    private static final Logger logger = Logger.getLogger(SynchronizedTest.class);
    private static final int pie_number = 100;
    private static final int pie_eaters = 300;
    @Test
    public void testsync001() {
        testSync(pie_number, pie_eaters, SyncWay.SYNC_CLASS);
    }

    @Test
    public void testsync002() {
        testSync(pie_number, pie_eaters, SyncWay.SYNC_MEMBER);
    }

    @Test
    public void testsync003() {
        testSync(pie_number, pie_eaters, SyncWay.SYNC_METHOD);
    }

    @Test
    public void testsync004() {
        testSync(pie_number, pie_eaters, SyncWay.SYNC_OBJECT);
    }
    
    private void testSync(int pieNumber, int nEaters, SyncWay syncway) {
        logger.info("test start!");
        PiePlate plate = new PiePlate(pieNumber, syncway);
        ExecutorService tp = Executors.newFixedThreadPool(nEaters); // 不要自己写new Thread[].start,会出现不可预知的并发怪异场景
        for (int i = 0; i < nEaters; i++) {
            tp.submit(new Thread(plate));
        }
        tp.shutdown();
    }
}

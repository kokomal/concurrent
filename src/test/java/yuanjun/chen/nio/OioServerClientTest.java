/**
 * @Title: OioServerClientTest.java
 * @Package: yuanjun.chen.nio
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月14日 下午4:26:54
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.nio;

import org.junit.Test;
import yuanjun.chen.nio.oiopair.OioClient;
import yuanjun.chen.nio.oiopair.OioServer;

/**
 * @ClassName: OioServerClientTest
 * @Description: 传统IO的测试
 * @author: 陈元俊
 * @date: 2018年8月14日 下午4:26:54
 */
public class OioServerClientTest {
    @Test
    public void testServerAndClient() throws Exception {

        OioServer server = new OioServer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.startAtPort(54321);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        OioClient cli = new OioClient();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cli.connectAtPort("127.0.0.1", 54321);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(12000);
        server.stopThread(54321); // 比较丑陋的打断accept阻塞的手段

        t1.join();
        t2.join();

        System.out.println("dead");
    }
}

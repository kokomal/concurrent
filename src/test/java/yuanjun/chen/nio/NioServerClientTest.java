/**
 * @Title: ServerClientTest.java
 * @Package: yuanjun.chen.nio
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月6日 下午2:54:50
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.nio.niopair.WebClient;
import yuanjun.chen.nio.niopair.WebServer;

/**
 * @ClassName: ServerClientTest
 * @Description: 主要实现了Server和Client的通讯，Server开启服务端SocketChannel后，等待
 *               Client连入，注意此时Client可以位于其他JVM，但连接的是Server的ip+端口
 * @author: 陈元俊
 * @date: 2018年8月6日 下午2:54:50
 */
public class NioServerClientTest {
    private static final Logger logger = LogManager.getLogger(NioServerClientTest.class);

    public static class Cli implements Runnable {
        private int arg;
        private int port;

        public Cli(int arg, int port) {
            super();
            this.arg = arg;
            this.port = port;
        }

        public void run() {
            try {
                WebClient.start("Hello" + arg, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testServerAndClient() throws Exception {
        int port = 44332;
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                logger.info("haha---");
                WebServer.open(port);
            }
        });
        t1.start();
        logger.info("break---");
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new Cli(i, port));
            ts[i].start();
        }
        t1.join();
        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
    }
    
    @Test
    public void testClientOnly() throws Exception {
        int port = 44332;
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new Cli(i, port));
            ts[i].start();
        }
        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
    }
}

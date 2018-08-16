/**
 * @Title: ParodyNettyTest.java
 * @Package: yuanjun.chen.netty
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月16日 下午6:02:57
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.junit.Test;
import yuanjun.chen.netty.parody.NioSelectorRunnablePool;
import yuanjun.chen.netty.parody.ServerBootstrap;
import yuanjun.chen.nio.NioServerClientTest.Cli;

/**
 * @ClassName: ParodyNettyTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年8月16日 下午6:02:57
 */
public class ParodyNettyTest {
    @Test
    public void testNettyServerAndCli() throws Exception {
        int port = 44332;
        // 初始化线程
        NioSelectorRunnablePool nioSelectorRunnablePool =
                new NioSelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        // 获取服务类
        ServerBootstrap bootstrap = new ServerBootstrap(nioSelectorRunnablePool);
        // 绑定端口
        bootstrap.bind(new InetSocketAddress(port));
        System.out.println("server start");

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

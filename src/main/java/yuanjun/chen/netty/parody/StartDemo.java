package yuanjun.chen.netty.parody;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 启动函数.
 * 
 * @SpecialThanksTo -琴兽-
 */
public class StartDemo {
    public static void main(String[] args) {
        // 初始化线程
        NioSelectorRunnablePool nioSelectorRunnablePool =
                new NioSelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        // 获取服务类
        ServerBootstrap bootstrap = new ServerBootstrap(nioSelectorRunnablePool);
        // 绑定端口
        bootstrap.bind(new InetSocketAddress(44332));
        System.out.println("start");
    }
}

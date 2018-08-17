/**
 * @Title: Netty5Server.java
 * @Package: yuanjun.chen.netty.netty5
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月17日 下午1:19:57
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName: Netty5Server
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年8月17日 下午1:19:57
 * @SpecialThanksTo -琴兽-
 */
public class Netty5Server {

    /**
     * @Title: init
     * @Description: 初始化
     * @param port
     * @return: void
     */
    public static void start(int port) {
        // 服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        // boss和worker
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            // 设置线程池
            bootstrap.group(boss, worker);

            // 设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);

            // 设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ServerHandler());
                }
            });

            // netty3中对应设置如下
            // bootstrap.setOption("backlog", 1024);
            // bootstrap.setOption("tcpNoDelay", true);
            // bootstrap.setOption("keepAlive", true);
            // 设置参数，TCP参数
            bootstrap.option(ChannelOption.SO_BACKLOG, 2048);// serverSocketchannel的设置，链接缓冲池的大小
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);// socketchannel的设置,维持链接的活跃，清除死链接
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);// socketchannel的设置,关闭延迟发送

            // 绑定端口
            ChannelFuture future = bootstrap.bind(port);

            System.out.println("start");

            // 等待服务端关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

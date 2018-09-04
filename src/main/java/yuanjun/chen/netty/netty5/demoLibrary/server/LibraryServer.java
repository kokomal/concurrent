package yuanjun.chen.netty.netty5.demoLibrary.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import yuanjun.chen.netty.netty5.demoLibrary.common.codec.GenericRequestDecoder;
import yuanjun.chen.netty.netty5.demoLibrary.common.codec.GenericResponseEncoder;

/**
 * Netty服务端
 *
 * @SpecialThanksTo -琴兽-
 */
public class LibraryServer {
    public static void main(String[] args) {
        // 服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        int port = 10101;
        try {
            // 设置线程池
            bootstrap.group(boss, worker);
            // 设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);
            // 设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new GenericRequestDecoder());
                    ch.pipeline().addLast(new GenericResponseEncoder());
                    ch.pipeline().addLast(new LibrarianHandler());
                }
            });

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

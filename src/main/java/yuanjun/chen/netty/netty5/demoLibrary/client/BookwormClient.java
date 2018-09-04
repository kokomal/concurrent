package yuanjun.chen.netty.netty5.demoLibrary.client;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import yuanjun.chen.netty.netty5.demoLibrary.common.biz.BookBorrowRequest;
import yuanjun.chen.netty.netty5.demoLibrary.common.codec.GenericRequestEncoder;
import yuanjun.chen.netty.netty5.demoLibrary.common.codec.GenericResponseDecoder;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericRequestDto;

/**   
 * @ClassName: BookwormClient   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @SpecialThanksTo -琴兽-
 * @date: 2018年9月4日 下午5:40:52  
 */
public class BookwormClient {
    public static void saySomething() {
        System.out.println("hahaha");
    }

    public static void main(String[] args) {
        saySomething();
        // 服务类
        Bootstrap bootstrap = new Bootstrap();
        int port = 10101;
        // worker
        EventLoopGroup worker = new NioEventLoopGroup();

        // 设置线程池
        bootstrap.group(worker);
        // 设置socket工厂
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new GenericRequestEncoder());
                ch.pipeline().addLast(new GenericResponseDecoder());
                ch.pipeline().addLast(new BookwormHandler());
            }
        });

        // 连接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", port));
        try {
            Channel channel = connect.sync().channel();
            System.out.println("client start");
            do {
                BookBorrowRequest bookReq = new BookBorrowRequest();
                bookReq.setUserName("Hiro Hamada");
                List<String> bookNames = new ArrayList<>();
                bookNames.add("PlayBoy");
                bookNames.add("Gone with the wind");
                bookReq.setBookNames(bookNames );
                System.out.println("I need to borrow book " + bookReq);
                GenericRequestDto req = new GenericRequestDto();
                req.setCommand((short) 1);
                req.setModule((short) 1);
                req.setBizData(bookReq.getBytes());
                // 发送请求
                channel.writeAndFlush(req);
                Thread.sleep(4000);
            } while (true);
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
        }
    }
}

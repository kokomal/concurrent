package yuanjun.chen.netty.netty5.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 多连接客户端
 * 
 * @author -琴兽-
 *
 */
public class Netty5MultiClient {

    public static void start(int nCli, int port) {
        Netty5MultiClient client = new Netty5MultiClient();
        client.init(nCli, port);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("请输入:");
                String msg = bufferedReader.readLine();
                client.nextChannel().writeAndFlush(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int port;
    
    /**
     * 服务类
     */
    private Bootstrap bootstrap = new Bootstrap();

    /**
     * 会话
     */
    private List<Channel> channels = new ArrayList<>();

    /**
     * 引用计数
     */
    private final AtomicInteger index = new AtomicInteger();

    /**
     * 初始化
     * 
     * @param count
     */
    public void init(int count, int port) {
        this.port = port;
        // worker
        EventLoopGroup worker = new NioEventLoopGroup();

        // 设置线程池
        bootstrap.group(worker);

        // 设置socket工厂、
        bootstrap.channel(NioSocketChannel.class);

        // 设置管道
        bootstrap.handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        for (int i = 1; i <= count; i++) {
            ChannelFuture future = bootstrap.connect("127.0.0.1", port);
            channels.add(future.channel());
        }
    }

    /**
     * 获取会话
     * 
     * @return
     */
    public Channel nextChannel() {
        return getFirstActiveChannel(0);
    }


    private Channel getFirstActiveChannel(int count) {
        Channel channel = channels.get(Math.abs(index.getAndIncrement() % channels.size()));
        if (!channel.isActive()) {
            // 重连
            reconnect(channel);
            if (count >= channels.size()) {
                throw new RuntimeException("no can use channel");
            }
            return getFirstActiveChannel(count + 1);
        }
        return channel;
    }

    /**
     * 重连
     * 
     * @param channel
     */
    private void reconnect(Channel channel) {
        synchronized (channel) {
            if (channels.indexOf(channel) == -1) {
                return;
            }
            Channel newChannel = bootstrap.connect("127.0.0.1", port).channel();
            channels.set(channels.indexOf(channel), newChannel);
        }
    }

}

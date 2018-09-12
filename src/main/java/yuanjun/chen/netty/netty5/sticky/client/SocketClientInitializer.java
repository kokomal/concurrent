package yuanjun.chen.netty.netty5.sticky.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**   
 * @ClassName: SocketClientInitializer   
 * @Description: 独立的ChannelInitializer   
 * @author: 陈元俊 
 * @date: 2018年9月12日 下午5:30:00  
 */
public class SocketClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(1024)); // 同server的initializer
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new SocketClientHandler());
    }
}
package yuanjun.chen.netty.netty5.sticky.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**   
 * @ClassName: SocketServerInitializer   
 * @Description: 独立的ChannelInitializer   
 * @author: 陈元俊 
 * @date: 2018年9月12日 下午5:30:00  
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(1024)); // 每一个包的长度1024，根据回车换行符切割
        pipeline.addLast(new StringDecoder()); // 字符串解码器
        pipeline.addLast(new SocketServerHandler());
    }
}
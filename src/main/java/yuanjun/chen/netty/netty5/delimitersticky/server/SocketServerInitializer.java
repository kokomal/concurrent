package yuanjun.chen.netty.netty5.delimitersticky.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 告诉DelimiterBasedFrameDecoder以$_作为分隔符
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
        ChannelPipeline pipeline = ch.pipeline();
        // 这里的1024,表示单条消息的最大长度,当达到长度后,还没有找到分隔符,则抛出TooLongFrameException
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new SocketServerHandler());
    }
}

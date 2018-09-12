package yuanjun.chen.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

/**
 * @ClassName: AbsIntegerEncoder
 * @Description: 绝对数Encoder,注意这是一个MessageToMessage的encoder
 * @author: 陈元俊
 * @SpecialThanksTo: Norman Maurer
 * @date: 2018年9月12日 下午3:27:02
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) {
            int value = Math.abs(in.readInt());
            out.add(value);
        }
    }
}

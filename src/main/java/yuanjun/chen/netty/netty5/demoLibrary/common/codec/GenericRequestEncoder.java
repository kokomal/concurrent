/**
 * @Title: GenericRequestEncoder.java
 * @Package: yuanjun.chen.nio.demoLibrary.common.codec
 * @Description: POJO-->Byte[]
 * @author: 陈元俊
 * @date: 2018年9月4日 下午3:16:39
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import yuanjun.chen.netty.netty5.demoLibrary.common.constant.ConstantValue;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericRequestDto;

/**
 * @ClassName: GenericRequestEncoder
 * @Description: POJO-->Byte[]
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年9月4日 下午3:16:39
 */
public class GenericRequestEncoder extends MessageToByteEncoder<GenericRequestDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, GenericRequestDto msg, ByteBuf out) throws Exception {
        // 包头
        out.writeInt(ConstantValue.FLAG);
        // module
        out.writeShort(msg.getModule());
        // cmd
        out.writeShort(msg.getCommand());
        // 长度
        out.writeInt(msg.getDataLength());
        // data
        if (msg.getBizData() != null) {
            out.writeBytes(msg.getBizData());
        }
    }
}

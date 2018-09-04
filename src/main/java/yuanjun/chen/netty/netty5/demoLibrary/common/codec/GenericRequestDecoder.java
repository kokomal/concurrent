/**
 * @Title: GenericRequestDecoder.java
 * @Package: yuanjun.chen.nio.demoLibrary.common.codec
 * @Description: 请求解码器，Byte[]-->POJO
 * @author: 陈元俊
 * @date: 2018年9月4日 下午3:01:37
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.common.codec;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import yuanjun.chen.netty.netty5.demoLibrary.common.constant.ConstantValue;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericRequestDto;

/**
 * @ClassName: GenericRequestDecoder
 * @Description: 请求解码器，Byte[]-->POJO
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年9月4日 下午3:01:37
 */
public class GenericRequestDecoder extends ByteToMessageDecoder {
    /**
     * 数据包基本长度
     */
    public static int BASE_LENGTH = 4 + 2 + 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (in.readableBytes() >= BASE_LENGTH) {
            System.out.println("GenericRequestDecoder incoming data size = " + in.readableBytes());
            // 防止socket字节流攻击
            if (in.readableBytes() > 2048) {
                in.skipBytes(in.readableBytes());
            }
            // 记录包头开始的index
            int beginReader;
            while (true) {
                beginReader = in.readerIndex();
                in.markReaderIndex();
                if (in.readInt() == ConstantValue.FLAG) { // 确认过眼神，是包头
                    break;
                }
                // 未读到包头，略过一个字节
                in.resetReaderIndex();
                in.readByte();
                // 长度又变得不满足
                if (in.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }
            // 模块号
            short module = in.readShort();
            // 命令号
            short cmd = in.readShort();
            // 长度
            int length = in.readInt();
            // 判断请求数据包数据是否到齐
            if (in.readableBytes() < length) {
                // 还原读指针
                in.readerIndex(beginReader);
                return;
            }
            // 读取data数据
            byte[] data = new byte[length];
            in.readBytes(data);
            GenericRequestDto request = new GenericRequestDto();
            request.setModule(module);
            request.setCommand(cmd);
            request.setBizData(data);
            // 继续往下传递
            out.add(request);
            return;
        }
        // 数据包不完整，需要等待后面的包来
        return;
    }
}

package yuanjun.chen.netty.codecTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import yuanjun.chen.netty.codec.FrameChunkDecoder;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @ClassName: FrameChunkDecoderTest
 * @Description: 帧截断decode测试
 * @author: 陈元俊
 * @SpecialThanksTo: Norman Maurer
 * @date: 2018年9月12日 下午3:44:55
 */
public class FrameChunkDecoderTest {
    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e) {
            // expected exception
            System.out.println("exceptions: " + e);
        }
        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());
        // 2--4--3
        // y--x--y
        // Read frames
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(2), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();
        buf.release();
    }
}

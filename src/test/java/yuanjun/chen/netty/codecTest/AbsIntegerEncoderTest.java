package yuanjun.chen.netty.codecTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import yuanjun.chen.netty.codec.AbsIntegerEncoder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @ClassName: AbsIntegerEncoderTest
 * @Description: 绝对数Encoder
 * @author: 陈元俊
 * @SpecialThanksTo: Norman Maurer
 * @date: 2018年9月12日 下午3:27:02
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        // read bytes
        for (int i = 1; i < 10; i++) {
            Object read = channel.readOutbound(); // 每一次的读取
            System.out.println(i + "th output: " + read);
            assertEquals((Integer) i, read);
        }
        assertNull(channel.readOutbound());
    }
}

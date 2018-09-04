package yuanjun.chen.netty.netty5.demoLibrary.common.serial;

import java.nio.ByteOrder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Buff工厂.
 * 
 * @SpecialThanksTo -琴兽-
 */
public class BufferFactory {
    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    /**
     * 获取一个buffer.
     * 
     * @return
     */
    public static ByteBuf getBuffer() {
        return Unpooled.buffer();
    }

    /**
     * 将数据写入buffer.
     * 
     * @param bytes
     * @return
     */
    public static ByteBuf getBuffer(byte[] bytes) {
        return Unpooled.copiedBuffer(bytes);
    }
}

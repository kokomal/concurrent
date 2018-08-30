package yuanjun.chen.nio.demoLibrary.serial;

import java.nio.ByteOrder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Buff工厂.
 * 
 * @author -琴兽-
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

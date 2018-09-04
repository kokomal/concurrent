/**
 * @Title: AbstractCommonSerializer.java
 * @Package: yuanjun.chen.nio.demoLibrary
 * @Description: 通用序列化工具，netty5实现
 * @author: 陈元俊
 * @date: 2018年8月30日 下午6:25:04
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.common.serial;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName: AbstractCommonSerializer
 * @Description: 通用序列化工具，netty5实现
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年8月30日 下午6:25:04
 */
public abstract class AbstractCommonSerializer {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    protected ByteBuf writeBuffer;
    protected ByteBuf readBuffer;

    /**
     * 反序列化具体实现，保留给具体的实现类去实现
     */
    protected abstract void read();

    /**
     * 序列化具体实现，同上
     */
    protected abstract void write();

    /**
     * 从byte数组获取数据
     * 
     * @param bytes 读取的数组
     */
    public AbstractCommonSerializer readFromBytes(byte[] bytes) {
        this.readBuffer = BufferFactory.getBuffer(bytes);
        read();
        this.readBuffer.clear();
        return this;
    }

    /**
     * 从buff获取数据
     * 
     * @param readBuffer
     */
    public void readFromBuffer(ByteBuf readBuffer) {
        this.readBuffer = readBuffer;
        read();
        this.readBuffer.clear();
    }

    /**
     * 写入本地buff
     * 
     * @return
     */
    public ByteBuf writeToLocalBuff() {
        this.writeBuffer = BufferFactory.getBuffer(); // 清掉旧的
        write();
        return this.writeBuffer;
    }

    /**
     * 写入目标buff
     * 
     * @param buffer
     * @return
     */
    public ByteBuf writeToTargetBuff(ByteBuf buffer) {
        this.writeBuffer = buffer;
        write();
        return this.writeBuffer;
    }

    /**
     * 返回buffer数组
     * 
     * @return
     */
    public byte[] getBytes() {
        writeToLocalBuff();
        byte[] bytes = null;
        if (writeBuffer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        this.writeBuffer.clear();
        return bytes;
    }

    /****************************************************/
    public byte readByte() {
        return readBuffer.readByte();
    }

    public short readShort() {
        return readBuffer.readShort();
    }

    public int readInt() {
        return readBuffer.readInt();
    }

    public long readLong() {
        return readBuffer.readLong();
    }

    public float readFloat() {
        return readBuffer.readFloat();
    }

    public double readDouble() {
        return readBuffer.readDouble();
    }

    public String readString() {
        int size = readBuffer.readShort(); // 先预先读size
        if (size <= 0) {
            return "";
        }
        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);
        return new String(bytes, CHARSET);
    }

    public <T> List<T> readList(Class<T> clz) {
        List<T> list = new ArrayList<>();
        int size = readBuffer.readShort(); // 先预先读size
        for (int i = 0; i < size; i++) {
            list.add(read(clz));
        }
        return list;
    }

    public <K, V> Map<K, V> readMap(Class<K> keyClz, Class<V> valueClz) {
        Map<K, V> map = new HashMap<>();
        int size = readBuffer.readShort(); // 先预先读size
        for (int i = 0; i < size; i++) {
            K key = read(keyClz);
            V value = read(valueClz);
            map.put(key, value);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public <I> I read(Class<I> clz) {
        Object t = null;
        if (clz == int.class || clz == Integer.class) {
            t = this.readInt();
        } else if (clz == byte.class || clz == Byte.class) {
            t = this.readByte();
        } else if (clz == short.class || clz == Short.class) {
            t = this.readShort();
        } else if (clz == long.class || clz == Long.class) {
            t = this.readLong();
        } else if (clz == float.class || clz == Float.class) {
            t = readFloat();
        } else if (clz == double.class || clz == Double.class) {
            t = readDouble();
        } else if (clz == String.class) {
            t = readString();
        } else if (AbstractCommonSerializer.class.isAssignableFrom(clz)) {
            try {
                byte hasObject = this.readBuffer.readByte();
                if (hasObject == 1) {
                    AbstractCommonSerializer temp = (AbstractCommonSerializer) clz.newInstance();
                    temp.readFromBuffer(this.readBuffer);
                    t = temp;
                } else {
                    t = null;
                }
            } catch (Exception e) {
            }

        } else {
            throw new RuntimeException(String.format("不支持类型:[%s]", clz));
        }
        return (I) t;
    }

    public AbstractCommonSerializer writeByte(Byte value) {
        writeBuffer.writeByte(value);
        return this;
    }

    public AbstractCommonSerializer writeShort(Short value) {
        writeBuffer.writeShort(value);
        return this;
    }

    public AbstractCommonSerializer writeInt(Integer value) {
        writeBuffer.writeInt(value);
        return this;
    }

    public AbstractCommonSerializer writeLong(Long value) {
        writeBuffer.writeLong(value);
        return this;
    }

    public AbstractCommonSerializer writeFloat(Float value) {
        writeBuffer.writeFloat(value);
        return this;
    }

    public AbstractCommonSerializer writeDouble(Double value) {
        writeBuffer.writeDouble(value);
        return this;
    }

    public AbstractCommonSerializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort((short) 0);
            return this;
        }

        byte data[] = value.getBytes(CHARSET);
        short len = (short) data.length;
        writeBuffer.writeShort(len);
        writeBuffer.writeBytes(data);
        return this;
    }

    public <T> AbstractCommonSerializer writeList(List<T> list) {
        if (isEmpty(list)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) list.size());
        for (T item : list) {
            writeObject(item);
        }
        return this;
    }

    public <K, V> AbstractCommonSerializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) map.size());
        for (Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    public AbstractCommonSerializer writeObject(Object object) {
        if (object == null) {
            writeByte((byte) 0);
            return this;
        } else {
            if (object instanceof Integer) {
                writeInt((int) object);
                return this;
            }
            if (object instanceof Long) {
                writeLong((long) object);
                return this;
            }
            if (object instanceof Short) {
                writeShort((short) object);
                return this;
            }
            if (object instanceof Byte) {
                writeByte((byte) object);
                return this;
            }
            if (object instanceof String) {
                String value = (String) object;
                writeString(value);
                return this;
            }
            if (object instanceof AbstractCommonSerializer) {
                writeByte((byte) 1);
                AbstractCommonSerializer value = (AbstractCommonSerializer) object;
                value.writeToTargetBuff(writeBuffer);
                return this;
            }
            throw new RuntimeException("不可序列化的类型:" + object.getClass());
        }
    }

    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    public <K, V> boolean isEmpty(Map<K, V> c) {
        return c == null || c.size() == 0;
    }
}

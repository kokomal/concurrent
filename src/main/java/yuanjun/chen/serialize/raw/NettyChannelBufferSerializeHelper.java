/**
 * @Title: NettyChannelBufferSerializeHelper.java
 * @Package: yuanjun.chen.serialize.raw
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月22日 上午11:02:11
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.raw;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: NettyChannelBufferSerializeHelper
 * @Description: 5改成了ByteBuf
 * @author: 陈元俊
 * @date: 2018年8月22日 上午11:02:11
 */
public class NettyChannelBufferSerializeHelper {
    public static byte[] ints2Bytes(List<Integer> targs) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        for (Integer targ : targs) {
            buffer.writeInt(targ);
        }
        int l = buffer.writerIndex(); // 刻舟求剑
        byte[] nn = new byte[l];
        buffer.readBytes(nn);
        return nn;
    }

    public static List<Integer> bytes2Ints(byte[] ashes) throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer(ashes);
        List<Integer> ll = new ArrayList<>();
        while (buffer.isReadable()) {
            int read = buffer.readInt();
            ll.add(read);
        }
        return ll;
    }

    public static void main(String[] args) throws Exception {
        Random seed = new Random();
        List<Integer> targs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            targs.add(seed.nextInt(100));
        }
        System.out.println("NEWBORN List<Integer> = " + targs);
        byte[] ashes = ints2Bytes(targs);
        System.out.println("bytes = " + Arrays.toString(ashes)); // 清晰看到多个高位零的稀疏数组
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸

        List<Integer> reviv = bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }
}

/**
 * @Title: NioByteBufferAuxSerializeHelper.java
 * @Package: yuanjun.chen.serialize.raw
 * @Description: 采用Nio的ByteBuffer进行基本类型的序列化
 * @author: 陈元俊
 * @date: 2018年8月22日 上午10:34:32
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.raw;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: NioByteBufferAuxSerializeHelper
 * @Description: 采用Nio的ByteBuffer进行基本类型的序列化
 * @author: 陈元俊
 * @date: 2018年8月22日 上午10:34:32
 */
public class NioByteBufferAuxSerializeHelper {
    public static byte[] ints2Bytes(List<Integer> args) throws Exception {
        int size = args.size();
        ByteBuffer buffer = ByteBuffer.allocate(4 * size);
        for (Integer arg : args) {
            buffer.putInt(arg); // 采用ByteBuffer的putInt方法
        }
        byte[] ashes = buffer.array();
        return ashes;
    }

    public static List<Integer> bytes2Ints(byte[] ashes) throws Exception {
        List<Integer> ll = new ArrayList<>();
        ByteBuffer buffer2 = ByteBuffer.wrap(ashes);
        while (buffer2.hasRemaining()) {
            ll.add(buffer2.getInt());
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
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸，4*size

        List<Integer> reviv = bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }

}

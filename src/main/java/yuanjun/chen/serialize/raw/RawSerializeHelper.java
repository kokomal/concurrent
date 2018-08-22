/**
 * @Title: RawSerializeHelper.java
 * @Package: yuanjun.chen.serialize.raw
 * @Description: 原始采用int暴力转4byte的方式进行int的序列化
 * @author: 陈元俊
 * @date: 2018年8月22日 上午10:11:11
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.raw;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: RawSerializeHelper
 * @Description: 原始采用int暴力转4byte的方式进行int的序列化，可见有大量的高位0存在
 * @author: 陈元俊
 * @date: 2018年8月22日 上午10:11:11
 */
public class RawSerializeHelper {
    private static final int magicFF = 0xff;

    /** 简单对List<Integer>列表进行序列化操作. */
    public static byte[] ints2Bytes(List<Integer> targs) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        for (Integer targ : targs) {
            arrayOutputStream.write(singleint2bytes(targ));
        }
        return arrayOutputStream.toByteArray();
    }

    /** 简单对byte[]进行反序列化操作转换成List<Integer>. */
    public static List<Integer> bytes2Ints(byte[] ashes) throws Exception {
        List<Integer> ll = new ArrayList<>();
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(ashes);
        while (arrayInputStream.available() > 0) {
            byte[] oneIntByte = new byte[4];
            arrayInputStream.read(oneIntByte); // 写入4个byte
            ll.add(bytes2singleint(oneIntByte));
        }
        return ll;
    }

    public static void main(String[] args) throws Exception {
        Random seed = new Random();
        List<Integer> targs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            targs.add(seed.nextInt(1000));
        }
        System.out.println("NEWBORN List<Integer> = " + targs);
        byte[] ashes = ints2Bytes(targs);
        System.out.println("bytes = " + Arrays.toString(ashes)); // 清晰看到多个高位零的稀疏数组
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸

        List<Integer> reviv = bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }

    /**
     * 大端字节序列(先写高位，再写低位) 每一个int都占用4个字节，相对来说比较浪费空间.
     * 
     * @param i
     * @return
     */
    public static byte[] singleint2bytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((i >> 24) & magicFF);
        bytes[1] = (byte) ((i >> 16) & magicFF);
        bytes[2] = (byte) ((i >> 8) & magicFF);
        bytes[3] = (byte) ((i >> 0) & magicFF);
        return bytes;
    }

    /**
     * 大端.
     * 
     * @param bytes
     * @return
     */
    public static int bytes2singleint(byte[] bArr) {
        return ((bArr[0] & magicFF) << 24) | ((bArr[1] & magicFF) << 16) | ((bArr[2] & magicFF) << 8)
                | ((bArr[3] & magicFF) << 0);
    }
}

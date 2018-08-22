/**
 * @Title: SerializeTest.java
 * @Package: yuanjun.chen.serialize
 * @Description: 序列化的测试
 * @author: 陈元俊
 * @date: 2018年8月22日 上午9:43:07
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import yuanjun.chen.serialize.raw.NettyChannelBufferSerializeHelper;
import yuanjun.chen.serialize.raw.NioByteBufferAuxSerializeHelper;
import yuanjun.chen.serialize.raw.RawSerializeHelper;

/**
 * @ClassName: SerializeTest
 * @Description: 序列化的测试
 * @author: 陈元俊
 * @date: 2018年8月22日 上午9:43:07
 */
public class SerializeTest {
    /**
     * 字节数组 
     */
    @Test
    public void testCloneByteArray() {
        byte[] array1 = "HAHA".getBytes();
        byte[] array2 = array1.clone(); // byte级别的肯定是深拷贝
        System.out.println("array1 length = " + array1.length + ", and Content = " + Arrays.toString(array1));
        array1[1] = 'O';
        System.out.println(new String(array1));
        System.out.println(new String(array2));
    }
    
    @Test
    public void rawSerializeTest() throws Exception {
        List<Integer> targs = generateRandomListInteger(1000, 30);
        System.out.println("NEWBORN List<Integer> = " + targs);
        byte[] ashes = RawSerializeHelper.ints2Bytes(targs);
        System.out.println("bytes = " + Arrays.toString(ashes)); // 清晰看到多个高位零的稀疏数组
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸

        List<Integer> reviv = RawSerializeHelper.bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }
    
    @Test
    public void nioSerializeTest() throws Exception {
        List<Integer> targs = generateRandomListInteger(1000, 300);
        System.out.println("NEWBORN List<Integer> = " + targs);
        byte[] ashes = NioByteBufferAuxSerializeHelper.ints2Bytes(targs);
        System.out.println("bytes = " + Arrays.toString(ashes)); // 清晰看到多个高位零的稀疏数组
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸

        List<Integer> reviv = NioByteBufferAuxSerializeHelper.bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }
    
    @Test
    public void nettySerializeTest() throws Exception {
        List<Integer> targs = generateRandomListInteger(1000, 300);
        System.out.println("NEWBORN List<Integer> = " + targs);
        byte[] ashes = NettyChannelBufferSerializeHelper.ints2Bytes(targs);
        System.out.println("bytes = " + Arrays.toString(ashes)); // 清晰看到多个高位零的稀疏数组
        System.out.println("bytes len = " + ashes.length); // 转换后的尺寸

        List<Integer> reviv = NettyChannelBufferSerializeHelper.bytes2Ints(ashes);
        System.out.println("REVIVED List<Integer> = " + reviv);
    }

    private static List<Integer> generateRandomListInteger(int bound, int counts) {
        Random seed = new Random();
        List<Integer> targs = new ArrayList<>();
        for (int i = 0; i < counts; i++) {
            targs.add(seed.nextInt(bound));
        }
        return targs;
    }
}

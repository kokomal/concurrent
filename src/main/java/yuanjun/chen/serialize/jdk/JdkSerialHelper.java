/**
 * @Title: JdkSerialHelper.java
 * @Package: yuanjun.chen.serialize.jdk
 * @Description: jdk自带ObjectOutputStream,性能比较低下
 * @author: 陈元俊
 * @date: 2018年8月21日 下午1:22:45
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import yuanjun.chen.serialize.pojo.DemoPOJO;
import yuanjun.chen.serialize.pojo.PojoUtils;

/**
 * @ClassName: JdkSerialHelper
 * @Description: jdk自带ObjectOutputStream,性能比较低下
 * @author: 陈元俊
 * @date: 2018年8月21日 下午1:22:45
 */
public class JdkSerialHelper {
    public static void main(String[] args) throws Exception {
        DemoPOJO dm = PojoUtils.generateRandomDemoPojo();
        System.out.println(dm);

        byte[] ashes = toBytes(dm);
        System.out.println(Arrays.toString(ashes));
        System.out.println(ashes.length);

        DemoPOJO reviv = parse(ashes, DemoPOJO.class);
        System.out.println(reviv);
    }

    public static <T extends Serializable> byte[] toBytes(T obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // 写入对象
        objectOutputStream.writeObject(obj);
        // 获取 字节数组
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T parse(byte[] ashes, Class<T> clazz) throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(ashes));
        T res = (T) inputStream.readObject();
        return res;
    }
}

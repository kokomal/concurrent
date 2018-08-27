/**
 * @Title: KyroHelper.java
 * @Package: yuanjun.chen.serialize.kyro
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月21日 上午11:01:31
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;
import yuanjun.chen.serialize.pojo.DemoPOJO;
import yuanjun.chen.serialize.pojo.PojoUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: KyroHelper
 * @Description: 使用Kyro序列化框架，轻量级的持久化框架，非常适合复杂数据类型的传输(例如嵌套map，其他pojo等)
 * @author: 陈元俊
 * @date: 2018年8月21日 上午11:01:31
 */
public class KyroHelper {
    public static <T extends Serializable> byte[] toBytes(T obj) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.register(obj.getClass());
        Output output = new Output(4096, 65536); // 太小会溢出
        kryo.writeObject(output, obj);
        byte[] ashes = output.toBytes();
        output.flush();
        output.close();
        return ashes;
    }

    public static <T extends Serializable> T parse(byte[] ashes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        Input input;
        try {
            input = new Input(ashes);
            T res = kryo.readObject(input, clazz);
            input.close();
            return res;
        } catch (KryoException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        DemoPOJO dm = PojoUtils.generateRandomDemoPojo();
        Map<Integer, String> mpp = new HashMap<>();
        for (int i = 0; i < 99; i++) {
            mpp.put(i, UUID.randomUUID().toString());
        }
        dm.setMp(mpp);
        System.out.println(dm);

        byte[] ashes = toBytes(dm);
        System.out.println(Arrays.toString(ashes));
        System.out.println(ashes.length);

        DemoPOJO reviv = parse(ashes, DemoPOJO.class);
        System.out.println(reviv);
    }
}

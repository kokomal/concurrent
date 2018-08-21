/**
 * @Title: PojoUtils.java
 * @Package: yuanjun.chen.serialize.pojo
 * @Description: DemoPojo随机mock类
 * @author: 陈元俊
 * @date: 2018年8月21日 下午12:23:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName: PojoUtils
 * @Description: DemoPojo随机mock类
 * @author: 陈元俊
 * @date: 2018年8月21日 下午12:23:03
 */
public class PojoUtils {
    public static final DemoPOJO generateRandomDemoPojo() {
        Random seed = new Random();
        DemoPOJO demo = new DemoPOJO();
        demo.setDeliveryDate(new Date());
        demo.setId(seed.nextLong());
        demo.setMaterialCost(new BigDecimal(seed.nextDouble()));
        demo.setRemunerateCost(new BigDecimal(seed.nextDouble()));
        demo.setReservedPriorityA(seed.nextInt(100));
        demo.setReservedPriorityB(seed.nextInt(100));
        demo.setReservedPriorityC(seed.nextInt(100));
        demo.setTransportPriority(seed.nextInt(100));
        demo.setUrgentDegree(seed.nextInt(100));
        return demo;
    }
}

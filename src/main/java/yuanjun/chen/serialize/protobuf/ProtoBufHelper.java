/**
 * @Title: ProtoBufHelper.java
 * @Package: yuanjun.chen.serialize.protobuf
 * @Description: protobuf的序列化和反序列化helper
 * @author: 陈元俊
 * @date: 2018年8月21日 下午2:07:00
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.serialize.protobuf;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import yuanjun.chen.serialize.pojo.DemoPOJO;
import yuanjun.chen.serialize.pojo.PojoUtils;
import yuanjun.chen.serialize.protobuf.dto.DemoDtoModule;
import yuanjun.chen.serialize.protobuf.dto.DemoDtoModule.DemoDto;

/**
 * @ClassName: ProtoBufHelper
 * @Description: protobuf的序列化和反序列化helper, 缺点就是类型支持较窄,转换比较费劲,优点是尺寸极小,带宽占用小
 * @author: 陈元俊
 * @date: 2018年8月21日 下午2:07:00
 */
public class ProtoBufHelper {
    public static void main(String[] args) throws Exception {
        DemoPOJO dm = PojoUtils.generateRandomDemoPojo();
        System.out.println(dm);

        byte[] ashes = toBytes(dm);
        System.out.println(Arrays.toString(ashes));
        System.out.println(ashes.length);

        DemoPOJO reviv = parse(ashes);
        System.out.println(reviv);
    }

    public static byte[] toBytes(final DemoPOJO obj) {
        DemoDtoModule.DemoDto.Builder demobuilder = DemoDtoModule.DemoDto.newBuilder();
        demobuilder.setDeliveryDateRaw(obj.getDeliveryDate().getTime()); // 日期转换成long
        demobuilder.setId(obj.getId());
        demobuilder.setMaterialCost(obj.getMaterialCost().doubleValue()); // 转换成double
        demobuilder.setRemunerateCost(obj.getRemunerateCost().doubleValue());
        demobuilder.setReservedPriorityA(obj.getReservedPriorityA());
        demobuilder.setReservedPriorityB(obj.getReservedPriorityB());
        demobuilder.setReservedPriorityC(obj.getReservedPriorityC());
        demobuilder.setTransportPriority(obj.getTransportPriority());
        demobuilder.setUrgentDegree(obj.getUrgentDegree());
        DemoDto dm = demobuilder.build();
        return dm.toByteArray();
    }

    public static DemoPOJO parse(byte[] ashes) throws Exception {
        DemoDtoModule.DemoDto dm = DemoDtoModule.DemoDto.parseFrom(ashes);
        DemoPOJO res = new DemoPOJO();
        res.setDeliveryDate(new Date(dm.getDeliveryDateRaw()));
        res.setId(dm.getId());
        res.setMaterialCost(new BigDecimal(dm.getMaterialCost()));
        res.setRemunerateCost(new BigDecimal(dm.getRemunerateCost()));
        res.setReservedPriorityA(dm.getReservedPriorityA());
        res.setReservedPriorityB(dm.getReservedPriorityB());
        res.setReservedPriorityC(dm.getReservedPriorityC());
        res.setTransportPriority(dm.getTransportPriority());
        res.setUrgentDegree(dm.getUrgentDegree());
        return res;
    }
}

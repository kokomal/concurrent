package yuanjun.chen.serialize.protobuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import yuanjun.chen.serialize.protobuf.dto.PlayerModule;
import yuanjun.chen.serialize.protobuf.dto.PlayerModule.PBPlayer;
import yuanjun.chen.serialize.protobuf.dto.PlayerModule.PBPlayer.Builder;

/**
 * protobuf学习
 * 
 * @SpecialThanksTo -琴兽-
 *
 */
public class PB2Bytes {

    public static void main(String[] args) throws Exception {
        byte[] ashes = toBytes();
        toPlayer(ashes);
    }

    /**
     * 序列化
     */
    public static byte[] toBytes() {
        // 获取一个PBPlayer的构造器
        Builder builder = PlayerModule.PBPlayer.newBuilder();
        List<Integer> ll = new ArrayList<>();
        ll.add(123);
        ll.add(456);
        // 设置数据
        builder.setPlayerId(101).setAge(20).setName("Thor").addAllSkills(ll);
        // 构造出对象
        PBPlayer player = builder.build();
        // 序列化成字节数组
        byte[] byteArray = player.toByteArray();
        System.out.println("turn player into---" + Arrays.toString(byteArray));
        return byteArray;
    }

    /**
     * 反序列化
     * 
     * @param bs
     * @throws Exception
     */
    public static void toPlayer(byte[] bs) throws Exception {
        PBPlayer player = PlayerModule.PBPlayer.parseFrom(bs);
        System.out.println("revived from ashes---" + player.toString());
    }
}

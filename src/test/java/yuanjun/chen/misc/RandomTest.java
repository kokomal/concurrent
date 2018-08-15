package yuanjun.chen.misc;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;

public class RandomTest {

    @Test
    public void traditionalRandom() {
        // (1)创建一个默认种子的随机数生成器
        Random random = new Random();
        // (2)输出10个在0-5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; ++i) {
            System.out.println(random.nextInt(5));
        }
    }

    @Test
    public void threadLocalRandom() {
        // (10)获取一个随机数生成器
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // (11)输出10个在0-5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; ++i) {
            System.out.println(random.nextInt(5));
        }
    }
}

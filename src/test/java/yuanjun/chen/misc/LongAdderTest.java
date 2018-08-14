/**
 * @Title: LongAdderTest.java
 * @Package: yuanjun.chen.misc
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月13日 下午1:48:30
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import org.junit.Test;

/**
 * @ClassName: LongAdderTest
 * @Description: 测试JDK8新增的LongAdder与JDK5的AtomicLong性能
 * @author: 陈元俊
 * @date: 2018年8月13日 下午1:48:30
 */
public class LongAdderTest {
    @Test
    public void testLongAdderAndAtomicLong() {
        testAtomicLongVSLongAdder(20, 5000000);
    }

    static void testAtomicLongVSLongAdder(final int threadCount, final int times) {
        try {
            long start = System.currentTimeMillis();
            testLongAdder(threadCount, times);
            long end = System.currentTimeMillis() - start;
            System.out.println("条件>>>>>>线程数:" + threadCount + ", 单线程操作计数" + times);
            System.out.println("结果>>>>>>LongAdder方式增加计数" + (threadCount * times) + "次,共计耗时:" + end);

            long start2 = System.currentTimeMillis();
            testAtomicLong(threadCount, times);
            long end2 = System.currentTimeMillis() - start2;
            System.out.println("条件>>>>>>线程数:" + threadCount + ", 单线程操作计数" + times);
            System.out.println("结果>>>>>>AtomicLong方式增加计数" + (threadCount * times) + "次,共计耗时:" + end2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < times; j++) {
                        atomicLong.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            }, "my-thread" + i));
        }

        for (Thread thread : list) {
            thread.start();
        }
        countDownLatch.await();
        System.out.println("finally the atomicLong = " + atomicLong);
    }

    static void testLongAdder(final int threadCount, final int times) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < times; j++) {
                        longAdder.add(1);
                    }
                    countDownLatch.countDown();
                }
            }, "my-thread" + i));
        }

        for (Thread thread : list) {
            thread.start();
        }
        countDownLatch.await();
        System.out.println("finally the longAdder = " + longAdder);
    }

}

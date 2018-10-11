/**
 * @Title: FibonacciTest.java
 * @Package: yuanjun.chen.pool
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月10日 上午9:26:14
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.pool;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @ClassName: FibonacciTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月10日 上午9:26:14
 */
public class FibonacciTest {

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal ONE = new BigDecimal(1);

    @Test
    public void testFibonacci() throws Exception {
        int n = 44;
        FibobacciTask fb = new FibobacciTask(n);
        ForkJoinPool pool = ForkJoinPool.commonPool(); // 不太推荐用new，而是用工厂
        BigDecimal k = pool.submit(fb).get();
        System.out.println("ForkJoin Fibonacci(" + n + ") = " + k);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }

    @Test
    public void testRecursiveFibonacci() {
        int targ = 45;
        System.out.println("Recursive Fibonacci(" + targ + ") = " + fiboRecursive(44));
    }

    @Test
    public void testTailRecursiveFibonacci() {
        int targ = 44;
        System.out.println("Tail Fibonacci(" + targ + ") = " + smartFiboRecursive(targ, ZERO, ONE));
    }
    
    @Test
    public void testLoopFibonacci() {
        int targ = 44;
        System.out.println("Loop Fibonacci(" + targ + ") = " + loopFibonacci(44));
    }
    
    @Test
    public void testCachedFibonacci() {
        int targ = 44;
        System.out.println("Cached recursive Fibonacci(" + targ + ") = " + cachedRecursiveFibonacci(44));
    }

    /**
     * @Title: fiboRecursive
     * @Description: 低效递归
     * @return: BigDecimal
     */
    private static BigDecimal fiboRecursive(int n) {
        if (n == 0) {
            return ZERO;
        } else if (n == 1) {
            return ONE;
        }
        return fiboRecursive(n - 1).add(fiboRecursive(n - 2));
    }

    /**
     * @Title: smartFiboRecursive
     * @Description: 尾递归
     * @return: BigDecimal
     */
    private static BigDecimal smartFiboRecursive(int n, BigDecimal fn_2, BigDecimal fn_1) {
        if (n == 0) {
            return fn_2;
        }
        return smartFiboRecursive(n - 1, fn_1, fn_2.add(fn_1));
    }

    private static Map<Integer, BigDecimal> cache = new ConcurrentHashMap<>();
    static {
        cache.put(0, new BigDecimal(0));
        cache.put(1, new BigDecimal(1));
    }
    
    /**   
     * @Title: cachedRecursiveFibonacci   
     * @Description: 带缓存的fibonacci 
     * @param n
     * @return      
     * @return: BigDecimal      
     */
    private static BigDecimal cachedRecursiveFibonacci(int n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        BigDecimal res = cachedRecursiveFibonacci(n - 1)
                .add(cachedRecursiveFibonacci(n - 2));
        cache.put(n, res);
        return res;
    }

    /**
     * @Title: loopFibonacci
     * @Description: 纯迭代
     * @return: BigDecimal
     */
    private static BigDecimal loopFibonacci(int n) {
        BigDecimal f0 = ZERO;
        BigDecimal f1 = ONE;
        BigDecimal currentNum = ZERO;
        if (n <= 1) {
            return new BigDecimal(n);
        }
        for (int i = 2; i <= n; i++) {
            currentNum = f0.add(f1);
            f0 = f1;
            f1 = currentNum;
        }
        return currentNum;
    }

}

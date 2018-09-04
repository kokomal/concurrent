/**
 * @Title: FibobacciTask.java
 * @Package: yuanjun.chen.pool
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月4日 上午11:30:18
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.pool;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

/**
 * @ClassName: FibobacciTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年9月4日 上午11:30:18
 */
public class FibobacciTask extends RecursiveTask<BigDecimal> {
    private static final long serialVersionUID = 3724102379241252483L;
    private int n;

    private static Map<Integer, BigDecimal> cache = new ConcurrentHashMap<>();
    static {
        cache.put(0, new BigDecimal(0));
        cache.put(1, new BigDecimal(1));
    }

    public FibobacciTask(int n) {
        this.n = n;
    }

    @Override
    protected BigDecimal compute() {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        if (this.n <= 0) {
            return new BigDecimal(0);
        }
        FibobacciTask pre1 = new FibobacciTask(n - 1);
        FibobacciTask pre2 = new FibobacciTask(n - 2);
        BigDecimal result = pre1.fork().join().add(pre2.fork().join());
        cache.put(n, result);
        printFibo(result);
        return result;
    }

    private void printFibo(BigDecimal result) {
        System.out.println("FIBONACCI (" + n + ")= " + result);
    }
}

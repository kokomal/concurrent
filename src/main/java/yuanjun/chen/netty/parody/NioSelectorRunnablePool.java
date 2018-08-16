package yuanjun.chen.netty.parody;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import yuanjun.chen.netty.parody.boss.Boss;
import yuanjun.chen.netty.parody.boss.NioServerBoss;
import yuanjun.chen.netty.parody.worker.NioServerWorker;
import yuanjun.chen.netty.parody.worker.Worker;

/**
 * Selector管理者.
 * 
 * @SpecialThanksTo -琴兽-
 */
public class NioSelectorRunnablePool {
    /** Boss数组. */
    private final AtomicInteger bossIndex = new AtomicInteger();
    private Boss[] bosses;

    /** Worker数组. */
    private final AtomicInteger workerIndex = new AtomicInteger();
    private Worker[] workeres;

    public NioSelectorRunnablePool(Executor boss, Executor worker) {
        initBoss(boss, 1);
        initWorker(worker, Runtime.getRuntime().availableProcessors() * 2);
    }

    /**
     * 初始化boss.这里的Boss和Worker均不是runnable，而是绑定一个已有的线程池
     * 在new的时候已经开始open Selector了！
     * @param boss
     * @param count
     */
    private void initBoss(Executor boss, int count) {
        this.bosses = new NioServerBoss[count];
        for (int i = 0; i < bosses.length; i++) {
            bosses[i] = new NioServerBoss(boss, "boss thread " + (i + 1), this);
        }
    }

    /**
     * 初始化worker.
     * 
     * @param worker
     * @param count
     */
    private void initWorker(Executor worker, int count) {
        this.workeres = new NioServerWorker[count];
        for (int i = 0; i < workeres.length; i++) {
            workeres[i] = new NioServerWorker(worker, "worker thread " + (i + 1), this);
        }
    }

    /**
     * 获取一个worker.RoundRobin
     * 
     * @return
     */
    public Worker nextWorker() {
        return workeres[Math.abs(workerIndex.getAndIncrement() % workeres.length)];
    }

    /**
     * 获取一个boss.RoundRobin
     * 
     * @return
     */
    public Boss nextBoss() {
        return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
    }
}

package yuanjun.chen.netty.parody;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象selector线程类.
 *
 * @SpecialThanksTo -琴兽-
 */
public abstract class AbstractNioSelector implements Runnable {
    /**
     * 选择器wakenUp状态标记.
     */
    protected final AtomicBoolean wakenUp = new AtomicBoolean();
    /**
     * 线程池.
     */
    private final Executor executor;
    /**
     * 任务队列.
     */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
    /**
     * 选择器.
     */
    protected Selector selector;
    /**
     * 线程管理对象.
     */
    protected NioSelectorRunnablePool selectorRunnablePool;
    /**
     * 线程名称.
     */
    private String threadName;

    protected AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.selectorRunnablePool = selectorRunnablePool;
        openSelector();
    }

    /**
     * 获取selector并启动线程.
     */
    private void openSelector() {
        try {
            this.selector = Selector.open(); // 每一个的selector都不一样
            // System.out.println(this.selector);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create a selector.");
        }
        executor.execute(this); // 所有Boss和Worker直接通过线程池直接执行，而且是初始化好了之后立刻执行，和taskQueue没有关系
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        do {
            try {
                wakenUp.set(false);
                select(selector); // boss会延迟等待，worker会一直等
                processTaskQueue(); // 很简单，从任务队列取出Runnable并执行，这里是取selector注册事件，根据不同的身份，设置不同的selector监听事件
                process(selector); // 对于boss而言，accept，引导新客户端的接入，对于worker而言，执行具体业务（字符串读取）
            } catch (Exception e) {
                // ignore
            }
        } while (true);
    }

    /**
     * 注册一个任务并激活selector.
     *
     * @param task
     */
    protected final void registerTask(Runnable task) {
        taskQueue.add(task);
        Selector selector = this.selector;
        if (selector != null) {
            if (wakenUp.compareAndSet(false, true)) {
                selector.wakeup();
            }
        } else {
            taskQueue.remove(task);
        }
    }

    /**
     * 执行队列里的任务.
     */
    private void processTaskQueue() { // 怎么跳出死循环？
        for (; ; ) {
            final Runnable task = taskQueue.poll();
            if (task == null) {
                break; // 取不到才跳出
            }
            task.run();
        }
    }

    /**
     * 获取线程管理对象.
     *
     * @return
     */
    public NioSelectorRunnablePool getSelectorRunnablePool() {
        return selectorRunnablePool;
    }

    /**
     * Select抽象方法. 抽象方法，让Boss或者Worker具体进行实现
     *
     * @param selector
     * @return
     * @throws IOException
     */
    protected abstract int select(Selector selector) throws IOException;

    /**
     * Selector的业务处理. 抽象方法，让Boss或者Worker具体进行实现
     *
     * @param selector
     * @throws IOException
     */
    protected abstract void process(Selector selector) throws IOException;
}

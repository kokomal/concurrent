/**
 * @Title: MyLinkedBlockingQueue.java
 * @Package: yuanjun.chen.container
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月2日 上午9:28:28
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.container;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: MyLinkedBlockingQueue
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年8月2日 上午9:28:28
 */
public class MyLinkedBlockingQueue<T extends Object> {
    private final AtomicInteger count;
    private List<T> listval;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;
    private int capacity;

    public MyLinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.listval = new LinkedList<T>();
        this.lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
        count = new AtomicInteger();
    }

    public static void main(String[] args) throws Exception {
        MyLinkedBlockingQueue<Integer> mq = new MyLinkedBlockingQueue<Integer>(4);
        mq.add(11);
        mq.add(22);
        System.out.println(mq.take());
        System.out.println(mq.take());
        long t1 = System.currentTimeMillis();
        try {
            mq.take();
        } catch (Exception e) {
            System.out.println("did not fetch " + e);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("fetch time " + (t2 - t1) + "ms");
    }

    public void add(T item) throws Exception {
        lock.lock();
        try {
            while (this.count.get() == this.capacity) {
                notFull.await();
            }
            this.listval.add(item);
            this.count.incrementAndGet();
            notEmpty.signalAll(); // 可以取了
        } finally {
            lock.unlock();
        }
    }

    public T take() throws Exception {
        lock.lock();
        try {
            while (this.count.get() == 0) {
                notEmpty.await(); // 等待，天荒地老
            }
            T rem = this.listval.remove(this.count.decrementAndGet());
            notFull.signalAll(); // 可以加了
            return rem;
        } finally {
            lock.unlock();
        }
    }

    public T takeWithOT(long time, TimeUnit unit) throws Exception {
        lock.lock();
        try {
            while (this.count.get() == 0) {
                boolean waiteSuccess = notEmpty.await(time, unit); // 等待指定时间
                if (!waiteSuccess) {
                    throw new Exception("OverTime!");
                } // 超时了，跳出去
            }
            T rem = this.listval.remove(this.count.decrementAndGet());
            notFull.signalAll(); // 可以加了
            return rem;
        } finally {
            lock.unlock();
        }
    }
}

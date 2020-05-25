package yuanjun.chen.curator.locks;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/23 21:36
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 * 分段锁，系统提供一定数量的原始锁，根据传入对象的哈希值获取对应的锁并加锁
 * 注意：要锁的对象的哈希值如果发生改变，有可能导致锁无法成功释放!!!
 */
public class SegmentLock<T> {
    private Integer segments = 16;//默认分段数量
    private ReentrantLock[] lockArray;

    public SegmentLock() {
        init(null, false);
    }

    public SegmentLock(Integer counts, boolean fair) {
        init(counts, fair);
    }

    private void init(Integer counts, boolean fair) {
        if (counts != null) {
            segments = counts;
        }
        lockArray = new ReentrantLock[segments];
        for (int i = 0; i < segments; i++) {
            lockArray[i] = new ReentrantLock(fair);
        }
    }

    public void lock(T key) {
        ReentrantLock lock = lockArray[((key.hashCode() >>> 1) % segments)];
        lock.lock();
    }

    public void unlock(T key) {
        ReentrantLock lock = lockArray[((key.hashCode() >>> 1) % segments)];
        lock.unlock();
    }

}

package yuanjun.chen.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: CachedData
 * @Description: 来自Oracle官方的读写锁-写降级的demo
 * @author: 陈元俊
 * @date: 2018年8月15日 下午3:52:51
 */
public class CachedData {
    public static boolean STOP = false;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    Integer data = 0;
    volatile boolean cacheValid;

    public void setCacheValid(boolean isValid) {
        rwl.writeLock().lock();
        this.cacheValid = isValid;
        rwl.writeLock().unlock();
    }

    public void processCachedData() {
        while (!STOP) {
            rwl.readLock().lock();
            if (!cacheValid) { // 缓存失效了
                rwl.readLock().unlock(); // 欲写，必须关闭读锁，否则死锁，获不到！
                rwl.writeLock().lock(); // 写入获得写锁
                try {
                    if (!cacheValid) { // 这里模拟从远程获取数据并且更新cache
                        data++;
                        System.out.println("modified data = " + data);
                        cacheValid = true;
                    }
                    // Downgrade by acquiring read lock before releasing write lock
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock(); // Unlock write, still hold read
                }
            }
            try {
                System.out.println("data = " + data);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwl.readLock().unlock(); // 最终释放
            }
        }
    }
}

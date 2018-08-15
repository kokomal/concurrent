/**  
 * @Title: ReadWriteLockTest.java   
 * @Package: yuanjun.chen.lock   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年8月15日 下午5:34:26   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.lock;

import org.junit.Test;

/**   
 * @ClassName: ReadWriteLockTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年8月15日 下午5:34:26  
 */
public class ReadWriteLockTest {
    @Test
    public void testReadWriteLock() {
        testWriteLockDegrade(); // 会终止于第27s，考虑每一个process 100ms的耗时
    }
    
    /**   
     * @Title: testWriteLockDegrade   
     * @Description: 测试读写锁的写锁降级       
     * @return: void      
     * @throws   
     */
    private static void testWriteLockDegrade() {
        CachedData cd = new CachedData();
        cd.cacheValid = true;
        int len = 10;
        Thread[] ts = new Thread[len];
        for (int i = 0; i < len; i++) {
            ts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    cd.processCachedData();
                }
            });
            ts[i].start();
        }
        Thread t = new Thread(new Runnable() { // 专门invalid的线程, 1s置cache非法一次
            @Override
            public void run() {
                while (!CachedData.STOP) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i am begin to set cache invalid");
                    cd.setCacheValid(false); // 可以直接修改cacheValid
                    System.out.println("finished set cache invalid");
                }
            }
        });
        t.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        CachedData.STOP = true;
        System.out.println("all stopped");
    }
}

/**  
 * @Title: StampedLockTest.java   
 * @Package: yuanjun.chen.misc   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年8月13日 下午2:28:08   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.misc;

import java.util.concurrent.locks.StampedLock;
import org.junit.Test;

/**   
 * @ClassName: StampedLockTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年8月13日 下午2:28:08  
 */
public class StampedLockTest {
    @Test
    public void stampedLockTest() {
        StampedLock sl = new StampedLock();
    }
}

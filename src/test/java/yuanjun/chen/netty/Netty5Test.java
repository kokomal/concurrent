/**  
 * @Title: Netty5Test.java   
 * @Package: yuanjun.chen.netty   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年8月17日 下午1:30:20   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.netty;

import org.junit.Test;
import yuanjun.chen.netty.netty5.client.Netty5Client;
import yuanjun.chen.netty.netty5.client.Netty5MultiClient;
import yuanjun.chen.netty.netty5.server.Netty5Server;

/**   
 * @ClassName: Netty5Test   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年8月17日 下午1:30:20  
 */
public class Netty5Test {
    @Test
    public void testNetty5Single() throws Exception {
        int port = 11223;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Netty5Server.start(port);                
            }
            
        });
        t1.start();
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Netty5Client.start(port);                
            }
            
        });
        t2.start();
        
        t1.join();
        t2.join();
    }
    
    @Test
    public void testNetty5Multi() throws Exception {
        int port = 11223;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Netty5Server.start(port);                
            }
            
        });
        t1.start();
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Netty5MultiClient.start(5, port);                
            }
            
        });
        t2.start();
        
        t1.join();
        t2.join();
    }
}

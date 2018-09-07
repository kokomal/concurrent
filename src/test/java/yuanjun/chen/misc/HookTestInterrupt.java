/**
 * @Title: HookTestInterrupt.java
 * @Package: yuanjun.chen.misc
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月7日 下午3:58:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.misc;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HookTestInterrupt
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年9月7日 下午3:58:03
 */
public class HookTestInterrupt {
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute Hook.....");
            }
        }));
    }

    public static void main(String[] args) {
        new HookTestInterrupt().start();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                do {
                    System.out.println("thread is running....");
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }

        });
        thread.start();
    }
}

/**
 * @Title: PieData.java
 * @Package: yuanjun.chen.lock.readwrite
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月15日 下午3:06:35
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.lock.readwrite;

/**
 * @author : 陈元俊
 * @ClassName : PieData
 * @Description : TODO(这里用一句话描述这个类的作用)
 * @date : 2018年8月15日 下午3:06:35
 */
public class Pie {
    private String smell;

    public Pie(String smell) {
        this.smell = smell;
    }

    public void watch() throws Exception {
        System.out.println("begin watching it..." + this.smell);
        Thread.sleep(1000);
        System.out.println("end watching it..." + this.smell);
    }

    public String getSmell() {
        return this.smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }
}

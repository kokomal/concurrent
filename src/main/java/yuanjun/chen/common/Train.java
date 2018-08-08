/**
 * @Title: Train.java
 * @Package: yuanjun.chen.lock
 * @Description: 基本pojo
 * @author: 陈元俊
 * @date: 2018年7月30日 下午3:57:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.common;

import java.util.Random;
import java.util.UUID;

/**
 * @ClassName: Train
 * @Description: 基本pojo
 * @author: 陈元俊
 * @date: 2018年7月30日 下午3:57:03
 */
public class Train {
    private String name;
    private Float fuel;
    private int carriages;

    public static Train genUUTrain() {
        Random rd = new Random();
        return new Train(UUID.randomUUID().toString(), (float) (rd.nextFloat() % 1000.0), rd.nextInt(200));
    }

    public Train(String name, Float fuel, int carriages) {
        this.name = name;
        this.fuel = fuel;
        this.carriages = carriages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFuel() {
        return fuel;
    }

    public void setFuel(Float fuel) {
        this.fuel = fuel;
    }

    public int getCarriages() {
        return carriages;
    }

    public void setCarriages(int carriages) {
        this.carriages = carriages;
    }

    @Override
    public String toString() {
        return "Train [name=" + name + ", fuel=" + fuel + ", carriages=" + carriages + "]";
    }
}

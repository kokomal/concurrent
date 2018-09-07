package yuanjun.chen.disruptor;

import java.util.concurrent.CountDownLatch;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @ClassName: TradeTransactionPublisher
 * @Description: 创建一个发送消费事件 的类，用于发送要处理的消息
 * @author: 陈元俊
 * @date: 2018年9月7日 下午1:54:07
 */
public class TradeTransactionPublisher implements Runnable {
    Disruptor<TradeTransaction> disruptor;
    private CountDownLatch latch;
    private static int LOOP = 10;// 生产者发送10条消息

    public TradeTransactionPublisher(CountDownLatch latch, Disruptor<TradeTransaction> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        // TradeTransactionEventTranslator tradeTransloator = new TradeTransactionEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            TradeTransactionEventTranslator tradeTransloator = new TradeTransactionEventTranslator(i);
            System.out.println("生产者成功向Ringbuffer中发送了一条消息[" + i + "]");
            disruptor.publishEvent(tradeTransloator);
        }
        latch.countDown();
    }
}

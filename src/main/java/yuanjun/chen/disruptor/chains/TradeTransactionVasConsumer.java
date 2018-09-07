package yuanjun.chen.disruptor.chains;

import com.lmax.disruptor.EventHandler;
import yuanjun.chen.disruptor.TradeTransaction;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {
    private String tag;
    
    /**
     * @param tag
     */
    public TradeTransactionVasConsumer(String tag) {
        super();
        this.tag = tag;
    }

    /** 消费者C1接到消息后的业务处理逻辑. */
    @Override
    public void onEvent(TradeTransaction ta, long arg1, boolean arg2) throws Exception {
        // do something....
        System.out.println("Tag-" + tag + " handles " + ta.getId());
    }
}

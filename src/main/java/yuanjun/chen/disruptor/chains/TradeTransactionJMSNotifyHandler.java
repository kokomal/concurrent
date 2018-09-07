package yuanjun.chen.disruptor.chains;
 import com.lmax.disruptor.EventHandler;
import yuanjun.chen.disruptor.TradeTransaction;

public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction>{
 	/** 消费者C3接到消息后的业务处理逻辑. */
	@Override
	public void onEvent(TradeTransaction ta, long arg1, boolean arg2) throws Exception {
		//do send jms message  
		System.out.println("sending the UUID " + ta.getId());
	}
 }

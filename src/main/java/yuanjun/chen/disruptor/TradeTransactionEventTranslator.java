package yuanjun.chen.disruptor;

import java.util.Random;
import com.lmax.disruptor.EventTranslator;

public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private int i;
    private Random random = new Random();

    /**
     * @param i
     */
    public TradeTransactionEventTranslator(int i) {
        super();
        this.i = i;
    }

    @Override
    public void translateTo(TradeTransaction event, long sequence) {
        this.generateTradeTransaction(i, event);
    }

    private TradeTransaction generateTradeTransaction(int i, TradeTransaction trade) {
        trade.setId(String.valueOf(i));
        trade.setPrice(random.nextDouble() * 9999);
        return trade;
    }
}

package yuanjun.chen.disruptor;

public class TradeTransaction {
    /** 交易ID. */
    private String id;
    /** 交易金额. */
    private double price;

    public TradeTransaction() {}

    public TradeTransaction(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

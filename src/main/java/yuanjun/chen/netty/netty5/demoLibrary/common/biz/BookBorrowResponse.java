/**
 * @Title: BookBorrowResponse.java
 * @Package: yuanjun.chen.nio.demoLibrary.common.biz
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月31日 下午1:49:50
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.common.biz;

import java.util.List;
import yuanjun.chen.netty.netty5.demoLibrary.common.serial.AbstractCommonSerializer;

/**
 * @ClassName: BookBorrowResponse
 * @Description: 借书业务的返回pojo
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年8月31日 下午1:49:50
 */
public class BookBorrowResponse extends AbstractCommonSerializer {
    /** 用户名. */
    private String userName;
    /** 书列表. */
    private List<String> bookNames;
    private List<String> bookSNs;
    private long borrowTime;
    private long returnTime;

    public List<String> getBookNames() {
        return bookNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBookNames(List<String> bookNames) {
        this.bookNames = bookNames;
    }

    public List<String> getBookSNs() {
        return bookSNs;
    }

    public void setBookSNs(List<String> bookSNs) {
        this.bookSNs = bookSNs;
    }

    public long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(long borrowTime) {
        this.borrowTime = borrowTime;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    protected void read() {
        this.userName = readString();
        this.bookNames = readList(String.class);
        this.bookSNs = readList(String.class);
        this.borrowTime = readLong();
        this.returnTime = readLong();
    }

    @Override
    protected void write() {
        writeString(this.userName);
        writeList(this.bookNames);
        writeList(this.bookSNs);
        writeLong(this.borrowTime);
        writeLong(this.returnTime);
    }

    @Override
    public String toString() {
        return "BookBorrowResponse [userName=" + userName + ", bookNames=" + bookNames + ", bookSNs=" + bookSNs
                + ", borrowTime=" + borrowTime + ", returnTime=" + returnTime + "]";
    }
    
}

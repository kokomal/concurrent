/**
 * @Title: BookBorrowRequest.java
 * @Package: yuanjun.chen.nio.demoLibrary.common.biz
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月31日 下午1:47:07
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.common.biz;

import java.util.List;
import yuanjun.chen.netty.netty5.demoLibrary.common.serial.AbstractCommonSerializer;

/**
 * @ClassName: BookBorrowRequest
 * @Description: 借书业务的请求pojo
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年8月31日 下午1:47:07
 */
public class BookBorrowRequest extends AbstractCommonSerializer {
    private String userName;

    private List<String> bookNames;

    public List<String> getBookNames() {
        return bookNames;
    }

    public void setBookNames(List<String> bookNames) {
        this.bookNames = bookNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected void read() {
        this.userName = readString();
        this.bookNames = readList(String.class);
    }

    @Override
    protected void write() {
        writeString(this.userName);
        writeList(this.bookNames);
    }

    @Override
    public String toString() {
        return "BookBorrowRequest [userName=" + userName + ", bookNames=" + bookNames + "]";
    }
}

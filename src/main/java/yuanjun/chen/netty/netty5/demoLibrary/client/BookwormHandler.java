/**
 * @Title: BookwormHandler.java
 * @Package: yuanjun.chen.nio.demoLibrary.client
 * @Description: 客户端的handler
 * @author: 陈元俊
 * @date: 2018年9月4日 下午3:52:22
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.client;

import java.util.Date;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import yuanjun.chen.netty.netty5.demoLibrary.common.biz.BookBorrowResponse;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericResponseDto;

/**
 * @ClassName: BookwormHandler
 * @Description: 客户端（书虫）的handler
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年9月4日 下午3:52:22
 */
public class BookwormHandler extends SimpleChannelInboundHandler<GenericResponseDto> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GenericResponseDto msg) throws Exception {
        int module = msg.getModule();
        int command = msg.getCommand();
        System.out.println("module=" + module + " command=" + command);
        BookBorrowResponse bookResp = new BookBorrowResponse();
        bookResp.readFromBytes(msg.getBizData());
        Date dtBorrow = new Date(bookResp.getBorrowTime());
        Date dtReturn = new Date(bookResp.getReturnTime());
        System.out.println("fetch book! --- " + bookResp.toString() + " borrowd @ " + dtBorrow + " and should return @ "
                + dtReturn);
    }

}

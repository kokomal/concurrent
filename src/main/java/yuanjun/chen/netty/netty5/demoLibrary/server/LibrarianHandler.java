/**
 * @Title: LibrarianHandler.java
 * @Package: yuanjun.chen.nio.demoLibrary.server
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月4日 下午3:29:57
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.netty.netty5.demoLibrary.server;

import java.util.ArrayList;
import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import yuanjun.chen.netty.netty5.demoLibrary.common.biz.BookBorrowRequest;
import yuanjun.chen.netty.netty5.demoLibrary.common.biz.BookBorrowResponse;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericRequestDto;
import yuanjun.chen.netty.netty5.demoLibrary.common.dto.GenericResponseDto;

/**
 * @ClassName: LibrarianHandler
 * @Description: 服务端（图书馆）的handler
 * @author: 陈元俊
 * @SpecialThanksTo -琴兽-
 * @date: 2018年9月4日 下午3:29:57
 */
public class LibrarianHandler extends SimpleChannelInboundHandler<GenericRequestDto> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("entering into the library hall");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception caught! " + cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GenericRequestDto msg) throws Exception {
        short module = msg.getModule();
        short command = msg.getCommand();
        BookBorrowRequest borrowReq = new BookBorrowRequest();
        borrowReq.readFromBytes(msg.getBizData());
        System.out.println("Library get req--" + borrowReq.toString());

        BookBorrowResponse libResp = new BookBorrowResponse();
        libResp.setBookNames(borrowReq.getBookNames());
        List<String> sns = generateSns();
        libResp.setBookSNs(sns );
        libResp.setBorrowTime(System.currentTimeMillis());
        libResp.setReturnTime(System.currentTimeMillis() + 1000 * 3600 * 24);
        libResp.setUserName(borrowReq.getUserName());
        
        GenericResponseDto ret = new GenericResponseDto();
        ret.setCommand(command);
        ret.setModule(module);
        ret.setBizData(libResp.getBytes());
        ctx.channel().writeAndFlush(ret);
    }

    private List<String> generateSns() {
        List<String> sns = new ArrayList<String>(); // mock 
        sns.add("1001");
        sns.add("1002");
        return sns;
    }

}

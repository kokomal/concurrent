package yuanjun.chen.netty.netty5.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端消息处理
 * 
 * @SpecialThanksTo -琴兽-
 *
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("客户端收到消息:" + msg);
    }

}

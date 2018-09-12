package yuanjun.chen.netty.netty5.sticky.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName: SocketClientHandler
 * @Description: 粘包的粗暴解决方案
 * @author: 陈元俊
 * @SpecialThanksTo: Sam哥哥
 * @date: 2018年9月12日 下午5:27:33
 */
public class SocketClientHandler extends ChannelInboundHandlerAdapter {
    private int counter;
    private byte[] req;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // old code 导致粘包
        /*
         * ByteBuf buf = (ByteBuf) msg; byte[] req = new byte[buf.readableBytes()];
         * buf.readBytes(req); String body = new String(req, "UTF-8");
         */
        String body = (String) msg;
        System.out.println("Now is : " + body + " ; the counter is :" + ++counter);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    public SocketClientHandler() {
        req = ("QUERY TIME ORDER" + "\n").getBytes();
    }
}

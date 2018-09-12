package yuanjun.chen.netty.netty5.sticky.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class SocketServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
         * ByteBuf buf = (ByteBuf) msg; byte[] req = new byte[buf.readableBytes()];
         * System.out.println("req len = " + req.length); // 将buf中的数据读取到req中 buf.readBytes(req);
         * 
         * // 打印客户端发送的数据 String body = new String(req, "UTF-8").substring(0, req.length - 1);
         */
        String body = (String) msg;
        
        System.out.println("server receive order:" + body + ";the counter is:" + ++counter);

        String currentTime = "";
        if ("QUERY TIME ORDER".equalsIgnoreCase(body)) {
            currentTime = new Date(System.currentTimeMillis()).toString();
        } else {
            currentTime = "BAD ORDER";
        }

        String separator = "\n";
        currentTime = currentTime + separator;

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }
}

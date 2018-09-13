package yuanjun.chen.netty.netty5.heartbeat2;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class BaseServerHandler extends ChannelHandlerAdapter {
    private int loss_connect_time;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("hehehehe");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) { // 服务端长时间没有收到客户端的写，说明远端的客户端idle了
                loss_connect_time++;
                System.out.println("10 秒没有接收到客户端的信息了");
                if (loss_connect_time > 2) {
                    System.out.println("Begin To CutDown Client！！！");
                    ctx.channel().close(); // 将远程channel踢下线
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // 大并发情况下，服务端就不必相应了
        System.out.println("server channelRead..");
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

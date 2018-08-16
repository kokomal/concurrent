package yuanjun.chen.netty.parody.worker;

import java.nio.channels.SocketChannel;

/**
 * Worker接口.
 * 
 * @SpecialThanksTo -琴兽-
 */
public interface Worker {
    /**
     * 加入一个新的客户端会话.
     * 
     * @param channel
     */
    void registerNewChannelTask(SocketChannel channel);
}

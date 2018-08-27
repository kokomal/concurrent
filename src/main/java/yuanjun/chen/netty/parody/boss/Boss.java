package yuanjun.chen.netty.parody.boss;

import java.nio.channels.ServerSocketChannel;

/**
 * Boss接口.
 *
 * @SpecialThanksTo -琴兽-
 */
public interface Boss {
    /**
     * 加入一个新的ServerSocket.
     *
     * @param serverChannel
     */
    void registerAcceptChannelTask(ServerSocketChannel serverChannel);
}

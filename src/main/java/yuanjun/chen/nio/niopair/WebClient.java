package yuanjun.chen.nio.niopair;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WebClient {
    private static final Logger logger = LogManager.getLogger(WebClient.class);

    public static void start(String content, int port) throws Exception {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", port));

            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            ByteBuffer readBuffer = ByteBuffer.allocate(128);

            writeBuffer.put(content.getBytes());
            writeBuffer.flip();

            do {
                Thread.sleep(1000);
                writeBuffer.rewind();
                while (writeBuffer.hasRemaining()) {
                    socketChannel.write(writeBuffer);
                }
                readBuffer.clear();
                socketChannel.read(readBuffer);
                readBuffer.flip();
                logger.info("client get " + new String(readBuffer.array()));
            } while (true);
        } catch (IOException e) {
            logger.info("eerr" + e);
        }
    }
}

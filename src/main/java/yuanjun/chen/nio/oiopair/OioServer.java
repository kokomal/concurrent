package yuanjun.chen.nio.oiopair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: OioServer
 * @Description: 传统IO的服务端,为每一个新连入的客户端开辟一个线程
 * @author: 陈元俊
 * @date: 2018年8月14日 下午1:37:08
 */
public class OioServer {
    private volatile boolean stop = false;

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @SuppressWarnings("resource")
    public void startAtPort(int port) throws Exception {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 创建socket服务,监听port端口
        ServerSocket server = new ServerSocket(port);
        System.out.println("服务器启动！");
        while (true) {
            // 获取一个套接字（阻塞）
            final Socket socket = server.accept(); // 如果需要强行停止，有比较丑陋的方法，即手动连接一次，跳出循环
            if (stop) {
                System.out.println("should break");
                break;
            }
            System.out.println("来个一个新客户端！");
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 业务处理
                    try {
                        handle(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @SuppressWarnings("resource")
    public void stopThread(int port){  
        this.stop = true;  
        try {  
            new Socket("localhost",port);  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 读取数据.
     * 
     * @param socket
     * @throws Exception
     */
    public static void handle(Socket socket) throws Exception {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        System.out.println("Accept Client: " + reader.readLine()); // 握手
        int limit = 99;
        while (true) {
            Thread.sleep(10);
            limit--;
            if (limit == 0) {
                line = "stop";
                writer.println(line); // 向client写
                writer.flush();
                break;
            }
            line = "Today the magic key is-" + UUID.randomUUID().toString();
            writer.println(line); // 向client写
            writer.flush();
            System.out.println("From Client: " + reader.readLine()); // 写完后从client读
        }
        writer.close(); // 关闭Socket输出流
        reader.close(); // 关闭Socket输入流
        socket.close(); // 关闭Socket
        System.out.println("server handler finished");
    }
}

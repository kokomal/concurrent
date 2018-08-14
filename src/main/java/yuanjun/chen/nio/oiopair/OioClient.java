/**
 * @Title: OioClient.java
 * @Package: yuanjun.chen.nio.oiopair
 * @Description: 传统IO的客户端
 * @author: 陈元俊
 * @date: 2018年8月14日 下午1:42:16
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.nio.oiopair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName: OioClient
 * @Description: 传统IO的客户端
 * @author: 陈元俊
 * @date: 2018年8月14日 下午1:42:16
 */
public class OioClient {
    public void connectAtPort(String url, int port) throws Exception {
        try {
            Socket socket = new Socket(url, port);
            System.out.println("客户端启动成功");
            PrintWriter write = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readline = "hi there!";
            write.println(readline); // client向server写，打招呼
            write.flush();
            while (true) { // 正式开始读写
                readline = in.readLine();
                if ("stop".equals(readline))
                    break;
                System.out.println("From Server: " + readline); // 写完后读取Server的信息
                write.println(readline + " CONSUMED"); // client向server写
                write.flush();
            }
            write.close(); // 关闭Socket输出流
            in.close(); // 关闭Socket输入流
            socket.close(); // 关闭Socket
            System.out.println("client handler finished");
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }
    }
}

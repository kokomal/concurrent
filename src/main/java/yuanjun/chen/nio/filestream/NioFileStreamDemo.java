/**
 * 
 */
package yuanjun.chen.nio.filestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * @author hp
 *
 */
public class NioFileStreamDemo {
	/**
	 * 从原始路径拷贝文件到新路径 如果新路径不存在，则新建，已有，则尾部添加
	 */
	@SuppressWarnings("resource")
	public static void nioCopyFile(String srcFile, String destFile) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile, true); // appendable
		FileChannel readChannel = fis.getChannel();
		FileChannel writeChannle = fos.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			int len = readChannel.read(buffer);
			if (len == -1) {
				break;
			}
			buffer.flip();
			writeChannle.write(buffer);
		}
		readChannel.close();
		writeChannle.close();
		fis.close();
		fos.close();
	}

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("wordsworth.txt");
		String serial = UUID.randomUUID().toString();
		nioCopyFile(url.getFile(), "d://masterpiece" + serial + ".txt");
	}
}

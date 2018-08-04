/**
 * 
 */
package yuanjun.chen.nio.filestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
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

	/**
	 * 官方demo
	 * 
	 * @throws Exception
	 */
	public static void readViaNio(String srcFile) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile(srcFile, "rw");
		FileChannel inChannel = aFile.getChannel();

		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {

			System.out.println("Read " + bytesRead);
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}

			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("wordsworth.txt");
		String serial = UUID.randomUUID().toString();
		//nioCopyFile(url.getFile(), "d://masterpiece" + serial + ".txt");
	
		readViaNio(url.getFile());
	
	}
}

/**
 * 
 */
package yuanjun.chen.nio.filestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		System.out.println(readChannel.size()); // 输出readChannel对应的文件的大小（Byte）
		System.out.println(writeChannle.size()); // 输出readChannel对应的文件的大小（Byte）
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
	
	@SuppressWarnings("resource")
    public static void scatterTest(String srcFile) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile(srcFile, "rw");
		FileChannel inChannel = aFile.getChannel();

		ByteBuffer header = ByteBuffer.allocate(64);
		ByteBuffer body   = ByteBuffer.allocate(64);

		ByteBuffer[] bufferArray = { header, body };

		inChannel.read(bufferArray);
		
		String val1 = new String(bufferArray[0].array(), "utf-8");
		String val2 = new String(bufferArray[1].array(), "utf-8");
		System.out.println(val1 + "---" + val2);
	}
	
	public static void gatherTest(String destFile) throws Exception {
		ByteBuffer header = ByteBuffer.wrap("基督山恩仇录".getBytes("utf-8"));
		ByteBuffer body = ByteBuffer.wrap("大仲马作品".getBytes("utf-8"));

		ByteBuffer[] bufferArray = { header, body };
		FileOutputStream fos = new FileOutputStream(destFile, true); // appendable
		FileChannel writeChannle = fos.getChannel();
		writeChannle.write(bufferArray);
		fos.close();
		writeChannle.close();
	}

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("wordsworth.txt");
		String serial = UUID.randomUUID().toString();
		nioCopyFile(url.getFile(), "d://masterpiece" + serial + ".txt");
	
		//readViaNio(url.getFile());
	
		//scatterTest(url.getFile());
		
		//gatherTest("d://masterpiece.txt");
	}
}

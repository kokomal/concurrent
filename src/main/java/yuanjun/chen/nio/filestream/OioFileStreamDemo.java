package yuanjun.chen.nio.filestream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * @author hp
 */
public class OioFileStreamDemo {
	public static void oioCopyFile(String srcFile, String destFile) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile, true); // appendable
		byte[] buf = new byte[512];
		do {
			if (fis.read(buf) != -1) {
				System.out.println(new String(buf, "utf-8"));
				fos.write(buf);
				buf = new byte[512];
			} else {
				break;
			}
		} while (true);
		fis.close();
		fos.close();
	}

	public static void oioReadline(String srcFile, String destFile) throws Exception {
		InputStreamReader read = new InputStreamReader(new FileInputStream(srcFile), "UTF-8");
		BufferedReader reader = new BufferedReader(read);
		FileOutputStream fos = new FileOutputStream(destFile, true); // appendable
		String line;
		String fileContent = "";
		try {
			// 循环，每次读一行
			while ((line = reader.readLine()) != null) {
				fileContent += line;
				System.out.println(line);
				fos.write((line + "\r\n").getBytes());
			}
		} finally {
			reader.close();
			read.close();
			fos.close();
		}
		System.out.println("===========");
		System.out.println(fileContent);
	}

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("wordsworth.txt");
		String serial = UUID.randomUUID().toString();
		//oioCopyFile(url.getFile(), "d://masterpiece" + serial + ".txt");
		oioReadline(url.getFile(), "d://masterpiece" + serial + ".txt");
	}
}

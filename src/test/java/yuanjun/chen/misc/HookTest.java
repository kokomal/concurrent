package yuanjun.chen.misc;
 
import java.util.concurrent.TimeUnit;
 
/**   
 * @ClassName: HookTest   
 * @Description: 测试JVM异常关闭时的hook机制是否激活，在正常退出的情况下，hook机制一定可以激活  
 * @author: 陈元俊 
 * @date: 2018年9月7日 下午3:55:35  
 */
public class HookTest
{
	public void start()
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run()
			{
				System.out.println("Execute Hook.....");
			}
		}));
	}
	
	public static void main(String[] args)
	{
		new HookTest().start();
		System.out.println("The Application is doing something");
		try
		{
			TimeUnit.MILLISECONDS.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}

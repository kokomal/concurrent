package yuanjun.chen.curator.session;

import org.apache.zookeeper.data.Stat;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 14:16
 */
public class GetNodeDataSample extends BaseSessionHolder {

	public static void main(String[] args) throws Exception {
		client.start();
		Stat stat = new Stat();
		String data = new String(client.getData().storingStatIn(stat).forPath(PATH));
		System.out.println(String.format("data:%s,stat:%s", data, stat));
		Thread.sleep(Integer.MAX_VALUE);
	}
}

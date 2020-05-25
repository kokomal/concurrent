package yuanjun.chen.curator.session;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 14:39
 */
public class DeleteNodeSample extends BaseSessionHolder {

	public static void main(String[] args)throws Exception{
		client.start();
		client.delete().deletingChildrenIfNeeded().forPath(PATH);
//		byte[] bytes = client.getData().forPath(PATH);
//		System.out.println(new String(bytes));
	}
}

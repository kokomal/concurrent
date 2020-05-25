package yuanjun.chen.curator.session;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 12:31
 */
public abstract class BaseSessionHolder extends BaseConnectionInfo{

	protected static String PATH = "/zk-book/c1";

	protected static CuratorFramework client =
			CuratorFrameworkFactory.builder()
					.connectString(connectionInfo)
					.connectionTimeoutMs(50000)
					.sessionTimeoutMs(50000)
					.retryPolicy(new ExponentialBackoffRetry(1000,3))
					.build();
}

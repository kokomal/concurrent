package yuanjun.chen.curator.session;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 12:01
 */
public class CreateSessionSample extends BaseConnectionInfo {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.newClient(
                        connectionInfo,
                        5000,
                        3000,
                        retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

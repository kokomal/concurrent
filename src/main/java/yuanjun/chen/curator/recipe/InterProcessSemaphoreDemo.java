package yuanjun.chen.curator.recipe;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/11 0:30
 */
public class InterProcessSemaphoreDemo {

    private static final int MAX_LEASE = 10; // 最大租约
    private static final String PATH = "/examples/locks";

    // 展现了semaphore的精细使用
    public static void main(String[] args) throws Exception {
        FakeLimitedResource resource = new FakeLimitedResource();
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();
            InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, PATH, MAX_LEASE);
            Collection<Lease> leases = semaphore.acquire(5); // 申请多个
            System.out.println("get " + leases.size() + " leases");
            Lease lease = semaphore.acquire(); // 再获得一个，此时还剩下4个
            System.out.println("get another lease");
            resource.use();
            Collection<Lease> leases2 = semaphore.acquire(5, 10, TimeUnit.SECONDS); // 如果10s内获得不到，返回null
            System.out.println("Should timeout and acquire return " + leases2);
            System.out.println("return one lease");
            semaphore.returnLease(lease); // return 1个租约
            System.out.println("return another 5 leases");
            semaphore.returnAll(leases); // 还掉当前占用的所有租约
        }
    }
}

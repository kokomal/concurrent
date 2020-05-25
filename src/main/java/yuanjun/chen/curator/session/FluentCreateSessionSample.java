package yuanjun.chen.curator.session;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 12:19
 */
public class FluentCreateSessionSample extends BaseConnectionInfo {

    public static void main(String[] args) throws Exception {
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                        .namespace("base")
                        .build();
        client.start();
        //创建节点
//        client.create().forPath("/path");
        client.create().forPath("/path", "init".getBytes());
//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/path");
//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/path", "init".getBytes());
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/path/to/rome", "init".getBytes());
        //删除节点
//        client.delete().forPath("/path");
//        client.delete().deletingChildrenIfNeeded().forPath("/path");
//        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(10086).forPath("path");
//        client.delete().guaranteed().forPath("/path");
//        //读取数据
//        client.getData().forPath("path");
//        Stat stat = new Stat();
//        client.getData().storingStatIn(stat).forPath("/path");
//
//        //更新(设置)数据
//        client.setData().forPath("/path", "data".getBytes());
//        client.setData().withVersion(10086).forPath("/path", "data".getBytes());

        //检查是否存在
        Stat ext = client.checkExists().forPath("/path");
        System.out.println(ext);
        //获取所有的子节点
        List<String> v = client.getChildren().forPath("/path");
        System.out.println(v);
        //原子事务
        CuratorOp op1 = client.transactionOp().create().withMode(CreateMode.EPHEMERAL).forPath("/path/to/china", "data".getBytes());
        CuratorOp op2 = client.transactionOp().setData().forPath("/path/to/china", "databb".getBytes());

        Collection<CuratorTransactionResult> results = client.transaction().forOperations(op1, op2);

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType() + "-" + result.getResultStat());

        }

        Stat stat = new Stat();
        byte[] val = client.getData().storingStatIn(stat).forPath("/path/to/china");

        System.out.println(new String(val));

//
//        //异步化Api
        Executor executor = Executors.newFixedThreadPool(2);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {
                    System.out.println(String.format("eventType:%s,resultCode:%s", curatorEvent.getType(), curatorEvent.getResultCode()));
                }, executor)
                .forPath("/path/to/moon", "haha".getBytes());
//        Thread.sleep(Integer.MAX_VALUE);
        byte[] val2 = client.getData().storingStatIn(stat).forPath("/path/to/moon");

        System.out.println(new String(val2));
    }
}

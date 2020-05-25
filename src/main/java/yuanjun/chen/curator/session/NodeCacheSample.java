package yuanjun.chen.curator.session;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/22 23:17
 */
public class NodeCacheSample extends BaseSessionHolder {

    public static void main(String[] args) throws Exception {
        client.start();
        String path = "/root";
        final TreeCache treeCache = new TreeCache(client, path);
        treeCache.getListenable()
                .addListener((curatorFramework, treeCacheEvent) -> {
                    System.out.println(String.format("treeCacheEvent:%s,childData:%s", treeCacheEvent.getType(), new String(treeCacheEvent.getData().getData())));
                });
        treeCache.start();

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("PathChildrenCache changed! " + pathChildrenCacheEvent.getType());
            }
        });
        pathChildrenCache.start();

        final NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NODE-CACHE changed!");
            }
        });
        nodeCache.start();

        Stat stat = client.checkExists().forPath(path);
        if (stat != null) {
            client.delete().forPath(path);
        }
        client.create().withMode(CreateMode.PERSISTENT).forPath(path, "initData".getBytes());
        Thread.sleep(2000);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path + "/child", "initChildData".getBytes());
        Thread.sleep(2000);
        client.setData().forPath(path, "modifyInitData".getBytes());
        client.setData().forPath(path + "/child", "modifyInitChildData".getBytes());
        Thread.sleep(2000);
        client.delete().forPath(path + "/child");
        client.delete().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

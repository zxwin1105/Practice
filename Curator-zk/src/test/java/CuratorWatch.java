import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryOneTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author: zhaixinwei
 * @Date: 2022/5/11
 * @Description: watch事件监听
 */
public class CuratorWatch {




    private CuratorFramework client;

    /**
     * 使用连式编程来与ZK建立连接 方法二
     */
    @Before
    public void connect2(){
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.245.100:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(new RetryOneTime(100))
//                .namespace("zxw") // 添加命名空间，后续所有默认的操作都会在命名空间上进行。eg:
                .build();
        client.start();
    }

    /**
     * 单节点监听
     */
    @Test
    public void nodeCache() throws Exception {
        /*
         * NodeCache构造参数
         * @param: CuratorFramework 客户端
         * @param: path 添加监视器的节点path
         * @param: dataIsCompressed 是否压缩数据
         */
        NodeCache nodeCache = new NodeCache(client,"/new");
        // 注册监听事件
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                // 获取修改节点后的状态、数据
                byte[] data = nodeCache.getCurrentData()
                        .getData();
                System.out.println(new String(data));
            }
        });
        // 开启监听
        nodeCache.start(true);
        while (true) {}
    }

    /**
     * 监听子节点事件
     */
    @Test
    public void pathChildrenCache() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,"/zxw",true);
        // 绑定监听器
        pathChildrenCache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            System.out.println("change");
            System.out.println(pathChildrenCacheEvent); // 节点变化事件
            if (pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                System.out.print(pathChildrenCacheEvent.getData().getPath()+"节点数据变化:");
                System.out.println(new String(pathChildrenCacheEvent.getData().getData()));
            }
        });
        // 开启监听
        pathChildrenCache.start();
        while(true){}
    }

    /**
     * 监听某个节点和所有子节点
     */
    @Test
    public void treeCache() throws Exception {
        TreeCache treeCache = new TreeCache(client,"/zxw");
        treeCache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
            System.out.println(treeCacheEvent);
        });
        treeCache.start();
        while(true){}
    }

    @After
    public void destroy(){
        client.close();
    }
}

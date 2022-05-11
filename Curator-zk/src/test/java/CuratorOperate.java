import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: zhaixinwei
 * @Date: 2022/5/10
 * @Description: 通过API操作ZK服务
 */
public class CuratorOperate {

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
                .namespace("zxw") // 添加命名空间，后续所有默认的操作都会在命名空间上进行。eg:
                .build();
        client.start();
    }

    @Test
    public void createNode() throws Exception {
        // 创建节点，如果没有指定节点内容，会默认将当前客户端IP作为数据存储
        String s = client.create()
                .forPath("/javaAPI");
        // 创建节点，添加节点内容
        client.create()
                .forPath("/javaAPI1","javaAPI".getBytes(StandardCharsets.UTF_8));
        // 节点类型，默认是持久化类型
        client.create()
                .withMode(CreateMode.EPHEMERAL) // 配置节点类型
                .forPath("/javaAPI1","javaAPI".getBytes(StandardCharsets.UTF_8));
        // 创建多级节点
        client.create()
                .creatingParentsIfNeeded() // 如果父节点不存在，创建父节点
                .forPath("/father/son");
    }

    /**
     * 获取节点信息
     * - 查询数据：get /node
     * - 查询子节点：ls /
     * - 查询节点状态信息：ls -s /
     */
    @Test
    public void search() throws Exception {
        // 查询数据
        byte[] zxwData = client.getData()
                .forPath("/father/son");
        System.out.println(new String(zxwData));

        // 查询子节点
        List<String> strings = client.getChildren()
                .forPath("/");
        System.out.println(strings);

        // 查询节点信息
        Stat stat = new Stat(); // 用于存储接节点信息
        client.getData()
                .storingStatIn(stat) // 将节点信息存入stat
                .forPath("/");
        System.out.println(stat);
    }

    /**
     * 修改节点数据
     */
    @Test
    public void setData() throws Exception {
        client.setData().forPath("/father/son","100".getBytes(StandardCharsets.UTF_8));
    }

    /**
     *  修改数据存在的问题：当有多个客户端连接时，可能造成数据修改混乱。当客户端A查询完数据，客户端B修改了节点数据，
     *  当客户端A去修改节点数据是，发现数据发生变化。
     *  解决问题办法，根据版本来修改节点数据
     */
    @Test
    public void setDataForVersion() throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat)
                .forPath("/father/son");
        int version = stat.getVersion();
        System.out.println(version);
        client.setData().withVersion(version)
            .forPath("/father/son","100".getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 删除节点
     */
    @Test
    public void deleteData() throws Exception {
        client.delete()
                .deletingChildrenIfNeeded() // 删除带子节点的节点
                .forPath("/father");
        // 配置必然删除成功
        client.delete()
                .guaranteed()
                .forPath("/father/son");

        // 添加删除操作后的回调
        client.delete()
                .inBackground(((curatorFramework, curatorEvent) -> {
                    System.out.println(curatorEvent);
                }))
                .forPath("/father/son");
    }
    @After
    public void destroy(){
        client.close();
    }
}

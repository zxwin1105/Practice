import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;

/**
 * @Author: zhaixinwei
 * @Date: 2022/5/10
 * @Description: 连接ZK服务操作
 */
public class CuratorConnect {

    /**
     * 连接ZK服务器 方法一
     */
    @Test
    public void connect1(){
        /*
         * newClient参数说明
         * @param connectString zk服务连接字符串，指定zk服务器ip:port
         * @param sessionTimeoutMs 会话超时时间
         * @param connectionTimeoutMs 连接超时时间
         * @param retryPolicy 重试策略
         */
        // 创建连接ZK客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.245.100:2181",
                60*1000,
                15*1000,
               new ExponentialBackoffRetry(1000,10));
        // 客户端与ZK服务建立连接
        client.start();
    }

    /**
     * 使用连式编程来与ZK建立连接 方法二
     */
    @Test
    public void connect2(){
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.245.100:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(new RetryOneTime(100))
                .namespace("zxw") // 添加命名空间，后续所有默认的操作都会在命名空间上进行。eg:
                .build();
        client.start();
    }
}


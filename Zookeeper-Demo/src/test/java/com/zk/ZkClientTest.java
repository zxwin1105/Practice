package com.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 客户端连接相关
 * @author zhaixinwei
 * @date 2022/9/14
 */
public class ZkClientTest {

    private static final String ZK_SERVER_ADDRESS = "192.168.56.11:2181";

    private static final int SESSION_TIMEOUT = 5000;

    private ZooKeeper zookeeper = null;

    private static final CountDownLatch COUNTDOWNLATCH = new CountDownLatch(1);

    @Before
    public void init_zk_instant() throws IOException, InterruptedException {
        /* 在客户端成功连接zk服务后，会给客户端发送一个通知，可以使用Watch接收 */
        this.zookeeper = new ZooKeeper(ZK_SERVER_ADDRESS,SESSION_TIMEOUT,event->{
            if(Watcher.Event.KeeperState.SyncConnected.equals(event.getState())){
                System.out.println("zk server connected success");
            }else{
                System.out.println("zk server connected failed");
            }
            COUNTDOWNLATCH.countDown();
        });
        COUNTDOWNLATCH.await();
    }

    @Test
    public void sync_create_znode() throws KeeperException, InterruptedException {
        // 同步方式创建znode
        String val = zookeeper.create("/client", "client_data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(val);

        // 获取znode data
        byte[] data = zookeeper.getData("/client", event -> {
            if (Watcher.Event.EventType.NodeDataChanged.equals(event.getType())) {
                System.out.println("NodeChildrenChanged");
            }
        }, null);
        System.out.println(new String(data));
        TimeUnit.SECONDS.sleep(100);
    }

}

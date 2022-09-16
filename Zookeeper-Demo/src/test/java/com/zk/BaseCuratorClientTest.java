package com.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Test;

/**
 * curator API使用
 * @author zhaixinwei
 * @date 2022/9/15
 */
public class BaseCuratorClientTest {
    private static final String ZK_SERVER_ADDRESS = "192.168.56.11:2181";
    private static final CuratorFramework CURATOR;

    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(ZK_SERVER_ADDRESS)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .namespace("curator")
                .build();
        curatorFramework.start();
        CURATOR = curatorFramework;
    }

    public CuratorFramework getCurator(){
        return CURATOR;
    }
}

package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

/**
 * @author zhaixinwei
 * @date 2022/9/15
 */
public class CuratorClientTest extends BaseCuratorClientTest{

    @Test
    public void create_znode_test() throws Exception {
        CuratorFramework curator = getCurator();
        String path = curator.create().creatingParentsIfNeeded().forPath("/e1/cre");
        System.out.println(path);
    }

    @Test
    public void set_data_for_znode() throws Exception {
        CuratorFramework curator = getCurator();
        curator.setData().forPath("");

    }
}

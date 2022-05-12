package distributionLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhaixinwei
 * @date: 2022/5/11
 * @description: 售票服务，多个客户端买票时，需要通过ZK实现分布式事务锁，以保证资源安全
 */
public class TicketServer implements Runnable{

    private int ticket = 10;

    private InterProcessLock lock;

    public TicketServer(){
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.245.100:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(new RetryOneTime(100))
                .namespace("ticket") // 添加命名空间，后续所有默认的操作都会在命名空间上进行。eg:
                .build();
        client.start();

        lock = new InterProcessMutex(client,"/lock");
    }

    @Override
    public void run() {
        while(true){
            try {
                lock.acquire(3,TimeUnit.SECONDS);

                if(ticket>0){
                    try {
                        TimeUnit.MICROSECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ticket--;
                    System.out.println(Thread.currentThread().getName()+"购票成功。剩余票数："+ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

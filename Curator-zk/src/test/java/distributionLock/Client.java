package distributionLock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: zhaixinwei
 * @date: 2022/5/11
 * @description: 购票客户端，模拟用户购票
 */
public class Client {

    public static void main(String[] args) {
        TicketServer ticketServer = new TicketServer();

        Thread t1 = new Thread(ticketServer,"飞猪");
        Thread t2 = new Thread(ticketServer,"携程");
        t1.start();
        t2.start();
    }

}

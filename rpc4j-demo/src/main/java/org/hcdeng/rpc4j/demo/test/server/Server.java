package org.hcdeng.rpc4j.demo.test.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("classpath*:applicationContext-server.xml");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}

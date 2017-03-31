package org.hcdeng.rpc4j.demo.test.client;

import org.hcdeng.rpc4j.demo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

    private static DemoService demoService;

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-client.xml");
        demoService = (DemoService) context.getBean("demoService");
        test();
    }

    public static void test() throws InterruptedException {
        String result = demoService.echo("Hello World!");
        System.out.println(result);
    }

}

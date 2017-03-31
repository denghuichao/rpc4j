package org.hcdeng.rpc4j.demo.api;

public interface DemoService {
    String echo(String input);
    MessageDTO loadObject(int messageId);
}

package org.hcdeng.rpc4j.demo.impl;

import org.hcdeng.rpc4j.demo.api.DemoService;
import org.hcdeng.rpc4j.demo.api.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoServiceImpl implements DemoService {

    private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String echo(String input) {
        return "echo:"+input;
    }

    @Override
    public MessageDTO loadObject(int messageId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(1);
        messageDTO.setContent("zxc");
        return messageDTO;
    }
}

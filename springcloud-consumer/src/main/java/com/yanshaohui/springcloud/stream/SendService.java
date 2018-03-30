package com.yanshaohui.springcloud.stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

import com.yanshaohui.springcloud.entity.UserEntity;

@EnableBinding(Source.class)
public class SendService {
    
	@Autowired
    private Source source;

    public void sendMessage(UserEntity user) {

        try {
            source.output().send(MessageBuilder.withPayload(user).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

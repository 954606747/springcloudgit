package com.yanshaohui.springcloud.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.yanshaohui.springcloud.entity.UserEntity;

@EnableBinding(Sink.class)
public class MsgSink {
	
	private static Logger logger = LoggerFactory.getLogger(MsgSink.class);

	@StreamListener(Sink.INPUT)
    public void messageSink(UserEntity payload) throws InterruptedException {
		Thread.sleep(3000);
        logger.debug("Received data from stream:'{}'",payload);
    }
	
}

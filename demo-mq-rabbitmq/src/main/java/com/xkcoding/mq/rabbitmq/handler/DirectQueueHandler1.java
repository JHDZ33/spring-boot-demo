package com.xkcoding.mq.rabbitmq.handler;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.xkcoding.mq.rabbitmq.constants.RabbitConsts;
import com.xkcoding.mq.rabbitmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 直接队列1 处理器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-04 15:42
 */
@Slf4j
// 消费方这边也能完全配置一遍，算是做一个双重保险吧；下面注释掉的例子参数是读取配置文件中的参数，和本demo通过常量池不一样。
//@RabbitListener(
//    containerFactory="rabbitListenerContainerFactory",
//    bindings = @QueueBinding(
//        value = @Queue(	value = "${mqlistener.queue.name}",
//            durable = "${mqlistener.queue.durable}",
//            arguments = {@Argument(name = "x-dead-letter-exchange",value = "${mqlistener.death.exchange.name}"),
//                @Argument(name = "x-dead-letter-routing-key",value = "${mqlistener.death.arrive.key}")}),
//        exchange = @Exchange(value = "${mqlistener.exchange.name}",
//            durable = "${mqlistener.exchange.durable}",
//            type = "${mqlistener.exchange.type}",
//            ignoreDeclarationExceptions = "${mqlistener.exchange.ignoreDeclarationExceptions}"),
//        key = "${mqlistener.key}"
//    )
//)
@RabbitListener(queues = RabbitConsts.DIRECT_MODE_QUEUE1)
@Component
public class DirectQueueHandler1 {

    /**
     * 如果 spring.rabbitmq.listener.direct.acknowledge-mode: auto，则可以用这个方式，会自动ack
     */
    // @RabbitHandler
    public void directHandlerAutoAck(MessageStruct message) {
        log.info("直接队列处理器，接收消息：{}", JSONUtil.toJsonStr(message));
    }

    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("直接队列1，手动ACK，接收消息：{}", JSONUtil.toJsonStr(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}

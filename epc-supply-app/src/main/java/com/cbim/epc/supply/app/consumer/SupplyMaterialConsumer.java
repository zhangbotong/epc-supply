package com.cbim.epc.supply.app.consumer;

import com.cbim.epc.sdk.mq.constants.EpcMQConstants;
import com.cbim.epc.sdk.mq.utils.MQUtils;
import com.cbim.epc.supply.app.consumer.logic.MaterialBindMetaObjectConsumerLogic;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 物料消费
 *
 * @author xiaozp
 * @since 2023/5/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SupplyMaterialConsumer {

    private final MQUtils mqUtils;

    private final MaterialBindMetaObjectConsumerLogic materialBindMetaObjectConsumerLogic;


    /**
     * 构件解析规则 消费处理 元数据-属性变更消息
     *
     * @param message {@link Message}
     * @param channel {@link Channel}
     * @param deliveryTag 投递标签（ACK时使用）
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = EpcMQConstants.QUEUE_SUPPLY_MATERIAL_BIND_META_OBJECT, durable = "true"),
                    exchange = @Exchange(value = EpcMQConstants.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = EpcMQConstants.KEY_META_OBJECT_CHANGE
            )
    })
    public void materialConsumeMetaObjectChangeMsg(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        long start = System.currentTimeMillis();
        mqUtils.consumeSimpleChangeMessage(message, channel, deliveryTag, materialBindMetaObjectConsumerLogic);
        log.info("materialConsumeMetaObjectChangeMsg消费耗时：{} ms", System.currentTimeMillis() - start);

    }


}

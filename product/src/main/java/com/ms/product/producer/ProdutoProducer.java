package com.ms.product.producer;

import com.ms.product.domain.Produto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.exchange.orders}")
    private String exchange;

    @Value("${broker.routingkey.orders}")
    private String routingKey;

    public ProdutoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarStatusProduto(Produto produto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, produto);
    }
}
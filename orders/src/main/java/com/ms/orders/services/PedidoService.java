package com.ms.orders.services;

import com.ms.orders.domain.Pedido;
import com.ms.orders.repository.PedidoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.orders.name}")
    private String queueName;

    public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("CRIADO");
        pedidoRepository.save(pedido);
        rabbitTemplate.convertAndSend(queueName, pedido);
        return pedido;
    }

    public void atualizaStatus(Long id, String status){
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        pedidoOpt.ifPresent(pedido -> {
            pedido.getListaProdutos().forEach(itemPedido -> {
                if (itemPedido.getQuantidade() > 0) {
                    pedido.setStatus("disponivel");
                }else {
                    pedido.setStatus("Indisponivel");
                }
            });

            pedidoRepository.save(pedido);
        });
    }
}
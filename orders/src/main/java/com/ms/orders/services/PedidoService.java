package com.ms.orders.services;

import com.ms.orders.domain.Pedido;
import com.ms.orders.repository.PedidoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Pedido buscarPorId(Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Nenhum pedido encontrado"));
        return pedido;
    }

    public void atualizarStatusPedido (Long id, String status){
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        pedidoOptional.ifPresent(pedido ->  {
            pedido.setStatus(status);
            pedidoRepository.save(pedido);
        });
    }

    public List<Pedido> listarTodos(){
        return pedidoRepository.findAll();
    }
}
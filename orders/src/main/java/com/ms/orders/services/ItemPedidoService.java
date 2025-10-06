package com.ms.orders.services;

import com.ms.orders.domain.ItemPedido;
import com.ms.orders.repository.ItemPedidoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.orders.name}")
    private String queueName;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, RabbitTemplate rabbitTemplate) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ItemPedido buscarPorId (Long id){
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(()-> new RuntimeException("Nenhum item encontrado"));

        return itemPedido;
    }

    public List<ItemPedido> buscarPorIdProduto (Long idProduto){
        List<ItemPedido> listaItemPedido = itemPedidoRepository.findByIdProduto(idProduto);

        return listaItemPedido;
    }
}
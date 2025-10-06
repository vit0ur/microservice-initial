package com.ms.orders.services;

import com.ms.orders.domain.ItemPedido;
import com.ms.orders.domain.Pedido;
import com.ms.orders.repository.ItemPedidoRepository;
import com.ms.orders.repository.PedidoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ItemPedidoRepository itemPedidoRepository;

    @Value("${broker.queue.orders.name}")
    private String queueName;

    public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        pedido.setValorTotal(pedido.getValorTotal());
        pedido.setStatus("CRIADO");

        pedidoRepository.save(pedido);
        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedido itemDTO : pedido.getListaProdutos()) {
            ItemPedido item = new ItemPedido();
            item.setIdProduto(itemDTO.getIdProduto());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPedido(pedido);
            itens.add(item);
        }

        itemPedidoRepository.saveAll(itens);

        pedido.setListaProdutos(itens);
        rabbitTemplate.convertAndSend(queueName, pedido);
        return pedido;
    }

    public Pedido buscarPorId(Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Nenhum pedido encontrado"));
        return pedido;
    }

    public void atualizarStatusPedido (Long id, String status){
            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

            pedido.setStatus(status);
            pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos(){
        return pedidoRepository.findAll();
    }
}
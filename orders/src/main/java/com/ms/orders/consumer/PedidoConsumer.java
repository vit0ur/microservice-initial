package com.ms.orders.consumer;

import com.ms.orders.domain.ItemPedido;
import com.ms.orders.dtos.ProdutoDTO;
import com.ms.orders.services.ItemPedidoService;
import com.ms.orders.services.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {
    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoConsumer(PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }

    @RabbitListener(queues = "${broker.queue.orders.name}")
    public void processaPedido(ProdutoDTO produtoDTO) {
        int quantidade = produtoDTO.getQuantidadeEmEstoque();
        String status = null;
        if (quantidade <= 0){
            status = "Sem em estoque";
        } else if (quantidade == 1) {
            status = "Ultima unidade";
        } else{
            status = "Disponivel";
        }

        for (ItemPedido itemPedido : itemPedidoService.buscarPorIdProduto(produtoDTO.getIdProduto())) {
            pedidoService.atualizarStatusPedido(itemPedido.getPedido().getIdPedido(), status);
        }
    }
}
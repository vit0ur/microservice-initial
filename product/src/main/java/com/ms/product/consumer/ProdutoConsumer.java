package com.ms.product.consumer;

import com.ms.product.dtos.ItemPedidoDTO;
import com.ms.product.dtos.PedidoDTO;
import com.ms.product.service.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    private final ProdutoService produtoService;

    public ProdutoConsumer(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RabbitListener(queues = "${broker.queue.orders.name}")
    public void processarPedido(PedidoDTO pedido) {
        for (ItemPedidoDTO item : pedido.getListaProdutos()) {
            produtoService.atualizarEstoque(item.getIdProduto(), item.getQuantidade());
        }
    }

}
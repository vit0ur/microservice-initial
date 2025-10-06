package com.ms.product.consumer;

import com.ms.product.domain.Produto;
import com.ms.product.dtos.ItemPedidoDTO;
import com.ms.product.dtos.PedidoDTO;
import com.ms.product.producer.ProdutoProducer;
import com.ms.product.service.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    private final ProdutoService produtoService;
    private final ProdutoProducer produtoProducer;

    public ProdutoConsumer(ProdutoService produtoService, ProdutoProducer produtoProducer) {
        this.produtoService = produtoService;
        this.produtoProducer = produtoProducer;
    }

    @RabbitListener(queues = "${broker.queue.orders.name}")
    public void processarPedido(PedidoDTO pedido) {
        for (ItemPedidoDTO item : pedido.getListaProdutos()) {
            int novoEstoque = produtoService.atualizarEstoque(item.getIdProduto(), item.getQuantidade());

            Produto produto = new Produto();
            produto.setId(item.getIdProduto());
            produto.setQuantidadeEmEstoque(novoEstoque);

            produtoProducer.enviarStatusProduto(produto);
        }
    }
}
package com.ms.orders.consumer;

import com.ms.orders.domain.ItemPedido;
import com.ms.orders.dtos.ProdutoDTO;
import com.ms.orders.services.ItemPedidoService;
import com.ms.orders.services.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PedidoConsumer {

    private static final Logger log = LoggerFactory.getLogger(PedidoConsumer.class);

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoConsumer(PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }

    @RabbitListener(queues = "${broker.queue.orders.name}")
    public void processaPedido(ProdutoDTO produtoDTO) {
        try {
            log.info("Recebido ProdutoDTO: {}", produtoDTO);

            Integer quantidade = produtoDTO.getQuantidadeEmEstoque();
            String status;

            if (quantidade == null) {
                log.warn("Quantidade em estoque nula para produto {}", produtoDTO.getIdProduto());
                status = "ESTOQUE_DESCONHECIDO";
            } else if (quantidade <= 0) {
                status = "SEM_ESTOQUE";
            } else if (quantidade == 1) {
                status = "ULTIMA_UNIDADE";
            } else {
                status = "DISPONIVEL";
            }

            List<ItemPedido> itens = itemPedidoService.buscarPorIdProduto(produtoDTO.getIdProduto());

            if (itens.isEmpty()) {
                log.warn("Nenhum ItemPedido encontrado para o produto {}", produtoDTO.getIdProduto());
            }

            for (ItemPedido itemPedido : itens) {
                Long idPedido = itemPedido.getPedido().getIdPedido();
                log.info("Atualizando status do pedido {} para {}", idPedido, status);
                pedidoService.atualizarStatusPedido(idPedido, status);
            }

        } catch (Exception e) {
            log.error("Erro ao processar ProdutoDTO: {}", produtoDTO, e);
            throw e; // ou trate de forma personalizada, se necessário
        }
    }
}
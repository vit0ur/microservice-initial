package com.ms.product.service;

import com.ms.product.domain.Produto;
import com.ms.product.repository.ProdutoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.orders.name}")
    private String queueName;

    public ProdutoService(ProdutoRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public void atualizarEstoque(Long id, Integer quantidadeVendida) {
        Optional<Produto> produtoOpt = repository.findById(id);
        produtoOpt.ifPresent(produto -> {
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidadeVendida);
            rabbitTemplate.convertAndSend(queueName, produto);
            repository.save(produto);
        });
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}

package com.ms.orders.domain;

import jakarta.persistence.*;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduto;
    private Integer quantidade;

    public ItemPedido(Long id, Long idProduto, Integer quantidade) {
        this.id = id;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public ItemPedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
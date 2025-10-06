package com.ms.orders.dtos;

import java.math.BigDecimal;

public class ProdutoDTO {
    private Long idProduto;
    private String nome;
    private BigDecimal preco;
    private Integer quantidadeEmEstoque;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long idProduto, String nome, BigDecimal preco, Integer quantidadeEmEstoque) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
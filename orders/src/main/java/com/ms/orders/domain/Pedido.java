package com.ms.orders.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private BigDecimal valorTotal;
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> listaProdutos;

    public Pedido() {
    }

    public Pedido(Long idPedido, BigDecimal valorTotal, String status, List<ItemPedido> listaProdutos) {
        this.idPedido = idPedido;
        this.valorTotal = valorTotal;
        this.status = status;
        this.listaProdutos = listaProdutos;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemPedido> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<ItemPedido> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
}
package com.ms.product.dtos;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {
    private Long idPedido;
    private List<ItemPedidoDTO> listaProdutos;
    private BigDecimal valorTotal;
    private String status;

    public PedidoDTO() {
    }

    public PedidoDTO(Long idPedido, List<ItemPedidoDTO> listaProdutos, BigDecimal valorTotal, String status) {
        this.idPedido = idPedido;
        this.listaProdutos = listaProdutos;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public List<ItemPedidoDTO> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<ItemPedidoDTO> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
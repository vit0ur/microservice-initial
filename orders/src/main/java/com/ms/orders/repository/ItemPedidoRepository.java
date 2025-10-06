package com.ms.orders.repository;

import com.ms.orders.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByIdProduto (Long idProduto);
}
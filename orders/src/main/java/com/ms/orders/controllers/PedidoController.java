package com.ms.orders.controllers;

import com.ms.orders.domain.Pedido;
import com.ms.orders.services.PedidoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return service.criarPedido(pedido);
    }
}

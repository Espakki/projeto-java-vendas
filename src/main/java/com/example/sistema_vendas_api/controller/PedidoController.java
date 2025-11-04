package com.example.sistema_vendas_api.controller;

import com.example.sistema_vendas_api.dto.PedidoDTO;
import com.example.sistema_vendas_api.model.Pedido;
import com.example.sistema_vendas_api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> salvarPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido pedidoSalvo = pedidoService.criarPedido(pedidoDTO);
            return new ResponseEntity<>(pedidoSalvo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

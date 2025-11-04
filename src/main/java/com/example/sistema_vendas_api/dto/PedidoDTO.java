package com.example.sistema_vendas_api.dto;

import java.util.List;

public class PedidoDTO {

    private Integer clienteId;
    private List<ItemPedidoDTO> itens;

    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    public List<ItemPedidoDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
package com.example.sistema_vendas_api.dto;

public class ItemPedidoDTO {

    private Integer produtoId;
    private Integer quantidade;

    public Integer getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
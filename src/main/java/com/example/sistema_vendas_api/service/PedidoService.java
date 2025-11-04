package com.example.sistema_vendas_api.service;

import com.example.sistema_vendas_api.dto.ItemPedidoDTO;
import com.example.sistema_vendas_api.dto.PedidoDTO;
import com.example.sistema_vendas_api.model.Cliente;
import com.example.sistema_vendas_api.model.ItemPedido;
import com.example.sistema_vendas_api.model.Pedido;
import com.example.sistema_vendas_api.model.Produto;

import com.example.sistema_vendas_api.repository.ClienteRepository;
import com.example.sistema_vendas_api.repository.ItemPedidoRepository;
import com.example.sistema_vendas_api.repository.PedidoRepository;
import com.example.sistema_vendas_api.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Pedido criarPedido(PedidoDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("PENDENTE");

        for (ItemPedidoDTO itemDTO : requestDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
            if (produto.getQuantidadeEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            int novoEstoque = produto.getQuantidadeEstoque() - itemDTO.getQuantidade();
            produto.setQuantidadeEstoque(novoEstoque);
            produtoRepository.save(produto);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(java.math.BigDecimal.valueOf(produto.getPreco()));
            pedido.getItens().add(itemPedido);
        }
        return pedidoRepository.save(pedido);
    }
}
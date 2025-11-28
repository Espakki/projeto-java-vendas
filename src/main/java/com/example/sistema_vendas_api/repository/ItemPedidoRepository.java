package com.example.sistema_vendas_api.repository;

import com.example.sistema_vendas_api.model.ItemPedido;
import com.example.sistema_vendas_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    boolean existsByProduto(Produto produto);
}
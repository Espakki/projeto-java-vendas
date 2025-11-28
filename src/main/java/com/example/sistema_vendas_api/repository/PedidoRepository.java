package com.example.sistema_vendas_api.repository;

import com.example.sistema_vendas_api.model.Cliente;
import com.example.sistema_vendas_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    boolean existsByCliente(Cliente cliente);
}
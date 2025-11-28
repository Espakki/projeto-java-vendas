package com.example.sistema_vendas_api.service;

import com.example.sistema_vendas_api.model.Cliente;
import com.example.sistema_vendas_api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente salvarCliente(@NonNull Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(@NonNull Integer id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente atualizarCliente(@NonNull Integer id, @NonNull Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void deletarCliente(@NonNull Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}


package com.example.sistema_vendas_api.controller;

import com.example.sistema_vendas_api.model.Cliente;
import com.example.sistema_vendas_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    // listar
    @GetMapping
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }
    // cadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }
    // busca por id
    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Integer id){
        return clienteRepository.findById(id).orElse(null);
    }
    // atualizar por id
    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(clienteAtualizado.getNome());
                    clienteExistente.setEmail(clienteAtualizado.getEmail());
                    clienteExistente.setTelefone(clienteAtualizado.getTelefone());

                    return clienteRepository.save(clienteExistente);
                })
                .orElse(null);
    }
    // deletar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clienteRepository.deleteById(id);
    }

}

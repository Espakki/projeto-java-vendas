package com.example.sistema_vendas_api.controller;

import com.example.sistema_vendas_api.model.Produto;
import com.example.sistema_vendas_api.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    //listar
    @GetMapping
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }
    //cadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }
    // busca por id
    @GetMapping("/{id}")
    public Produto findById(@PathVariable Integer id){
        return produtoRepository.findById(id).orElse(null);
    }
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Integer id, @RequestBody Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoAtualizado.getNome());
                    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                    produtoExistente.setPreco(produtoAtualizado.getPreco());
                    produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

                    return produtoRepository.save(produtoExistente);
                })
                .orElse(null);
    }
    // deletar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtoRepository.deleteById(id);
    }
}

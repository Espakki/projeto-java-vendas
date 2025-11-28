package com.example.sistema_vendas_api.service;

import com.example.sistema_vendas_api.model.Produto;
import com.example.sistema_vendas_api.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto salvarProduto(@NonNull Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(@NonNull Integer id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto atualizarProduto(@NonNull Integer id, @NonNull Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void deletarProduto(@NonNull Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}


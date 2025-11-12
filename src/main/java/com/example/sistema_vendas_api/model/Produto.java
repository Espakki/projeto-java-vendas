package com.example.sistema_vendas_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    public Produto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return "Produto [ID=" + id +
                ", Nome=" + nome +
                ", Preço=" + preco +
                ", Estoque=" + quantidadeEstoque + "]";
    }
}

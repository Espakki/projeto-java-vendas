package org.example;
import dao.ProdutoDAO;
import model.Produto;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // --- TESTE DO READ (Listar Todos) ---
        System.out.println("--- Buscando todos os produtos do banco... ---");

        List<Produto> produtosDoBanco = produtoDAO.listarTodos();

        // Se a lista estiver vazia, avisa
        if (produtosDoBanco.isEmpty()) {
            System.out.println("Nenhum produto encontrado no banco.");
        } else {
            // Se não, "Para cada 'p' do tipo 'Produto' dentro da lista 'produtosDoBanco'..."
            for (Produto p : produtosDoBanco) {
                // Usamos o método .toString() que você criou na classe Produto!
                System.out.println(p);
            }
        }

        System.out.println("-------------------------------------------");
        }
    }
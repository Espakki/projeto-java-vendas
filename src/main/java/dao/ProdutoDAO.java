package dao;

import model.Produto;
import org.example.util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ProdutoDAO {
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, quantidade_estoque) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produto.getNome());

            pstmt.setString(2, produto.getDescricao());

            pstmt.setDouble(3, produto.getPreco());

            pstmt.setInt(4, produto.getQuantidadeEstoque());

            pstmt.executeUpdate();

            System.out.println("Produto '" + produto.getNome() + "'Salvo com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao salvar o produto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos";

        List<Produto> listaDeProdutos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                listaDeProdutos.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar o produto: " + e.getMessage());
            e.printStackTrace();
        }
        return listaDeProdutos;
    }
}

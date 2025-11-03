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
    // metodo insert
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

    // metodo read por lista
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

    // metodo read por id
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";
        Produto produto = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return produto;
    }

    // metodo update
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ? WHERE id_produto = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidadeEstoque());

            pstmt.setInt(5, produto.getId());

            pstmt.executeUpdate();

            System.out.println("Produto '" + produto.getNome() + "' atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // metodo delete
    public void deletar(int id) {
        String sql = "DELETE FROM produtos WHERE id_produto = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            System.out.println("Produto com ID " + id + " deletado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

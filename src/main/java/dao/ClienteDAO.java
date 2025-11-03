package dao;

import model.Cliente;
import org.example.util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ClienteDAO {
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());

            pstmt.setString(2, cliente.getEmail());

            pstmt.setString(3, cliente.getTelefone());

            pstmt.executeUpdate();

            System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao cadrastar o cliente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // metodo read por lista
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM clientes";

        List<Cliente> listaDeClientes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));

                listaDeClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter as informações do cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return listaDeClientes;
    }

    // metodo read por id
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefone(rs.getString("telefone"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pelo ID do cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }

    // metodo update
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id_cliente = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setInt(4, cliente.getId());

            pstmt.executeUpdate();

            System.out.println("O cliente '" + cliente.getNome() + "'foi atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar os dados do cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // metodo delete
    public void deletar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            System.out.println("Cliente com ID " + id + " deletado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar o cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// O pacote está correto
package org.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {

    private static Properties carregarPropriedades() {
        Properties props = new Properties();
        // O 'try-with-resources' garante que o 'input' será fechado
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            System.err.println("Erro: Não foi possível encontrar o arquivo 'config.properties'.");
            System.err.println("Certifique-se de que ele exista na pasta raiz do projeto.");
            e.printStackTrace();
            // Retorna props vazias se der erro
        }
        return props;
    }

    public static Connection getConexao() throws SQLException {
        Properties props = carregarPropriedades();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        if (url == null || user == null || pass == null) {
            throw new SQLException("Falha ao ler 'config.properties'. Verifique o arquivo.");
        }

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do postgres não encontrado.", e);
        }
    }
}
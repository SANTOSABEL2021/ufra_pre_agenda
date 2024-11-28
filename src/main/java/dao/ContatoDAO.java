package dao;

import model.Contato;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private static final String INSERT_CONTATO_SQL = "INSERT INTO contato (nome, telefone) VALUES (?, ?)";
    private static final String SELECT_CONTATO_BY_ID = "SELECT id, nome, telefone FROM contato WHERE id = ?";
    private static final String SELECT_ALL_CONTATOS = "SELECT * FROM contato";
    private static final String DELETE_CONTATO_SQL = "DELETE FROM contato WHERE id = ?";
    private static final String UPDATE_CONTATO_SQL = "UPDATE contato SET nome = ?, telefone = ? WHERE id = ?";

    public void insertContato(Contato contato) throws SQLException, ClassNotFoundException {
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTATO_SQL)) {
            preparedStatement.setString(1, contato.getNome());
            preparedStatement.setString(2, contato.getTelefone());
            preparedStatement.executeUpdate();
        }
    }

    public Contato selectContato(int id) throws SQLException, ClassNotFoundException {
        Contato contato = null;
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTATO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                contato = new Contato(id, nome, telefone);
            }
        }
        return contato;
    }

    public List<Contato> selectAllContatos() throws SQLException, ClassNotFoundException {
        List<Contato> contatos = new ArrayList<>();
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTATOS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                contatos.add(new Contato(id, nome, telefone));
            }
        }
        return contatos;
    }

    public boolean deleteContato(int id) throws SQLException, ClassNotFoundException {
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTATO_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean updateContato(Contato contato) throws SQLException, ClassNotFoundException {
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTATO_SQL)) {
            preparedStatement.setString(1, contato.getNome());
            preparedStatement.setString(2, contato.getTelefone());
            preparedStatement.setInt(3, contato.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }
}

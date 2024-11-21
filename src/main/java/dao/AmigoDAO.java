package dao;

import model.Amigo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmigoDAO {

    public void adicionarAmigo(Amigo amigo) {
        String sql = "INSERT INTO amigos (nome, telefone) VALUES (?, ?)";
        try (Connection conexao = Conexao.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, amigo.getNome());
            stmt.setString(2, amigo.getTelefone());
            stmt.executeUpdate();
            System.out.println("Amigo adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar amigo: " + e.getMessage());
        }
    }

    public List<Amigo> listarAmigos() {
        List<Amigo> amigos = new ArrayList<>();
        String sql = "SELECT * FROM amigos";
        try (Connection conexao = Conexao.getConnection(); Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Amigo amigo = new Amigo();
                amigo.setId(rs.getInt("id"));
                amigo.setNome(rs.getString("nome"));
                amigo.setTelefone(rs.getString("telefone"));
                amigos.add(amigo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar amigos: " + e.getMessage());
        }
        return amigos;
    }

    public void atualizarAmigo(Amigo amigo) {
        String sql = "UPDATE amigos SET nome = ?, telefone = ? WHERE id = ?";
        try (Connection conexao = Conexao.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, amigo.getNome());
            stmt.setString(2, amigo.getTelefone());
            stmt.setInt(3, amigo.getId());
            stmt.executeUpdate();
            System.out.println("Amigo atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar amigo: " + e.getMessage());
        }
    }

    public void deletarAmigo(int id) {
        String sql = "DELETE FROM amigos WHERE id = ?";
        try (Connection conexao = Conexao.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Amigo deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar amigo: " + e.getMessage());
        }
    }

    public Amigo buscarPorId(int id) {
        String sql = "SELECT * FROM amigos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Amigo amigo = new Amigo();
                    amigo.setId(rs.getInt("id"));
                    amigo.setNome(rs.getString("nome"));
                    amigo.setTelefone(rs.getString("telefone"));

                    return amigo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

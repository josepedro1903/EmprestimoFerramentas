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
        Amigo amigo = null;
        String sql = "SELECT * FROM amigos WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                amigo = new Amigo();
                amigo.setId(rs.getInt("id"));
                amigo.setNome(rs.getString("nome"));
                amigo.setTelefone(rs.getString("telefone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amigo;
    }

    public Amigo amigoComMaisEmprestimos() {
        String sql = "SELECT amigo_id, COUNT(*) AS total FROM emprestimos GROUP BY amigo_id ORDER BY total DESC LIMIT 1";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int amigoId = rs.getInt("amigo_id");
                return buscarPorId(amigoId); // Retorna o amigo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Amigo> amigosComPendencias() {
        List<Amigo> amigos = new ArrayList<>();
        String sql = "SELECT DISTINCT amigo_id FROM emprestimos WHERE data_devolucao IS NULL";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                amigos.add(buscarPorId(rs.getInt("amigo_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amigos;
    }

}

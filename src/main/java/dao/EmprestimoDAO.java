package dao;

import model.Emprestimo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (amigo_id, ferramenta_id) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setInt(2, emprestimo.getFerramenta_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET amigo_id = ?, ferramenta_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setInt(2, emprestimo.getFerramenta_id());
            stmt.setInt(3, emprestimo.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Empréstimo atualizado com sucesso.");
            } else {
                System.out.println("Nenhum empréstimo encontrado com o ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        try (Connection conn = Conexao.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setAmigo_id(rs.getInt("amigo_id"));
                emprestimo.setFerramenta_id(rs.getInt("ferramenta_id"));
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public void deletarEmprestimo(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Emprestimo buscarPorId(int id) {
        String sql = "SELECT * FROM emprestimos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setAmigo_id(rs.getInt("amigo_id"));
                emprestimo.setFerramenta_id(rs.getInt("ferramenta_id"));
                return emprestimo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    
    }
}

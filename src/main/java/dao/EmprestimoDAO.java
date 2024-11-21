package dao;

import model.Emprestimo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public void adicionarEmprestimo(Emprestimo emprestimo, List<Integer> ferramentasIds) {
        String sql = "INSERT INTO emprestimos (amigo_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Inserir empréstimo na tabela 'emprestimos'
            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setDate(2, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(3, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
            stmt.executeUpdate();

            // Recuperar o ID gerado para o empréstimo
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int emprestimoId = rs.getInt(1);

                // Associar ferramentas ao empréstimo na tabela 'emprestimos_ferramentas'
                String sqlFerramentas = "INSERT INTO emprestimos_ferramentas (emprestimo_id, ferramenta_id) VALUES (?, ?)";
                try (PreparedStatement stmtFerramentas = conn.prepareStatement(sqlFerramentas)) {
                    for (int ferramentaId : ferramentasIds) {
                        stmtFerramentas.setInt(1, emprestimoId);
                        stmtFerramentas.setInt(2, ferramentaId);
                        stmtFerramentas.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEmprestimo(Emprestimo emprestimo, List<Integer> ferramentasIds) {
        String sql = "UPDATE emprestimos SET amigo_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Atualizar dados do empréstimo
            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setDate(2, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(3, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
            stmt.setInt(4, emprestimo.getId());
            stmt.executeUpdate();

            // Atualizar associação com ferramentas
            String deleteFerramentas = "DELETE FROM emprestimos_ferramentas WHERE emprestimo_id = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteFerramentas)) {
                stmtDelete.setInt(1, emprestimo.getId());
                stmtDelete.executeUpdate();
            }

            String insertFerramentas = "INSERT INTO emprestimos_ferramentas (emprestimo_id, ferramenta_id) VALUES (?, ?)";
            try (PreparedStatement stmtInsert = conn.prepareStatement(insertFerramentas)) {
                for (int ferramentaId : ferramentasIds) {
                    stmtInsert.setInt(1, emprestimo.getId());
                    stmtInsert.setInt(2, ferramentaId);
                    stmtInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setAmigo_id(rs.getInt("amigo_id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                if (rs.getDate("data_devolucao") != null) {
                    emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                }

                // Buscar ferramentas associadas
                List<Integer> ferramentasIds = buscarFerramentasPorEmprestimo(emprestimo.getId());
                emprestimo.setFerramentasIds(ferramentasIds);

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public void deletarEmprestimo(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Remover associação com ferramentas
            String deleteFerramentas = "DELETE FROM emprestimos_ferramentas WHERE emprestimo_id = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteFerramentas)) {
                stmtDelete.setInt(1, id);
                stmtDelete.executeUpdate();
            }

            // Remover o empréstimo
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> buscarFerramentasPorEmprestimo(int emprestimoId) {
        List<Integer> ferramentasIds = new ArrayList<>();
        String sql = "SELECT ferramenta_id FROM emprestimos_ferramentas WHERE emprestimo_id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ferramentasIds.add(rs.getInt("ferramenta_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferramentasIds;
    }
}

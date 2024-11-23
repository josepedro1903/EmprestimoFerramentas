package dao;

import model.Emprestimo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Amigo;

public class EmprestimoDAO {

    // Adiciona um novo empréstimo ao banco de dados e associa as ferramentas ao empréstimo.
    public void adicionarEmprestimo(Emprestimo emprestimo, List<Integer> ferramentasIds) {
        String sql = "INSERT INTO emprestimos (amigo_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setDate(2, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(3, null);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int emprestimoId = rs.getInt(1);

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

    // Atualiza os dados de um empréstimo existente e suas ferramentas associadas.
    public void atualizarEmprestimo(Emprestimo emprestimo, List<Integer> ferramentasIds) {
        String sql = "UPDATE emprestimos SET amigo_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getAmigo_id());
            stmt.setDate(2, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(3, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
            stmt.setInt(4, emprestimo.getId());
            stmt.executeUpdate();

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

    // Lista todos os empréstimos cadastrados no banco de dados.
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

                List<Integer> ferramentasIds = buscarFerramentasPorEmprestimo(emprestimo.getId());
                emprestimo.setFerramentasIds(ferramentasIds);

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    // Lista os empréstimos que ainda estão ativos (não devolvidos).
    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE data_devolucao IS NULL";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setAmigo_id(rs.getInt("amigo_id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());

                List<Integer> ferramentasIds = buscarFerramentasPorEmprestimo(emprestimo.getId());
                emprestimo.setFerramentasIds(ferramentasIds);

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    // Deleta um empréstimo e suas associações com ferramentas.
    public void deletarEmprestimo(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String deleteFerramentas = "DELETE FROM emprestimos_ferramentas WHERE emprestimo_id = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteFerramentas)) {
                stmtDelete.setInt(1, id);
                stmtDelete.executeUpdate();
            }

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca os IDs das ferramentas associadas a um empréstimo.
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

    // Registra a devolução de um empréstimo atualizando a data de devolução.
    public void devolverEmprestimo(int emprestimoId) {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            stmt.setInt(2, emprestimoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verifica se um amigo tem empréstimos ativos (não devolvidos).
    public boolean amigoTemEmprestimosAtivos(int amigoId) {
        String sql = "SELECT COUNT(*) FROM emprestimos WHERE amigo_id = ? AND data_devolucao IS NULL";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, amigoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lista os amigos que nunca devolveram nenhum empréstimo.
    public List<Amigo> listarAmigosQueNuncaEntregaram() {
        String sql
                = "SELECT a.id, a.nome, a.telefone "
                + "FROM amigos a "
                + "JOIN emprestimos e ON a.id = e.amigo_id "
                + "GROUP BY a.id, a.nome, a.telefone "
                + "HAVING COUNT(e.id) > 0 AND SUM(CASE WHEN e.data_devolucao IS NOT NULL THEN 1 ELSE 0 END) = 0";
        List<Amigo> amigos = new ArrayList<>();
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Amigo amigo = new Amigo();
                amigo.setId(rs.getInt("id"));
                amigo.setNome(rs.getString("nome"));
                amigo.setTelefone(rs.getString("telefone"));
                amigos.add(amigo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amigos;
    }
}

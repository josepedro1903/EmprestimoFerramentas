package dao;

import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmprestimosFerramentasDAO {

    public void adicionarEmprestimoFerramenta(int emprestimoId, int ferramentaId) {
        String sql = "INSERT INTO emprestimos_ferramentas (emprestimo_id, ferramenta_id) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimoId);
            stmt.setInt(2, ferramentaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPorEmprestimo(int emprestimoId) {
        String sql = "DELETE FROM emprestimos_ferramentas WHERE emprestimo_id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

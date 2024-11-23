package dao;

import model.Ferramenta;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FerramentaDAO {

    // Adiciona uma nova ferramenta ao banco de dados.
    public void adicionarFerramenta(Ferramenta ferramenta) {
        String sql = "INSERT INTO ferramentas (nome, marca, custo_aquisicao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ferramenta.getNome());
            stmt.setString(2, ferramenta.getMarca());
            stmt.setDouble(3, ferramenta.getCustoAquisicao());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                ferramenta.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar ferramenta: " + e.getMessage());
        }
    }

    // Lista todas as ferramentas cadastradas no banco de dados.
    public List<Ferramenta> listarFerramentas() {
        List<Ferramenta> ferramentas = new ArrayList<>();
        String sql = "SELECT * FROM ferramentas";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ferramenta ferramenta = new Ferramenta();
                ferramenta.setId(rs.getInt("id"));
                ferramenta.setNome(rs.getString("nome"));
                ferramenta.setMarca(rs.getString("marca"));
                ferramenta.setCustoAquisicao(rs.getDouble("custo_aquisicao"));
                ferramentas.add(ferramenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferramentas;
    }

    // Atualiza os dados de uma ferramenta existente no banco de dados.
    public void atualizarFerramenta(Ferramenta ferramenta) {
        String sql = "UPDATE ferramentas SET nome = ?, marca = ?, custo_aquisicao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ferramenta.getNome());
            stmt.setString(2, ferramenta.getMarca());
            stmt.setDouble(3, ferramenta.getCustoAquisicao());
            stmt.setInt(4, ferramenta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deleta uma ferramenta do banco de dados com base no ID fornecido.
    public void deletarFerramenta(int id) {
        String sql = "DELETE FROM ferramentas WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca uma ferramenta no banco de dados com base no ID fornecido.
    public Ferramenta buscarPorId(int id) {
        Ferramenta ferramenta = null;
        String sql = "SELECT * FROM ferramentas WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ferramenta = new Ferramenta();
                ferramenta.setId(rs.getInt("id"));
                ferramenta.setNome(rs.getString("nome"));
                ferramenta.setMarca(rs.getString("marca"));
                ferramenta.setCustoAquisicao(rs.getDouble("custo_aquisicao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferramenta;
    }

    // Calcula o custo total de aquisição de todas as ferramentas cadastradas.
    public double calcularCustoTotalFerramentas() {
        String sql = "SELECT SUM(custoAquisicao) FROM ferramentas";
        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}

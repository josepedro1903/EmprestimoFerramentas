package dao;

import model.Ferramentas;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FerramentasDAO {

 

    public void adicionarFerramenta(Ferramentas ferramenta) {
        String sql = "INSERT INTO ferramentas (nome, marca, custoAquisicao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ferramenta.getNome());
            stmt.setString(2, ferramenta.getMarca());
            stmt.setDouble(3, ferramenta.getCustoAquisicao());
            stmt.executeUpdate();

            // Recuperar o ID gerado automaticamente
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                ferramenta.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ferramentas> listarFerramentas() {
        List<Ferramentas> ferramentas = new ArrayList<>();
        String sql = "SELECT * FROM ferramentas";
        try (Connection conn = Conexao.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ferramentas ferramenta = new Ferramentas();
                ferramenta.setId(rs.getInt("id"));
                ferramenta.setNome(rs.getString("nome"));
                ferramenta.setMarca(rs.getString("marca"));
                ferramenta.setCustoAquisicao(rs.getDouble("custoAquisicao"));
                ferramentas.add(ferramenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferramentas;
    }

    public void atualizarFerramenta(Ferramentas ferramenta) {
        String sql = "UPDATE ferramentas SET nome = ?, marca = ?, custoAquisicao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ferramenta.getNome());
            stmt.setString(2, ferramenta.getMarca());
            stmt.setDouble(3, ferramenta.getCustoAquisicao());
            stmt.setInt(4, ferramenta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarFerramenta(int id) {
        String sql = "DELETE FROM ferramentas WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ferramentas buscarPorId(int id) {
        Ferramentas ferramenta = null;
        String sql = "SELECT * FROM ferramentas WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ferramenta = new Ferramentas();
                ferramenta.setId(rs.getInt("id"));
                ferramenta.setNome(rs.getString("nome"));
                ferramenta.setMarca(rs.getString("marca"));
                ferramenta.setCustoAquisicao(rs.getDouble("custoAquisicao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferramenta;
    }
}

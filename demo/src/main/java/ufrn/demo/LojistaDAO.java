package ufrn.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class LojistaDAO {
    public void cadastrarLojista(Lojista lojista) {
        Connection conexao = null;
        PreparedStatement stm = null;

        try {
            conexao = Conexao.getConnection();
            String sql = "INSERT INTO lojista (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, lojista.getNome());
            stm.setString(2, lojista.getEmail());
            stm.setString(3, lojista.getSenha());
            stm.setString(4, lojista.getTipo());

            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }

    }

    public List<Lojista> getAllLojista() {
        List<Lojista> lojistas = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM lojista";
            stm = conexao.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Lojista lojista = new Lojista();
                lojista.setNome(rs.getString("nome"));
                lojista.setEmail(rs.getString("email"));
                lojista.setSenha(rs.getString("senha"));
                lojista.setTipo((rs.getString("tipo")));
                lojistas.add(lojista);

            }
        } catch (SQLException  ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return lojistas;

    }

    public Lojista autenticarLojista(String email, String senha) {
        Connection conexao = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM lojista WHERE email = ? AND senha = ?";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, senha);
            rs = stm.executeQuery();

            if (rs.next()) {
                Lojista lojista = new Lojista();
                lojista.setNome(rs.getString("nome"));
                lojista.setEmail(rs.getString("email"));
                lojista.setSenha(rs.getString("senha"));
                return lojista;
            }

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }
}

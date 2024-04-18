package ufrn.demo;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ufrn.demo.Conexao.getConnection;

@Repository
public class ProdutoDAO {

    private final Connection conexao;

    public ProdutoDAO() throws SQLException, URISyntaxException {
        this.conexao = getConnection();
    }

    // método para listar todos os produtos do banco de dados
    public List<Produto> listarProdutos() throws URISyntaxException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        // conexão com o banco de dados
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // itera sobre o resultado da consulta
            while (rs.next()) {
                int id = rs.getInt("id");
                int preco = rs.getInt("preco");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                int estoque = rs.getInt("estoque");

                // Cria um objeto Produto e adiciona à lista
                Produto produto = new Produto(id, preco, nome, descricao, estoque);
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    // método para adicionar um produto ao banco de dados
    @PostMapping("/cadastrar-produto")
    public void adicionarProdutoNoBanco(Produto produto) throws URISyntaxException {
        String sql = "INSERT INTO produtos (id, preco, nome, descricao, estoque) VALUES (?, ?, ?, ?, ?)";

        // conexão com o banco de dados
        try (Connection conexao = getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, produto.getId());
            stmt.setInt(2, produto.getPreco());
            stmt.setString(3, produto.getNome());
            stmt.setString(4, produto.getDescricao());
            stmt.setInt(5, produto.getEstoque());

            // cxecuta a inserção
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Produto getProdutoById(int idProduto) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idProduto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setPreco(rs.getInt("preco"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                return produto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void decrementaEstoque(int idProduto, int estoque) throws SQLException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        conexao = getConnection();
        String query = "UPDATE produtos SET estoque = estoque - ? WHERE id = ?";
        stmt = conexao.prepareStatement(query);
        stmt.setInt(1, estoque);
        stmt.setInt(2, idProduto);
        stmt.executeUpdate();
    }
}

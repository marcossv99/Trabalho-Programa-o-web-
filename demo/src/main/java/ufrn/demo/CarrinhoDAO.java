package ufrn.demo;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDAO {
    private Connection connection;

    public CarrinhoDAO() throws SQLException, URISyntaxException {
        // pega a conexão com o banco de dados usando a classe conexao
        this.connection = Conexao.getConnection();
    }

    // método para adicionar um produto ao carrinho
    public void adicionarProduto(int idProduto) {
        String sql = "INSERT INTO carrinho (id_produto) VALUES (?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idProduto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // método para remover um produto do carrinho
    public void removerProduto(int idProduto) {
        String sql = "DELETE FROM carrinho WHERE id_produto = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idProduto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // método para obter todos os produtos no carrinho
    public List<Produto> listarProdutosNoCarrinho() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE id IN (SELECT id_produto FROM carrinho)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setPreco(rs.getInt("preco"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}

    package ufrn.demo;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    import org.springframework.stereotype.Repository;

    @Repository
    public class ClienteDAO {
        public void cadastrarCliente(Cliente cliente) {
            Connection conexao = null;
            PreparedStatement stm = null;

            try {
                conexao = Conexao.getConnection();
                String sql = "INSERT INTO clientes (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
                stm = conexao.prepareStatement(sql);
                stm.setString(1, cliente.getNome());
                stm.setString(2, cliente.getEmail());
                stm.setString(3, cliente.getSenha());
                stm.setString(4, cliente.getTipo());

                int rowsAffected = stm.executeUpdate();
                System.out.println(rowsAffected + " registro(s) inserido(s) com sucesso!");

            } catch (SQLException ex) {
                System.out.println("Connection Failed! Check output console" + ex.getMessage());
            }
        }

        public List<Cliente> getAllCliente() {
            List<Cliente> clientes = new ArrayList<>();
            Connection conexao = null;
            ResultSet rs = null;
            PreparedStatement stm = null;

            try {
                conexao = Conexao.getConnection();
                String sql = "SELECT * FROM clientes";
                stm = conexao.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setTipo(rs.getString("tipo"));

                    clientes.add(cliente);
                }

            } catch (SQLException ex) {
                System.out.println("Connection Failed! Check output console" + ex.getMessage());
            }

            return clientes;
        }

        public Cliente autenticarCliente(String email, String senha) {
            Connection conexao = null;
            PreparedStatement stm = null;
            ResultSet rs = null;

            try {
                conexao = Conexao.getConnection();

                String sql = "SELECT * FROM clientes WHERE email = ? AND senha = ?";
                stm = conexao.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, senha);

                rs = stm.executeQuery();
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));

                    return cliente;
                }
            } catch (SQLException e) {
                System.out.println("Connection failed");

            }

            return null;
        }

    }

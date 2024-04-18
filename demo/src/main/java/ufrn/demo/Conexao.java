package ufrn.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection() throws SQLException {
        String dbUri = System.getenv("DATABASE_HOST");
        String dbPort = System.getenv("DATABASE_PORT");
        String dbName = System.getenv("DATABASE_NAME");
        String username = System.getenv("DATABASE_USERNAME");
        String password = System.getenv("DATABASE_PASSWORD");
        String dbUrl = "jdbc:postgresql://" + dbUri + ':' + dbPort + "/" + dbName + "?serverTimezone=UTC";

        System.out.println(dbUrl);

        return DriverManager.getConnection(dbUrl, username, password);
    }
}

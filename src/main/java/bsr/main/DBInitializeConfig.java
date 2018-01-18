package bsr.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInitializeConfig {

    private static final String TRANSFER_STATEMENT = "CREATE TABLE IF NOT EXISTS transfers (id INTEGER PRIMARY KEY, " +
            "accountFrom varchar(255), accountTo varchar(255), title varchar(255), name varchar(255))";
    private static final String ACCOUNT_STATEMENT = "CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY, " +
            "accountNumber varchar(255), balance Integer)";
    private static final String LOGIN_STATEMENT = "CREATE TABLE IF NOT EXISTS credentials (id INTEGER PRIMARY KEY, " +
            "login varchar(255) UNIQUE , password varchar(255))";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize(){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(TRANSFER_STATEMENT);
            statement.executeUpdate(ACCOUNT_STATEMENT);
            statement.executeUpdate(LOGIN_STATEMENT);
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

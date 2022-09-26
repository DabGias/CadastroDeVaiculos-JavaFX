package br.com.fiap.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.com.fiap.App;

public class ConnectionFactory {
    // private static final String url = "";
    // private static final String user = "";
    // private static final String password = "";

    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();

        props.load(App.class.getResourceAsStream("application.properties"));
        String url = props.getProperty("database.url");
        String user = props.getProperty("database.user");
        String password = props.getProperty("database.password");
        
        return DriverManager.getConnection(url, user, password);
    }
}

package me.arycer.dam.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    static {
        try (InputStream input = ClassLoader.getSystemResourceAsStream("db.properties")) {
            Properties p = new Properties();
            if (input == null) {
                throw new RuntimeException("No se ha encontrado el archivo db.properties");
            }

            p.load(input);
            String url = p.getProperty("db.url");
            String user = p.getProperty("db.user");
            String password = p.getProperty("db.password");

            System.out.printf("Conectando a %s con el usuario %s y la contraseña %s\n", url, user, password);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

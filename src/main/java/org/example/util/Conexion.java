package org.example.util;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 27-03-2025
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    // Detalles de la conexión
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/powergim?useSSL=false&useTimezone=true&serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    // Crea el pool de conexiones
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);
        dataSource.setInitialSize(10); // Número inicial de conexiones en el pool
        dataSource.setMaxTotal(100); // Número máximo de conexiones
        dataSource.setMaxIdle(30);  // Número máximo de conexiones inactivas
        dataSource.setMinIdle(10);   // Número mínimo de conexiones inactivas
        dataSource.setMaxWaitMillis(10000); // Tiempo máximo de espera para obtener una conexión
    }

    // Método para obtener una conexión desde el pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Método para cerrar el ResultSet
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    // Método para cerrar el PreparedStatement
    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}

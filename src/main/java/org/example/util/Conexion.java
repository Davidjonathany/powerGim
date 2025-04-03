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

        // Configuración básica del pool
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(150);
        dataSource.setMaxIdle(50);
        dataSource.setMinIdle(20);
        dataSource.setMaxWaitMillis(5000);

        // Protección contra conexiones rotas
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");

        // Chequeo periódico de conexiones inactivas
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);  // 1 minuto
        dataSource.setMinEvictableIdleTimeMillis(300000);    // 5 minutos

        // Manejo de conexiones abandonadas
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setRemoveAbandonedOnBorrow(true);
        dataSource.setLogAbandoned(true);
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

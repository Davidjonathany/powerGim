package org.example.util;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creacion 27-03-2025
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    //Creamos las variables para la conexion}
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/gim?useSSL=false&useTimezone=true&serverTimezone=UTC";
    //Creamos una variable para guardar el usuario
    private static final String JDBC_USERNAME="root";

    //Creamos una variable para guardar el usuario
    private static final String JDBC_PASSWORD="";
    //Implementamos un metodo
    public static DataSource getDataSource(){
        BasicDataSource ds= new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USERNAME);
        ds.setPassword(JDBC_PASSWORD);
        ds.setInitialSize(50);
        return ds;
    }
    //
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();

    }


    public static void close(ResultSet rs){
        try{
            rs.close();

        }
        catch (SQLException ex){
            ex.printStackTrace(System.out);
        }
    }
    public static void close(PreparedStatement stmt){
        try{
            stmt.close();
        }
        catch (SQLException e){
            e.printStackTrace(System.out);
        }
    }
}

package org.example;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creacion 27-03-2025
import org.example.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (Connection conn = Conexion.getConnection()) {
            // Consulta para la tabla Usuarios
            System.out.println("Datos de la tabla Usuarios:");
            try (PreparedStatement stmtUsuarios = conn.prepareStatement("SELECT * FROM Usuarios");
                 ResultSet rsUsuarios = stmtUsuarios.executeQuery()) {
                while (rsUsuarios.next()) {
                    System.out.print(rsUsuarios.getInt("id"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("nombre"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("apellido"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("usuario"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("clave"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("rol"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("correo"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("telefono"));
                    System.out.print(" | ");
                    System.out.print(rsUsuarios.getString("cedula"));
                    System.out.print(" | ");
                    System.out.println(rsUsuarios.getString("direccion"));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
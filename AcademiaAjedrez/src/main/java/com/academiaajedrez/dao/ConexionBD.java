package com.academiaajedrez.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/academia_ajedrez";
    private static final String USUARIO = "adminjm"; // Cambia esto a tu usuario de MySQL
    private static final String PASSWORD = "123456"; // Cambia esto a tu contraseña de MySQL

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    public static void probarConexion() {
        try (Connection conn = obtenerConexion()) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos exitosa.");
            } else {
                System.out.println("Conexión a la base de datos fallida.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}


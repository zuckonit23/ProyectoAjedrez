package com.academiaajedrez.controller;

import com.academiaajedrez.dao.UsuarioDAO;
import com.academiaajedrez.dao.UsuarioDAOImpl;
import com.academiaajedrez.exceptions.*;
import com.academiaajedrez.model.*;
import com.academiaajedrez.view.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAOImpl();
    }

    public void iniciarSesion(Scanner scanner) {
        while (true) {
            try {
                String username = solicitarString("Ingrese su nombre de usuario: ", scanner);
                String password = solicitarString("Ingrese su contraseña: ", scanner);

                Usuario usuario = usuarioDAO.obtenerUsuarioPorUsername(username);

                if (usuario == null || !usuario.getPassword().equals(password)) {
                    throw new EntradaInvalidaException("Credenciales incorrectas.");
                }

                System.out.println("¡Inicio de sesión exitoso!");

                if (usuario instanceof Profesor) {
                    ProfesorView profesorView = new ProfesorView((Profesor) usuario, usuarioDAO);
                    profesorView.mostrarMenuProfesor(scanner);
                } else if (usuario instanceof Alumno) {
                    Alumno alumno = (Alumno) usuario;
                    if (alumno.isAprobado()) {
                        AlumnoView alumnoView = new AlumnoView(alumno);
                        alumnoView.mostrarMenuAlumno(scanner);
                    } else {
                        System.out.println("Su registro está en espera de aprobación por parte de un profesor.");
                    }
                }
                break; // Salir del bucle después de iniciar sesión exitosamente
            } catch (EntradaInvalidaException | SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void registrarNuevoAlumno(Scanner scanner) {
        try {
            String username;
            while (true) {
                username = solicitarString("Ingrese su nombre de usuario: ", scanner);
                if (usuarioDAO.obtenerUsuarioPorUsername(username) != null) {
                    throw new UsuarioExistenteException("El nombre de usuario '" + username + "' ya está en uso. Por favor, elija otro.");
                } else {
                    break;
                }
            }

            String password = solicitarString("Ingrese una contraseña: ", scanner);
            String nombreCompleto = solicitarString("Ingrese su nombre completo: ", scanner);
            int edad = solicitarInt("Ingrese su edad: ", scanner);
            int eloFIDE = solicitarInt("Ingrese su Elo FIDE: ", scanner);
            int eloVirtual = solicitarInt("Ingrese su Elo Virtual: ", scanner);

            Date fechaIngreso = new Date();
            Alumno nuevoAlumno = new Alumno(username, password, nombreCompleto, edad, eloFIDE, eloVirtual, fechaIngreso);
            usuarioDAO.registrarUsuario(nuevoAlumno);

            System.out.println("Registro exitoso. Su cuenta está en espera de aprobación por parte de un profesor.");
        } catch (EntradaInvalidaException | UsuarioExistenteException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void registrarTorneo(Alumno alumno, Scanner scanner) {
        try {
            int id = solicitarInt("Ingrese el ID del torneo: ", scanner);
            String nombre = solicitarString("Ingrese el nombre del torneo: ", scanner);
            String lugar = solicitarString("Ingrese el lugar del torneo: ", scanner);
            Date fechaInicio = new Date(); // Simplificación para la prueba
            Date fechaFin = new Date(); // Simplificación para la prueba
            String ritmoJuego = solicitarRitmo("Ingrese el ritmo del juego (rapid, blitz, classic): ", scanner);
            int victorias = solicitarInt("Ingrese el número de victorias: ", scanner);
            int derrotas = solicitarInt("Ingrese el número de derrotas: ", scanner);
            int empates = solicitarInt("Ingrese el número de empates: ", scanner);

            Torneo torneo = new Torneo(id, nombre, lugar, fechaInicio, fechaFin, ritmoJuego, victorias, derrotas, empates);
            alumno.registrarTorneo(torneo);
            usuarioDAO.registrarTorneo(torneo, alumno.getIdAlumno());
            System.out.println("Torneo registrado exitosamente.");
        } catch (EntradaInvalidaException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    // Métodos para solicitar y validar entradas
    private String solicitarString(String mensaje, Scanner scanner) throws EntradaInvalidaException {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new EntradaInvalidaException("La entrada no puede estar vacía.");
        }
        return input;
    }

    private int solicitarInt(String mensaje, Scanner scanner) throws EntradaInvalidaException {
        try {
            System.out.print(mensaje);
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Entrada inválida. Por favor, ingrese un número entero.");
        }
    }
    
    private String solicitarRitmo(String mensaje, Scanner scanner) throws EntradaInvalidaException {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim().toLowerCase();
        if (!(input.equals("rapid") || input.equals("blitz") || input.equals("classic"))) {
            throw new EntradaInvalidaException("Ritmo de juego inválido. Debe ser 'rapid', 'blitz', o 'classic'.");
        }
        return input;
    }
}
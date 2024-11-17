package com.academiaajedrez.view;

import com.academiaajedrez.controller.ProfesorController;
import com.academiaajedrez.dao.UsuarioDAO;
import com.academiaajedrez.model.Alumno;
import com.academiaajedrez.model.Profesor;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProfesorView {
    private Profesor profesor;
    private ProfesorController profesorController;

    // Constructor que recibe un objeto Profesor y un objeto UsuarioDAO
    public ProfesorView(Profesor profesor, UsuarioDAO usuarioDAO) {
        this.profesor = profesor;
        this.profesorController = new ProfesorController(usuarioDAO);
    }

    public void mostrarMenuProfesor(Scanner scanner) {
        int opcion = -1;

        while (opcion != 4) {
            System.out.println("\n=== Menú Profesor ===");
            System.out.println("1. Ver alumnos en espera de aprobación");
            System.out.println("2. Aprobar alumno");
            System.out.println("3. Ver torneos de un alumno");
            System.out.println("4. Cerrar sesión");

            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        verAlumnosEnEspera();
                        break;
                    case 2:
                        aprobarAlumno(scanner);
                        break;
                    case 3:
                        verTorneosDeAlumno(scanner);
                        break;
                    case 4:
                        System.out.println("Cerrando sesión...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione entre 1 y 4.");
                        opcion = -1; // Mantener en el menú
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 4.");
                opcion = -1; // Mantener en el menú
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void verAlumnosEnEspera() throws SQLException {
        List<Alumno> alumnosPendientes = profesorController.verAlumnosEnEspera();
        if (alumnosPendientes.isEmpty()) {
            System.out.println("No hay alumnos en espera de aprobación.");
        } else {
            System.out.println("Alumnos en espera de aprobación:");
            for (Alumno alumno : alumnosPendientes) {
                System.out.println("ID: " + alumno.getIdAlumno() + ", Nombre: " + alumno.getNombreCompleto());
            }
        }
    }

    private void aprobarAlumno(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del alumno que desea aprobar: ");
        try {
            int idAlumno = Integer.parseInt(scanner.nextLine());
            profesorController.aprobarAlumno(idAlumno);
            System.out.println("Alumno aprobado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
        }
    }

    private void verTorneosDeAlumno(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del alumno para ver sus torneos: ");
        try {
            int idAlumno = Integer.parseInt(scanner.nextLine());
            profesorController.verTorneosDeAlumno(idAlumno);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
        }
    }
}



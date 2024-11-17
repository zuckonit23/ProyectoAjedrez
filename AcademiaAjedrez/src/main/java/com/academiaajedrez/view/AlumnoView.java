package com.academiaajedrez.view;

import com.academiaajedrez.controller.UsuarioController;
import com.academiaajedrez.model.Alumno;
import com.academiaajedrez.model.Torneo;

import java.util.Scanner;

public class AlumnoView {
    private Alumno alumno;
    private UsuarioController usuarioController;

    public AlumnoView(Alumno alumno) {
        this.alumno = alumno;
        this.usuarioController = new UsuarioController();
    }

    public void mostrarMenuAlumno(Scanner scanner) {
        int opcion = -1;
        do {
            System.out.println("\n=== Menú de Alumno ===");
            System.out.println("1. Registrar un nuevo torneo");
            System.out.println("2. Ver torneos registrados");
            System.out.println("3. Ver perfil");
            System.out.println("4. Salir");

            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarTorneo(scanner);
                        break;
                    case 2:
                        verTorneosRegistrados();
                        break;
                    case 3:
                        verPerfil();
                        break;
                    case 4:
                        System.out.println("Saliendo del menú de alumno...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        opcion = -1; // Mantener en el menú
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 4.");
                opcion = -1; // Mantener en el menú
            }
        } while (opcion != 4);
    }

    private void registrarTorneo(Scanner scanner) {
        usuarioController.registrarTorneo(alumno, scanner);
    }

    private void verTorneosRegistrados() {
        System.out.println("\n--- Torneos Registrados ---");
        if (alumno.getTorneos().isEmpty()) {
            System.out.println("No hay torneos registrados.");
        } else {
            for (Torneo torneo : alumno.getTorneos()) {
                System.out.println(torneo);
            }
        }
    }

    private void verPerfil() {
        System.out.println("\n--- Perfil del Alumno ---");
        System.out.println("Nombre: " + alumno.getNombreCompleto());
        System.out.println("Edad: " + alumno.getEdad());
        System.out.println("Elo FIDE: " + alumno.getEloFIDE());
        System.out.println("Elo Virtual: " + alumno.getEloVirtual());
        System.out.println("Fecha de Ingreso: " + alumno.getFechaIngreso());
        System.out.println("Estado de Aprobación: " + (alumno.isAprobado() ? "Aprobado" : "Pendiente"));
    }
}

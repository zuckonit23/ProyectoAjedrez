package com.academiaajedrez.view;

import com.academiaajedrez.controller.UsuarioController;
import com.academiaajedrez.dao.ConexionBD;

import java.util.Scanner;

public class MainView {
    public static void main(String[] args) {
        ConexionBD.probarConexion();
        Scanner scanner = new Scanner(System.in);
        UsuarioController usuarioController = new UsuarioController();
        int opcion = -1;

        do {
            System.out.println("\n=== Sistema de Gestión de Academia de Ajedrez ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar nuevo alumno");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        usuarioController.iniciarSesion(scanner);
                        break;
                    case 2:
                        usuarioController.registrarNuevoAlumno(scanner);
                        break;
                    case 3:
                        System.out.println("Gracias por utilizar el sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione entre 1 y 3.");
                        opcion = -1; // Forzar repetición en caso de error
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entre 1 y 3.");
                opcion = -1; // Forzar repetición en caso de excepción
            }
        } while (opcion != 3);

        scanner.close();
    }
}

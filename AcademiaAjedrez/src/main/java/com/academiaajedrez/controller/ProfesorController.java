package com.academiaajedrez.controller;

import com.academiaajedrez.dao.UsuarioDAO;
import com.academiaajedrez.model.Alumno;
import com.academiaajedrez.model.Profesor;
import com.academiaajedrez.model.Torneo;

import java.sql.SQLException;
import java.util.List;

public class ProfesorController {
    private UsuarioDAO usuarioDAO;

    public ProfesorController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Alumno> verAlumnosEnEspera() throws SQLException {
        return usuarioDAO.obtenerAlumnosPendientes();
    }

    public void aprobarAlumno(int idAlumno) throws SQLException {
        usuarioDAO.aprobarAlumno(idAlumno);
        System.out.println("Alumno aprobado exitosamente.");
    }
    
    public void verTorneosDeAlumno(int idAlumno) throws SQLException {
        List<Torneo> torneos = usuarioDAO.obtenerTorneosPorAlumno(idAlumno);
        if (torneos.isEmpty()) {
            System.out.println("El alumno no ha registrado torneos.");
        } else {
            System.out.println("Torneos registrados por el alumno:");
            for (Torneo torneo : torneos) {
                System.out.println(torneo);
            }
        }
    }
}
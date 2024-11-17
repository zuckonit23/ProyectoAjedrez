package com.academiaajedrez.dao;

import com.academiaajedrez.model.Alumno;
import com.academiaajedrez.model.Usuario;
import com.academiaajedrez.model.Profesor;
import java.sql.SQLException;
import java.util.List;
import com.academiaajedrez.model.Torneo;



public interface UsuarioDAO {
    void registrarUsuario(Usuario usuario) throws SQLException;
    Usuario obtenerUsuarioPorUsername(String username) throws SQLException;
    List<Alumno> obtenerAlumnosPendientes() throws SQLException;
    void aprobarAlumno(int idAlumno) throws SQLException;
    void registrarTorneo(Torneo torneo, int idAlumno) throws SQLException;

    List<Torneo> obtenerTorneosPorAlumno(int idAlumno) throws SQLException;
    
}
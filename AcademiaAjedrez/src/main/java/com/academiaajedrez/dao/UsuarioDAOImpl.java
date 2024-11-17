package com.academiaajedrez.dao;

import com.academiaajedrez.model.Alumno;
import com.academiaajedrez.model.Torneo;
import com.academiaajedrez.model.Usuario;
import com.academiaajedrez.model.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sqlUsuario = "INSERT INTO usuarios (username, password, tipo_usuario, nombre_completo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            stmtUsuario.setString(1, usuario.getUsername());
            stmtUsuario.setString(2, usuario.getPassword());
            stmtUsuario.setString(3, usuario instanceof Alumno ? "alumno" : "profesor");
            stmtUsuario.setString(4, usuario.getNombreCompleto());
            stmtUsuario.executeUpdate();

            ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                if (usuario instanceof Alumno) {
                    registrarAlumno((Alumno) usuario, userId, conn);
                } else if (usuario instanceof Profesor) {
                    registrarProfesor((Profesor) usuario, userId, conn);
                }
            }
        }
    }

    private void registrarAlumno(Alumno alumno, int userId, Connection conn) throws SQLException {
        String sqlAlumno = "INSERT INTO alumnos (id, nombre_completo, edad, elo_fide, elo_virtual, fecha_ingreso, aprobado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtAlumno = conn.prepareStatement(sqlAlumno)) {
            stmtAlumno.setInt(1, userId);
            stmtAlumno.setString(2, alumno.getNombreCompleto());
            stmtAlumno.setInt(3, alumno.getEdad());
            stmtAlumno.setInt(4, alumno.getEloFIDE());
            stmtAlumno.setInt(5, alumno.getEloVirtual());
            stmtAlumno.setDate(6, new java.sql.Date(alumno.getFechaIngreso().getTime()));
            stmtAlumno.setBoolean(7, alumno.isAprobado());
            stmtAlumno.executeUpdate();
        }
    }

    private void registrarProfesor(Profesor profesor, int userId, Connection conn) throws SQLException {
        String sqlProfesor = "INSERT INTO profesores (id, nombre_completo, correo_electronico, experiencia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtProfesor = conn.prepareStatement(sqlProfesor)) {
            stmtProfesor.setInt(1, userId);
            stmtProfesor.setString(2, profesor.getNombreCompleto());
            stmtProfesor.setString(3, profesor.getCorreoElectronico());
            stmtProfesor.setInt(4, profesor.getExperiencia());
            stmtProfesor.executeUpdate();
        }
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String tipoUsuario = rs.getString("tipo_usuario");
                    String nombreCompleto = rs.getString("nombre_completo");
                    String password = rs.getString("password");

                    if ("alumno".equals(tipoUsuario)) {
                        return obtenerAlumnoPorId(userId, username, password, nombreCompleto, conn);
                    } else if ("profesor".equals(tipoUsuario)) {
                        return obtenerProfesorPorId(userId, username, password, nombreCompleto, conn);
                    }
                }
            }
        }
        return null;
    }

    private Alumno obtenerAlumnoPorId(int id, String username, String password, String nombreCompleto, Connection conn) throws SQLException {
        String sqlAlumno = "SELECT * FROM alumnos WHERE id = ?";
        try (PreparedStatement stmtAlumno = conn.prepareStatement(sqlAlumno)) {
            stmtAlumno.setInt(1, id);
            try (ResultSet rsAlumno = stmtAlumno.executeQuery()) {
                if (rsAlumno.next()) {
                    int edad = rsAlumno.getInt("edad");
                    int eloFIDE = rsAlumno.getInt("elo_fide");
                    int eloVirtual = rsAlumno.getInt("elo_virtual");
                    Date fechaIngreso = rsAlumno.getDate("fecha_ingreso");
                    boolean aprobado = rsAlumno.getBoolean("aprobado");

                    Alumno alumno = new Alumno(username, password, nombreCompleto, edad, eloFIDE, eloVirtual, fechaIngreso);
                    alumno.setIdAlumno(id);
                    alumno.setAprobado(aprobado);
                    return alumno;
                }
            }
        }
        return null;
    }

    private Profesor obtenerProfesorPorId(int id, String username, String password, String nombreCompleto, Connection conn) throws SQLException {
        String sqlProfesor = "SELECT * FROM profesores WHERE id = ?";
        try (PreparedStatement stmtProfesor = conn.prepareStatement(sqlProfesor)) {
            stmtProfesor.setInt(1, id);
            try (ResultSet rsProfesor = stmtProfesor.executeQuery()) {
                if (rsProfesor.next()) {
                    String correoElectronico = rsProfesor.getString("correo_electronico");
                    int experiencia = rsProfesor.getInt("experiencia");

                    Profesor profesor = new Profesor(username, password, nombreCompleto, correoElectronico, experiencia);
                    profesor.setIdProfesor(id);
                    return profesor;
                }
            }
        }
        return null;
    }

    @Override
    public List<Alumno> obtenerAlumnosPendientes() throws SQLException {
        List<Alumno> alumnosPendientes = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.password, u.nombre_completo, a.edad, a.elo_fide, a.elo_virtual, a.fecha_ingreso, a.aprobado " +
                     "FROM usuarios u JOIN alumnos a ON u.id = a.id WHERE a.aprobado = FALSE";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nombreCompleto = rs.getString("nombre_completo");
                int edad = rs.getInt("edad");
                int eloFIDE = rs.getInt("elo_fide");
                int eloVirtual = rs.getInt("elo_virtual");
                Date fechaIngreso = rs.getDate("fecha_ingreso");

                Alumno alumno = new Alumno(username, password, nombreCompleto, edad, eloFIDE, eloVirtual, fechaIngreso);
                alumno.setIdAlumno(id);
                alumnosPendientes.add(alumno);
            }
        }
        return alumnosPendientes;
    }

    @Override
    public void aprobarAlumno(int idAlumno) throws SQLException {
        String sql = "UPDATE alumnos SET aprobado = TRUE WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void registrarTorneo(Torneo torneo, int idAlumno) throws SQLException {
        String sql = "INSERT INTO torneos (id, nombre, lugar, fecha_inicio, fecha_fin, ritmo_juego, victorias, derrotas, empates, id_alumno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, torneo.getId());
            stmt.setString(2, torneo.getNombre());
            stmt.setString(3, torneo.getLugar());
            stmt.setDate(4, new java.sql.Date(torneo.getFechaInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(torneo.getFechaFin().getTime()));
            stmt.setString(6, torneo.getRitmoJuego());
            stmt.setInt(7, torneo.getVictorias());
            stmt.setInt(8, torneo.getDerrotas());
            stmt.setInt(9, torneo.getEmpates());
            stmt.setInt(10, idAlumno);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Torneo> obtenerTorneosPorAlumno(int idAlumno) throws SQLException {
        List<Torneo> torneos = new ArrayList<>();
        String sql = "SELECT * FROM torneos WHERE id_alumno = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Torneo torneo = new Torneo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("lugar"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getString("ritmo_juego"),
                        rs.getInt("victorias"),
                        rs.getInt("derrotas"),
                        rs.getInt("empates")
                    );
                    torneos.add(torneo);
                }
            }
        }
        return torneos;
    }
}
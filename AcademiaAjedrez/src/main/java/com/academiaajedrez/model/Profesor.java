package com.academiaajedrez.model;

public class Profesor extends Usuario {
    private String correoElectronico;
    private int experiencia;

    public Profesor(String username, String password, String nombreCompleto, String correoElectronico, int experiencia) {
        super(username, password, nombreCompleto);
        this.correoElectronico = correoElectronico;
        this.experiencia = experiencia;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setIdProfesor(int id) {
        // Método para establecer el id del profesor después de obtenerlo de la base de datos
    }
}


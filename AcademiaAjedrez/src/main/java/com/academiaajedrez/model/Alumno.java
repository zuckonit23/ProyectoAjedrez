package com.academiaajedrez.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alumno extends Usuario {
    private int idAlumno;
    private int edad;
    private int eloFIDE;
    private int eloVirtual;
    private Date fechaIngreso;
    private boolean aprobado;
    private List<Torneo> torneos; // Lista de torneos jugados por el alumno

    public Alumno(String username, String password, String nombreCompleto, int edad, int eloFIDE, int eloVirtual, Date fechaIngreso) {
        super(username, password, nombreCompleto);
        this.edad = edad;
        this.eloFIDE = eloFIDE;
        this.eloVirtual = eloVirtual;
        this.fechaIngreso = fechaIngreso;
        this.aprobado = false; // Inicialmente no aprobado
        this.torneos = new ArrayList<>(); // Inicialización de la lista de torneos
    }

    // Getters y setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEloFIDE() {
        return eloFIDE;
    }

    public void setEloFIDE(int eloFIDE) {
        this.eloFIDE = eloFIDE;
    }

    public int getEloVirtual() {
        return eloVirtual;
    }

    public void setEloVirtual(int eloVirtual) {
        this.eloVirtual = eloVirtual;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public List<Torneo> getTorneos() {
        return torneos;
    }

    // Método para registrar un nuevo torneo en la lista del alumno
    public void registrarTorneo(Torneo torneo) {
        this.torneos.add(torneo);
    }
}

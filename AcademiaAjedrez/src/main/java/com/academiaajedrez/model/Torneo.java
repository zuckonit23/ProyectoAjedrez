package com.academiaajedrez.model;

import java.util.Date;

public class Torneo {
    private int id;
    private String nombre;
    private String lugar;
    private Date fechaInicio;
    private Date fechaFin;
    private String ritmoJuego;
    private int victorias;
    private int derrotas;
    private int empates;

    public Torneo(int id, String nombre, String lugar, Date fechaInicio, Date fechaFin, String ritmoJuego, int victorias, int derrotas, int empates) {
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ritmoJuego = ritmoJuego;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getRitmoJuego() {
        return ritmoJuego;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setRitmoJuego(String ritmoJuego) {
        this.ritmoJuego = ritmoJuego;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    @Override
    public String toString() {
        return "Torneo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ritmoJuego='" + ritmoJuego + '\'' +
                ", victorias=" + victorias +
                ", derrotas=" + derrotas +
                ", empates=" + empates +
                '}';
    }
}

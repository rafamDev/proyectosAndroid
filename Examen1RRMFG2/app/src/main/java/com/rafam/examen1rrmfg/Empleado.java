package com.rafam.examen1rrmfg;

public class Empleado {
    private String dni;
    private String nombre;
    private String apeliido;
    private double sueldo;
    private String moneda;
    private int departamento;

    public Empleado() {
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApeliido(String apeliido) {
        this.apeliido = apeliido;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApeliido() {
        return apeliido;
    }

    public double getSueldo() {
        return sueldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public int getDepartamento() {
        return departamento;
    }
}

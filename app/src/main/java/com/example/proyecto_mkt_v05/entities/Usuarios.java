package com.example.proyecto_mkt_v05.entities;

public class Usuarios {
    private String nombres, apellidos, email, contrasenha, ocupacion;

    public Usuarios() {
    }

    public Usuarios(String nombres, String apellidos, String email, String contrasenha, String ocupacion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasenha = contrasenha;
        this.ocupacion = ocupacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos(){
        return apellidos;
    }

    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}

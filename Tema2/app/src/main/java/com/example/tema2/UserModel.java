package com.example.tema2;

public class UserModel {
    private String nume;

    public UserModel(){}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public UserModel(String nume, String nota) {
        this.nume = nume;
        this.nota = nota;
    }

    private String nota;
}

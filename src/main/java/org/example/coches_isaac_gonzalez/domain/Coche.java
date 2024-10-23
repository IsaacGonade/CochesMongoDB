package org.example.coches_isaac_gonzalez.domain;

public class Coche {
    //atributos de la clase
    private String matricula;
    private String marca;
    private String modelo;
    private String tipo;

    //constructores
    public Coche() {
    }

    public Coche(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
    }


    //metodos getter and setter
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //metodo to String
    @Override
    public String toString() {
        return "Coche{" +
                "matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

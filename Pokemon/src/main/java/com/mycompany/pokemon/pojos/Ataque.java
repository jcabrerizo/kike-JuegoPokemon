package com.mycompany.pokemon.pojos;

public class Ataque {

    // ATRIBUTOS DE LOS ATAQUES:
    private String nombre; // Nombre del ataque.
    private int danio; // Daño que causa el ataque.
    private TipoAtaque tipo; // Tipo de elemento del ataque.

    // CONSTRUCTOR DE LOS ATAQUES:
    public Ataque(String nombre, int danio, TipoAtaque tipo) {
        this.nombre = nombre;
        this.danio = danio;
        this.tipo = tipo;
    }

    // GETS Y SETS:
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public TipoAtaque getTipo() {
        return tipo;
    }

    public String getNombreTipo() {
        return tipo.getNombre();
    }

    public void setTipo(TipoAtaque tipo) {
        this.tipo = tipo;
    }
}


package com.mycompany.pokemon.model;

public class Ataque {

    // ATRIBUTOS DE LOS ATAQUES:
    private final String nombre; // Nombre del ataque.
    private int danio; // Daño que causa el ataque.
    private int precision; // TODO
    private int critica; // TODO
    private int retroceso; // TODO
    private final TipoAtaque tipo; // Tipo de elemento del ataque.

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

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getCritica() {
        return critica;
    }

    public void setCritica(int critica) {
        this.critica = critica;
    }

    public int getRetroceso() {
        return retroceso;
    }

    public void setRetroceso(int retroceso) {
        this.retroceso = retroceso;
    }

    // CREACIÓN DE LOS OBJETOS DE LOS ATAQUES:
    public static Ataque newGolpeCabeza() {
        return new Ataque("GOLPE CABEZA", 6, TipoAtaque.NORMAL);
    }

    public static Ataque newCuchillada() {
        return new Ataque("CUCHILLADA", 6, TipoAtaque.NORMAL);
    }

    public static Ataque newHojaAfilada() {
        return new Ataque("HOJA AFILADA", 6, TipoAtaque.PLANTA);
    }

    public static Ataque newHidrobomba() {
        return new Ataque("HIDROBOMBA", 10, TipoAtaque.AGUA);
    }

    public static Ataque newLlamarada() {
        return new Ataque("LLAMARADA", 8, TipoAtaque.FUEGO);
    }

    public static Ataque newPunioHielo() {
        return new Ataque("PUÑO HIELO", 4, TipoAtaque.HIELO);
    }

    public static Ataque newPunioTrueno() {
        return new Ataque("PUÑO TRUENO", 4, TipoAtaque.ELECTRICO);
    }

    public static Ataque newBofetonLodo() {
        return new Ataque("BOFETÓN LODO", 4, TipoAtaque.TIERRA);
    }
}


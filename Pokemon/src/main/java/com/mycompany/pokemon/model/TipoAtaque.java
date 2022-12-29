package com.mycompany.pokemon.model;

public enum TipoAtaque {
    NORMAL("Normal"),
    PLANTA("Planta"),
    AGUA("Agua"),
    FUEGO("Fuego"),
    HIELO("Hielo"),
    TIERRA("Tierra"),
    ELECTRICO("Eléctrico");

    private final String nombre;

    TipoAtaque(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}

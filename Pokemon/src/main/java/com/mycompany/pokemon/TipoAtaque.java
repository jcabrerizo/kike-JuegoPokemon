package com.mycompany.pokemon;

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

    String getNombre() {
        return this.nombre;
    }
}

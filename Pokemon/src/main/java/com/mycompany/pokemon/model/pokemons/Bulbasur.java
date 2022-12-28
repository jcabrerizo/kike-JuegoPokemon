package com.mycompany.pokemon.model.pokemons;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

public class Bulbasur extends Pokemon {
    final static String NOMBRE = "BULBASUR";
    final static int VIDA = 100;
    final static int VELOCIDAD = 45;
    final static Ataque ATAQUE1 =  Ataque.newGolpeCabeza();
    final static Ataque ATAQUE2 =  Ataque.newHojaAfilada();
    final static Ataque ATAQUE3 =  Ataque.newBofetonLodo();

    @Override
    public String getAsciiArt() {
        return """
                ((|))" +
                "( { o_}" +
                " u uu """;
    }

    public Bulbasur(){
        this(NOMBRE, TipoPokemon.PLANTA, ATAQUE1, ATAQUE2, ATAQUE3, VIDA, VELOCIDAD);
    }

    // ("BULBASAUR", "Planta", Ataque.newGolpeCabeza(), Ataque.newHojaAfilada(), Ataque.newBofetonLodo(), 100, 45)
    protected Bulbasur(String nombre, TipoPokemon tipo, Ataque ataque1, Ataque ataque2, Ataque ataque3, int vida, int velocidad) {
        super(nombre, tipo, ataque1, ataque2, ataque3, vida, velocidad);
    }
}

package com.mycompany.pokemon.model.pokemons;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

public class Squirtle extends Pokemon {
    final static String NOMBRE = "SQUIRTLE";
    final static int VIDA = 100;
    final static int VELOCIDAD = 40;
    final static Ataque ATAQUE1 =  Ataque.newGolpeCabeza();
    final static Ataque ATAQUE2 =  Ataque.newHidrobomba();
    final static Ataque ATAQUE3 =  Ataque.newPunioTrueno();

    @Override
    public String getAsciiArt() {
        return """
                $  (o_)" +
                " \\((´)´" +
                "  u u""";
    }
    public Squirtle(){
        this(NOMBRE, TipoPokemon.AGUA, ATAQUE1, ATAQUE2, ATAQUE3, VIDA, VELOCIDAD);
    }

    //("SQUIRTLE", "Agua", Ataque.newGolpeCabeza(), Ataque.newHidrobomba(), Ataque.newPunioTrueno(), 100, 40)
    protected Squirtle(String nombre, TipoPokemon tipo, Ataque ataque1, Ataque ataque2, Ataque ataque3, int vida, int velocidad) {
        super(nombre, tipo, ataque1, ataque2, ataque3, vida, velocidad);
    }

}

package com.mycompany.pokemon.model.pokemons;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

public class Charmander extends Pokemon {
    final static String NOMBRE = "CHARMANDER";
    final static int VIDA = 100;
    final static int VELOCIDAD = 50;
    final static Ataque ATAQUE1 =  Ataque.newCuchillada();
    final static Ataque ATAQUE2 =  Ataque.newLlamarada();
    final static Ataque ATAQUE3 =  Ataque.newPunioHielo();

    @Override
    public String getAsciiArt() {
        return "$  (o_)\n" + 
               " \\((´)´\n" +
               "  u u";
    }

    public Charmander(){
        this(NOMBRE, TipoPokemon.FUEGO, ATAQUE1, ATAQUE2, ATAQUE3, VIDA, VELOCIDAD);
    }

    //("CHARMANDER", "Fuego", Ataque.newCuchillada(), Ataque.newLlamarada(), Ataque.newPunioHielo(), 100, 50)
    protected Charmander(String nombre, TipoPokemon tipo, Ataque ataque1, Ataque ataque2, Ataque ataque3, int vida, int velocidad) {
        super(nombre, tipo, ataque1, ataque2, ataque3, vida, velocidad);
    }
}

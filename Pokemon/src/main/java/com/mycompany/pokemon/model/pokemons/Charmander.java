package com.mycompany.pokemon.model.pokemons;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

public class Charmander extends Pokemon {
    public Charmander(String nombre, String tipo, Ataque ataque1, Ataque ataque2, Ataque ataque3, int vida, int velocidad) {
        super(nombre, tipo, ataque1, ataque2, ataque3, vida, velocidad);
    }
}

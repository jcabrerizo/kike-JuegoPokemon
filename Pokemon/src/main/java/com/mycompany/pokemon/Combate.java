package com.mycompany.pokemon;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

import java.util.*;

public class Combate {

    // ATRIBUTOS DEL COMBATE:
    Pokemon miPokemon; // Pokémon del jugador.
    Pokemon rivalPokemon; // Pokémon del rival.
    String continuar; // Decide continuar con el siguiente turno.
    String usar; // Decide el uso del objeto de la mochila.
    boolean turnoJugador; // Turno del jugador (true) o del rival (false).
    boolean retroceder; // Decide si el turno se repite o no por el efecto de retroceso.
    boolean mochilaVacia; // Determina si la mochila está llena o no.
    boolean salirMochila; // Determina la salida de la mochila.
    boolean objetoUsado; // Determina si el objeto fue usado.
    int opcion; // Decide el ataque del jugador.
    int opcionRival; // Decide el ataque del rival.
    int danioBase; // Almacena el daño base del ataque para recuperarlo después de usar modificadores de daño por efectividad, para que no se acumule en los turnos siguientes.
    double probFallo; // Almacena el valor numérico para la probabilidad de fallar un ataque.
    double probCritico; // Almacena el valor numérico para la probabilidad de que un ataque sea un golpe crítico.
    double probRetroceso; // Almacena el valor numérico para la probabilidad de que un ataque haga retroceder.
    double numAleatorio; // Almacena un valor numérico para poder realizar los cálculos aleatorios.

    Scanner lectura = new Scanner(System.in); // Se declara la variable "lectura" de la clase "Scanner" para poder hacer introducciones por teclado.
    Random aleatorio = new Random(); // Se crea la variable "aleatorio" para darle valor posteriormente a "opcionRival".

    // CONSTRUCTOR DEL COMBATE:
    public Combate(Pokemon miPokemon, Pokemon rivalPokemon) {
        this.miPokemon = miPokemon;
        this.rivalPokemon = rivalPokemon;
    }

    // MÉTODO PARA GENERAR EL COMBATE:
    public void iniciarCombate() {

        // Se decide el primer turno según la velocidad de cada Pokémon:
        turnoJugador = miPokemon.getVelocidad() >= rivalPokemon.getVelocidad(); // Si la velocidad de "miPokemon" es mayor que la de "rivalPokemon", se devuelve "true".

        if (turnoJugador) {
            System.out.println("\n" + miPokemon.getNombre() + " es más rápido, comienza primero.");
        } else {
            System.out.println("\n" + rivalPokemon.getNombre() + " es más rápido, comienza primero.");
        }

        do {

            if (turnoJugador && miPokemon.getVida() > 0) {

                // TURNO DEL JUGADOR:
                do {

                    do {

                        retroceder = false;
                        objetoUsado = false;

                        System.out.println("\n**************** TU TURNO: ****************");
                        System.out.println("\n¿Qué debería hacer " + miPokemon.getNombre() + "?");
                        System.out.println("1. " + miPokemon.getAtaque1().getNombre());
                        System.out.println("2. " + miPokemon.getAtaque2().getNombre());
                        System.out.println("3. " + miPokemon.getAtaque3().getNombre());
                        System.out.println("4. -> Ver datos del Pokémon");
                        System.out.println("5. -> Mochila");
                        System.out.println("6. -> EXIT");

                        opcion = lectura.nextInt();

                        if (opcion < 1 || opcion > 6) {
                            System.out.println("\n¡Debes introducir un número del 1 al 6 para seleccionar una opción!");
                        }

                    } while (opcion < 1 || opcion > 6); // Mientras no se seleccione alguna de esas opciones numéricas, se repetirá el menú.

                    if (opcion == 1) {

                        danioBase = miPokemon.getAtaque1().getDanio(); // Guarda el daño inicial del ataque.
                        calcularDanio(miPokemon.getAtaque1(), rivalPokemon); // Llamada al método para calcular el daño del ataque 1.

                        rivalPokemon.setVida(rivalPokemon.getVida() - miPokemon.getAtaque1().getDanio()); // Se calcula el daño recibido en la vida del rival.
                        System.out.println("\n" + miPokemon.getNombre() + " usó " + miPokemon.getAtaque1().getNombre() + " causando " + miPokemon.getAtaque1().getDanio() + " puntos de daño.");

                        miPokemon.getAtaque1().setDanio(danioBase); // Recupera el daño inicial del ataque.

                    } else if (opcion == 2) {

                        danioBase = miPokemon.getAtaque2().getDanio(); // Guarda el daño inicial del ataque.
                        calcularDanio(miPokemon.getAtaque2(), rivalPokemon); // Se llama al método para calcular el daño del ataque 2.

                        rivalPokemon.setVida(rivalPokemon.getVida() - miPokemon.getAtaque2().getDanio()); // Se calcula el daño recibido en la vida del rival.
                        System.out.println("\n" + miPokemon.getNombre() + " usó " + miPokemon.getAtaque2().getNombre() + " causando " + miPokemon.getAtaque2().getDanio() + " puntos de daño.");

                        miPokemon.getAtaque2().setDanio(danioBase); // Recupera el daño inicial del ataque.

                    } else if (opcion == 3) {

                        danioBase = miPokemon.getAtaque3().getDanio(); // Guarda el daño inicial del ataque.
                        calcularDanio(miPokemon.getAtaque3(), rivalPokemon); // Se llama al método para calcular el daño del ataque 3.

                        rivalPokemon.setVida(rivalPokemon.getVida() - miPokemon.getAtaque3().getDanio()); // Se calcula el daño recibido en la vida del rival.
                        System.out.println("\n" + miPokemon.getNombre() + " usó " + miPokemon.getAtaque3().getNombre() + " causando " + miPokemon.getAtaque3().getDanio() + " puntos de daño.");

                        miPokemon.getAtaque3().setDanio(danioBase); // Recupera el daño inicial del ataque.

                    } else if (opcion==6) {
                    	System.out.println("Fin de programa III");
                    	System.exit(0);
                    } else if (opcion == 4) {

                        if (miPokemon.getNombre().equals("BULBASAUR")) {

                            System.out.println("\n ----------------------\n"
                                    + "|      BULBASAUR       |\n"
                                    + "| 	 ((|))         |\n"
                                    + "| 	 ( { o_}       |\n"
                                    + "| 	  u uu	       |\n"
                                    + " ----------------------");
                            System.out.println("Tipo: Planta/Veneno");
                            System.out.println("PS: 100");
                            System.out.println("Velocidad: 45");
                            System.out.println("\nGOLPE CABEZA (NORMAL):\n"
                                    + "-> Daño: 6\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 5%\n"
                                    + "-> Prob. retroceso: 30%");
                            System.out.println("\nHOJA AFILADA (PLANTA):\n"
                                    + "-> Daño: 6\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 20%");
                            System.out.println("\nBOFETÓN LODO (TIERRA):\n"
                                    + "-> Daño: 4\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 5%");

                        } else if (miPokemon.getNombre().equals("SQUIRTLE")) {

                            System.out.println("\n ----------------------\n"
                                    + "|       SQUIRTLE       |\n"
                                    + "| 	  ( o_)        |\n"
                                    + "|       @(#|\\)\\        |\n"
                                    + "|         u u          |\n"
                                    + " ----------------------");
                            System.out.println("Tipo: Agua");
                            System.out.println("PS: 100");
                            System.out.println("Velocidad: 40");
                            System.out.println("\nGOLPE CABEZA (NORMAL):\n"
                                    + "-> Daño: 6\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 5%\n"
                                    + "-> Prob. retroceso: 30%");
                            System.out.println("\nHIDROBOMBA (AGUA):\n"
                                    + "-> Daño: 10\n"
                                    + "-> Precisión: 80%\n"
                                    + "-> Prob. crítica: 5%");
                            System.out.println("\nPUÑO HIELO (HIELO):\n"
                                    + "-> Daño: 4\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 5%");

                        } else if (miPokemon.getNombre().equals("CHARMANDER")) {

                            System.out.println("\n ----------------------\n"
                                    + "|      CHARMANDER      |\n"
                                    + "|       $  (o_)        |\n"
                                    + "|        \\((´)´        |\n"
                                    + "|         u u          |\n"
                                    + " ----------------------");
                            System.out.println("Tipo: Fuego");
                            System.out.println("PS: 100");
                            System.out.println("Velocidad: 50");
                            System.out.println("\nCUCHILLADA (NORMAL):\n"
                                    + "-> Daño: 6\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 40%");
                            System.out.println("\nLLAMARADA (FUEGO):\n"
                                    + "-> Daño: 8\n"
                                    + "-> Precisión: 85%\n"
                                    + "-> Prob. crítica: 5%");
                            System.out.println("\nPUÑO TRUENO (ELÉCTRICO):\n"
                                    + "-> Daño: 4\n"
                                    + "-> Precisión: 100%\n"
                                    + "-> Prob. crítica: 5%");
                        }

                    } else if (opcion == 5) {

                        if (!mochilaVacia) { // Si la mochila está llena, se ejecutan las siguientes líneas.

                            System.out.println("\n _________\n"
                                    + "|_)_______)\n"
                                    + "|| |_| |_||\n"
                                    + "||________|\n"
                                    + "| |        |\n"
                                    + "|_|________|");
                            System.out.println("\n -> x1 SUPERPOCIÓN");
                            System.out.println("(Restaura 50 de PS)");
                            System.out.println("\n¿Usar? (S/N)");
                            usar = lectura.next();

                            do {

                                if (usar.equalsIgnoreCase("S")) {
                                    miPokemon.setVida(miPokemon.getVida() + 50);
                                    if (miPokemon.getVida() > 100) {
                                        miPokemon.setVida(100); // Si al curarse la vida pasa de 100, se iguala a 100.
                                    }

                                    System.out.println(" ____ \n"
                                            + " |__|  \n"
                                            + "/ PS \\\n"
                                            + "\\____/");
                                    System.out.println("\n¡PS restaurados!");

                                    mochilaVacia = true; // La mochila pasa a estar vacía.
                                    salirMochila = true; // Se sale de la mochila

                                } else if (!usar.equalsIgnoreCase("S") && !usar.equalsIgnoreCase("N")) {
                                    System.out.println("\nDebes responder con 'S' para confirmar o 'N' para denegar.");
                                    salirMochila = true;

                                } else if (usar.equalsIgnoreCase("N")) {
                                    System.out.println("\nNo usaste ningún objeto.");
                                    salirMochila = true; // Se sale de la mochila
                                }

                            } while (!salirMochila);

                        } else {
                            System.out.println("\n _________\n"
                                    + "|_)_______)\n"
                                    + "|| |_| |_||\n"
                                    + "||________|\n"
                                    + "| |        |\n"
                                    + "|_|________|");
                            System.out.println("\nLa mochila está vacía.");
                            objetoUsado = true;
                        }
                    }

                    if (miPokemon.getNombre().equals("BULBASAUR") && opcion != 4 && opcion != 5) { // Se valora también que al escoger las opciones 4 y 5 no se muestre el panel de combate.

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "| ((|))        (_o)  $ |\n"
                                + "| ( { o_}  VS  `(`))/  |\n"
                                + "|  u uu	         u u   |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");

                    } else if (miPokemon.getNombre().equals("SQUIRTLE") && opcion != 4 && opcion != 5) { // Se valora también que al escoger las opciones 4 y 5 no se muestre el panel de combate.

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "|   ( o_)        ((|)) |\n"
                                + "| @(#|\\)\\  VS  {_o } ) |\n"
                                + "|   u u          uu u  |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");

                    } else if (miPokemon.getNombre().equals("CHARMANDER") && opcion != 4 && opcion != 5) { // Se valora también que al escoger las opciones 4 y 5 no se muestre el panel de combate.

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "| $  (o_)      (_o )   |\n"
                                + "|  \\((´)´  VS  /(/|#)@ |\n"
                                + "|   u u          u u   |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");
                    }

                    // MENSAJE DE CONTINUAR TURNO:
                    do {

                        System.out.println("\nCONTINUAR(S)");

                        continuar = lectura.next();

                        if (continuar.equalsIgnoreCase("S")) {

                            turnoJugador = false; // Continúa con el turno del oponente.

                        } else {

                            System.out.println("\nEscribe 'S' para pasar al siguiente turno.");
                        }

                    } while (!continuar.equalsIgnoreCase("S"));

                    // Si se elige la opción 4 para ver los datos del Pokémon o la 5 saliendo de la mochila llena o habiendo usado previamente el objeto, no se saltará al siguiente turno.
                    if (opcion == 4 || (opcion == 5 && !mochilaVacia)) {
                        turnoJugador = true;
                    }

                } while (retroceder || objetoUsado);

            } else if (!turnoJugador && rivalPokemon.getVida() > 0) {

                // TURNO DE LA MÁQUINA:
                do {

                    retroceder = false;

                    opcionRival = aleatorio.nextInt(3) + 1; // La variable obtiene el valor aleatorio de 1 a 3. Para evitar que sea de 0 a 2, se añade +1.

                    System.out.println("\n********** TURNO DE TU OPONENTE: **********");

                    if (opcionRival == 1) {

                        danioBase = rivalPokemon.getAtaque1().getDanio(); // Guarda el daño inicial del ataque.

                        calcularDanio(rivalPokemon.getAtaque1(), miPokemon); // Se llama al método para calcular el daño del ataque 1.
                        miPokemon.setVida(miPokemon.getVida() - rivalPokemon.getAtaque1().getDanio()); // Se calcula el daño recibido en la vida del jugador.
                        System.out.println("\n" + rivalPokemon.getNombre() + " enemigo usó " + rivalPokemon.getAtaque1().getNombre() + " causando " + rivalPokemon.getAtaque1().getDanio() + " puntos de daño.");

                        rivalPokemon.getAtaque1().setDanio(danioBase); // Recupera el daño inicial del ataque.

                    } else if (opcionRival == 2) {

                        danioBase = rivalPokemon.getAtaque2().getDanio(); // Guarda el daño inicial del ataque.

                        calcularDanio(rivalPokemon.getAtaque2(), miPokemon); // Se llama al método para calcular el daño del ataque 2.
                        miPokemon.setVida(miPokemon.getVida() - rivalPokemon.getAtaque2().getDanio()); // Se calcula el daño recibido en la vida del jugador.
                        System.out.println("\n" + rivalPokemon.getNombre() + " enemigo usó " + rivalPokemon.getAtaque2().getNombre() + " causando " + rivalPokemon.getAtaque2().getDanio() + " puntos de daño.");

                        rivalPokemon.getAtaque2().setDanio(danioBase); // Recupera el daño inicial del ataque.

                    } else if (opcionRival == 3) {

                        danioBase = rivalPokemon.getAtaque2().getDanio(); // Guarda el daño inicial del ataque.

                        calcularDanio(rivalPokemon.getAtaque3(), miPokemon); // Se llama al método para calcular el daño del ataque 3.
                        miPokemon.setVida( miPokemon.getVida() - rivalPokemon.getAtaque3().getDanio()); // Se calcula el daño recibido en la vida del jugador.
                        System.out.println("\n" + rivalPokemon.getNombre() + " enemigo usó " + rivalPokemon.getAtaque3().getNombre() + " causando " + rivalPokemon.getAtaque3().getDanio() + " puntos de daño.");

                        rivalPokemon.getAtaque3().setDanio(danioBase); // Recupera el daño inicial del ataque.
                    }

                    if (miPokemon.getNombre().equals("BULBASAUR")) {

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "| ((|))        (_o)  $ |\n"
                                + "| ( { o_}  VS  `(`))/  |\n"
                                + "|  u uu	         u u   |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");

                    } else if (miPokemon.getNombre().equals("SQUIRTLE")) {

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "|   ( o_)        ((|)) |\n"
                                + "| @(#|\\)\\  VS  {_o } ) |\n"
                                + "|   u u          uu u  |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");

                    } else if (miPokemon.getNombre().equals("CHARMANDER")) {

                        System.out.println(" ----------------------\n"
                                + "|                      |\n"
                                + "| $  (o_)      (_o )   |\n"
                                + "|  \\((´)´  VS  /(/|#)@ |\n"
                                + "|   u u          u u   |\n"
                                + " ----------------------\n"
                                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                                + " ----------------------");
                    }

                    do {

                        System.out.println("\nCONTINUAR(S)");

                        continuar = lectura.next();

                        if (continuar.equalsIgnoreCase("S")) {

                            turnoJugador = true; // Continúa con el turno del jugador.

                        } else {

                            System.out.println("\nEscribe 'S' para pasar al siguiente turno.");
                        }

                    } while (!continuar.equalsIgnoreCase("S"));

                } while (retroceder);
            }

        } while (miPokemon.getVida() > 0 && rivalPokemon.getVida() > 0); // El combate continuará hasta que uno de los dos Pokémon llegue a 0 de vida.

        // MENSAJES DE VICTORIA Y DERROTA:
        if (rivalPokemon.getVida() <= 0) {
            System.out.println("\n¡VICTORIA!");
            System.out.println(rivalPokemon.getNombre() + " se ha debilitado. " + "¡" + miPokemon.getNombre() + " es el ganador!");
        } else if (miPokemon.getVida() <= 0) {
            System.out.println("\n¡DERROTA!");
            System.out.println(miPokemon.getNombre() + " se ha debilitado. " + "¡" + rivalPokemon.getNombre() + " es el ganador!");
        }
    }

    // MÉTODO PARA CALCULAR LOS DAÑOS BAJO MODIFICADORES:
    public void calcularDanio(Ataque ataque, Pokemon rival) {

        // MODIFICADORES TIPO AGUA:
        if (ataque.getNombreTipo().equals("Agua") && (rival.getTipo().equals(Pokemon.TipoPokemon.AGUA) || rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // AGUA VS AGUA O PLANTA = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Agua") && (rival.getTipo().equals(Pokemon.TipoPokemon.FUEGO))) {
            // AGUA VS FUEGO = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES TIPO PLANTA:
        if (ataque.getNombreTipo().equals("Planta") && (rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA) || rival.getTipo().equals(Pokemon.TipoPokemon.FUEGO))) {
            // PLANTA VS PLANTA O FUEGO = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Planta") && (rival.getTipo().equals(Pokemon.TipoPokemon.AGUA))) {
            // PLANTA VS AGUA = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES TIPO FUEGO:
        if (ataque.getNombreTipo().equals("Fuego") && (rival.getTipo().equals(Pokemon.TipoPokemon.FUEGO) || rival.getTipo().equals(Pokemon.TipoPokemon.AGUA))) {
            // FUEGO VS FUEGO O AGUA = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Fuego") && (rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // FUEGO VS PLANTA = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES TIPO TIERRA:
        if (ataque.getNombreTipo().equals("Tierra") && (rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // TIERRA VS PLANTA = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Tierra") && (rival.getTipo().equals(Pokemon.TipoPokemon.FUEGO))) {
            // TIERRA VS FUEGO = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES TIPO HIELO:
        if (ataque.getNombreTipo().equals("Hielo") && (rival.getTipo().equals(Pokemon.TipoPokemon.FUEGO) || rival.getTipo().equals(Pokemon.TipoPokemon.AGUA))) {
            // HIELO VS FUEGO O AGUA = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Hielo") && (rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // HIELO VS PLANTA = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES TIPO ELÉCTRICO:
        if (ataque.getNombreTipo().equals("Eléctrico") && (rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // ELÉCTRICO VS PLANTA = /2
            ataque.setDanio(ataque.getDanio()/2);
            System.out.println("\nNo es muy eficaz...");
        } else if (ataque.getNombreTipo().equals("Eléctrico") && (rival.getTipo().equals(Pokemon.TipoPokemon.AGUA))) {
            // ELÉCTRICO VS AGUA = *2
            ataque.setDanio(ataque.getDanio()*2);
            System.out.println("\n¡Es muy eficaz!");
        }

        // MODIFICADORES PARA ACERTAR, HACER GOLPES CRÍTICOS, Y HACER RETROCEDER, CLASIFICADOS POR ATAQUES:
        if (ataque.getNombre().equals("GOLPE CABEZA")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0.30; // 30% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("CUCHILLADA")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.40; // 40% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("HOJA AFILADA")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.20; // 20% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\n¡El ataque ha fallado!");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("HIDROBOMBA")) {

            probFallo = 0.20; // 20% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("LLAMARADA")) {

            probFallo = 0.15; // 15% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("PUÑO HIELO")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("PUÑO TRUENO")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }

        if (ataque.getNombre().equals("BOFETÓN LODO")) {

            probFallo = 0; // 0% de probabilidad de fallo.
            probCritico = 0.05; // 5% de probabilidad de golpe crítico.
            probRetroceso = 0; // 0% de probabilidad de retroceso.

            // PRECISIÓN:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probFallo) {
                // Si el número aleatorio es menor que la probabilidad, el ataque falla.
                ataque.setDanio(0);
                System.out.println("\nEl ataque ha fallado.");
            }
            // PROBABILIDAD CRÍTICA:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probCritico && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y ser crítico.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace el doble de daño.
                ataque.setDanio(ataque.getDanio()*2);
                System.out.println("\n¡Golpe crítico!");
            }
            // PROBABILIDAD DE RETROCESO:
            numAleatorio = aleatorio.nextDouble(); // Genera un número aleatorio entre 0 y 1.
            if (numAleatorio < probRetroceso && ataque.getDanio() > 0) { // Se valora también que "ataque.getDanio()" sea mayor que cero para evitar que un golpe se pueda fallar y hacer retroceder.
                // Si el número aleatorio es menor que la probabilidad, el ataque hace retroceder.
                retroceder = true;
                System.out.println("\n¡El ataque hizo retroceder!");
            }
        }
    }
}

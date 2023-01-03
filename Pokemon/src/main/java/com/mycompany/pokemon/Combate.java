package com.mycompany.pokemon;

import com.mycompany.pokemon.model.Ataque;
import com.mycompany.pokemon.model.Pokemon;

import java.util.*;

public class Combate {

    // ATRIBUTOS DEL COMBATE:
    final Pokemon miPokemon; // Pokémon del jugador.
    final Pokemon rivalPokemon; // Pokémon del rival.
    String continuar; // Decide continuar con el siguiente turno.
    String usar; // Decide el uso del objeto de la mochila.
    boolean turnoJugador; // Turno del jugador (true) o del rival (false).
    boolean retroceder; // Decide si el turno se repite o no por el efecto de retroceso.
    boolean mochilaVacia; // Determina si la mochila está llena o no.
    boolean salirMochila; // Determina la salida de la mochila.
    boolean objetoUsado; // Determina si el objeto fue usado.
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

        System.out.println("\n" + (turnoJugador ? miPokemon.getNombre() : rivalPokemon.getNombre()) + " es más rápido, comienza primero.");

        do {
            if (turnoJugador && miPokemon.getVida() > 0) {
                // TURNO DEL JUGADOR:
                do {
                    retroceder = false;
                    objetoUsado = false;
                    Accion accion = getAccionTurno(lectura);
                    if (accion.esAtaque) {
                        Ataque ataqueEscogido = getAtaque(miPokemon, accion.indiceAtaque);
                        danioBase = ataqueEscogido.getDanio(); // Guarda el daño inicial del ataque.
                        calcularDanio(ataqueEscogido, rivalPokemon); // Llamada al método para calcular el daño del ataque 1.
                        rivalPokemon.setVida(rivalPokemon.getVida() - ataqueEscogido.getDanio()); // Se calcula el daño recibido en la vida del rival.
                        System.out.println("\n" + miPokemon.getNombre() + " usó " + ataqueEscogido.getNombre() + " causando " + ataqueEscogido.getDanio() + " puntos de daño.");
                        ataqueEscogido.setDanio(danioBase); // Recupera el daño inicial del ataque.
                    } else if (accion.equals(Accion.VER_DATOS)) {
                        mostrarDatosPokemon(miPokemon);
                    } else if (accion.equals(Accion.MOCHILA)) {
                        if (!mochilaVacia) { // Si la mochila está llena, se ejecutan las siguientes líneas.
                            System.out.println("""
                                     _________
                                    |_)_______)
                                    || |_| |_||
                                    ||________|
                                    | |        |
                                    |_|________|""");
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

                                    System.out.println("""
                                             ____\s
                                             |__| \s
                                            / PS \\
                                            \\____/""");
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

                        } else { //cuando se ejecuta esto?
                            System.out.println("""

                                     _________
                                    |_)_______)
                                    || |_| |_||
                                    ||________|
                                    | |        |
                                    |_|________|""");
                            System.out.println("\nLa mochila está vacía.");
                            objetoUsado = true;
                        }
                    }
                    mostrarPanelCombate();
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
                    if (!accion.equals(Accion.VER_DATOS) && (!Accion.MOCHILA.equals(accion) || mochilaVacia)) {
                        //TODO: que es esto? por que es un if vacio? se puede invertir la condicion y dejar solo un if sin else si esta vacion
                    } else {
                        turnoJugador = true;
                    }
                } while (retroceder || objetoUsado);

            } else if (!turnoJugador && rivalPokemon.getVida() > 0) {
                Accion accionRival;
                // TURNO DE LA MÁQUINA:
                do {

                    retroceder = false;

                    opcionRival = aleatorio.nextInt(3) + 1; // La variable obtiene el valor aleatorio de 1 a 3. Para evitar que sea de 0 a 2, se añade +1.
                    accionRival = getAccionByIndex(opcionRival);

                    System.out.println("\n********** TURNO DE TU OPONENTE: **********");

                    if (accionRival.esAtaque) {
                        Ataque ataqueEscogido = getAtaque(rivalPokemon, accionRival.indiceAtaque);
                        danioBase = ataqueEscogido.getDanio(); // Guarda el daño inicial del ataque.
                        calcularDanio(ataqueEscogido, miPokemon); // Llamada al método para calcular el daño del ataque 1.
                        miPokemon.setVida(miPokemon.getVida() - ataqueEscogido.getDanio()); // Se calcula el daño recibido en la vida del rival.
                        System.out.println("\n" + rivalPokemon.getNombre() + " usó " + ataqueEscogido.getNombre() + " causando " + ataqueEscogido.getDanio() + " puntos de daño.");
                        ataqueEscogido.setDanio(danioBase); // Recupera el daño inicial del ataque.
                    }
                    mostrarPanelCombate();
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

    private void mostrarPanelCombate() {
        System.out.println(" ----------------------\n"
                + miPokemon.getAsciiArt()
                + "Vs"
                + rivalPokemon.getAsciiArt()
                + " ----------------------\n"
                + "  PS: " + miPokemon.getVida() + "       PS: " + rivalPokemon.getVida() + "\n"
                + " ----------------------");
    }

    private void mostrarDatosPokemon(Pokemon pokemon) {
        System.out.println("\n ----------------------");
        System.out.println(pokemon.getNombre().toUpperCase());
        System.out.println(pokemon.getAsciiArt());
        System.out.println(" ----------------------");
        // Pokemon
        System.out.println("Tipo: " + pokemon.getTipo());
        System.out.println("PS: " + pokemon.getVida());
        System.out.println("Velocidad: " + pokemon.getVelocidad());

        // Ataques
        mostrarDatosAtaque(pokemon.getAtaque1());
        mostrarDatosAtaque(pokemon.getAtaque2());
        mostrarDatosAtaque(pokemon.getAtaque3());
    }

    private void mostrarDatosAtaque(Ataque ataque) {
        System.out.printf("%n%s (%s):%n", ataque.getNombre(), ataque.getTipo());
        System.out.printf("-> Daño: %d%n", ataque.getDanio());
        System.out.printf("-> Precisión: %d%%%n", ataque.getPrecision());
        System.out.printf("-> Prob. crítica: %d%%%n", ataque.getCritica());
        if (ataque.getRetroceso() > 0) {
            System.out.printf("-> Prob. retroceso: %d%%%n", ataque.getRetroceso());
        }
    }

    private Ataque getAtaque(Pokemon miPokemon, int indiceAtaque) {
        if (indiceAtaque == 1) {
            return miPokemon.getAtaque1();
        } else if (indiceAtaque == 2) {
            return miPokemon.getAtaque2();
        } else if (indiceAtaque == 3) {
            return miPokemon.getAtaque3();
        }
        throw new IllegalArgumentException("Indice no valido");
    }

    private Accion getAccionTurno(Scanner lectura) {
        int accionInput;
        do {
            System.out.println("\n**************** TU TURNO: ****************");
            System.out.println("\n¿Qué debería hacer " + miPokemon.getNombre() + "?");
            System.out.println("1. " + miPokemon.getAtaque1().getNombre());
            System.out.println("2. " + miPokemon.getAtaque2().getNombre());
            System.out.println("3. " + miPokemon.getAtaque3().getNombre());
            System.out.println("4. -> Ver datos del Pokémon");
            System.out.println("5. -> Mochila");
            accionInput = lectura.nextInt();

            if (accionInput < 1 || accionInput > 5) {
                System.out.println("\n¡Debes introducir un número del 1 al 5 para seleccionar una opción!");
            }
        } while (accionInput < 1 || accionInput > 5); // Mientras no se seleccione alguna de esas opciones numéricas, se repetirá el menú.
        Accion accion = getAccionByIndex(accionInput);
        if (accion != null) return accion;
        throw new IllegalArgumentException("Error leyendo la accion");
    }

    private static Accion getAccionByIndex(int index) {
        switch (index) {
            case 1:
                return Accion.ATAQUE1;
            case 2:
                return Accion.ATAQUE2;
            case 3:
                return Accion.ATAQUE3;
            case 4:
                return Accion.VER_DATOS;
            case 5:
                return Accion.MOCHILA;
        }
        return null;
    }

    private enum Accion {
        ATAQUE1(true, 1),
        ATAQUE2(true, 2),
        ATAQUE3(true, 3),
        VER_DATOS(),
        MOCHILA();
        final boolean esAtaque;
        final int indiceAtaque;

        Accion(Boolean esAtaque, int indiceAtaque) {
            this.esAtaque = esAtaque;
            this.indiceAtaque = indiceAtaque;
        }

        Accion() {
            this.esAtaque = false;
            this.indiceAtaque = -1;
        }
    }

    // MÉTODO PARA CALCULAR LOS DAÑOS BAJO MODIFICADORES:
    public void calcularDanio(Ataque ataque, Pokemon rival) {

        // MODIFICADORES TIPO AGUA:
        if (ataque.getNombreTipo().equals("Agua") && (rival.getTipo().equals(Pokemon.TipoPokemon.AGUA) || rival.getTipo().equals(Pokemon.TipoPokemon.PLANTA))) {
            // AGUA VS AGUA O PLANTA = /2
            ataque.setDanio(ataque.getDanio() / 2);
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

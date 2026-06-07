package com.davidgarcia.lumen.niveles;

import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;

import java.util.List;
import java.util.function.Function;

/**
 * Datos de una sala individual del juego.
 * Contiene un factory que produce los enemigos de esta sala dado el personaje del jugador.
 * El factory permite que los enemigos referencien al personaje (Mirón, Devorador) sin
 * que la Sala tenga que conocer al jugador en su construcción.
 */
public class Sala {

    private final String identificador;
    private final Function<Personaje, List<Entidad>> generadorEnemigos;

    public Sala(String identificador, Function<Personaje, List<Entidad>> generadorEnemigos) {
        this.identificador = identificador;
        this.generadorEnemigos = generadorEnemigos;
    }

    public String getIdentificador() {
        return identificador;
    }

    public List<Entidad> generarEnemigos(Personaje personaje) {
        return generadorEnemigos.apply(personaje);
    }
}

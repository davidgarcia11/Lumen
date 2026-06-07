package com.davidgarcia.lumen.entidades.elementos;

import com.davidgarcia.lumen.entidades.Entidad;

/**
 * Objeto del escenario con el que Lumen puede interactuar.
 * Algunos elementos reaccionan al contacto (braseros) y otros requieren la tecla E
 * cuando Lumen está a rango (santuarios).
 */
public abstract class ElementoInteractuable extends Entidad {

    protected ElementoInteractuable(float x, float y) {
        super(x, y);
    }
}

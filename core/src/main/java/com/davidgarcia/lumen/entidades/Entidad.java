package com.davidgarcia.lumen.entidades;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/** Cualquier cosa que vive en el mundo del juego con posición. */
public abstract class Entidad {

    protected final Vector2 posicion = new Vector2();

    protected Entidad(float x, float y) {
        this.posicion.set(x, y);
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    /** Actualiza el estado de la entidad un frame. */
    public abstract void actualizar(float delta);

    /** Dibuja la entidad usando el renderer dado. */
    public abstract void dibujar(ShapeRenderer renderer);
}

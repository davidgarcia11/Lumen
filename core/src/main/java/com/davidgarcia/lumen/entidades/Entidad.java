package com.davidgarcia.lumen.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** Cualquier cosa que vive en el mundo del juego con posición. */
public abstract class Entidad {

    protected final Vector2 posicion = new Vector2();
    protected final Rectangle hitbox = new Rectangle();

    protected Entidad(float x, float y) {
        this.posicion.set(x, y);
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    /** Hitbox de contacto. Las subclases deben mantenerla actualizada con su posición y tamaño. */
    public Rectangle getHitbox() {
        return hitbox;
    }

    public abstract void actualizar(float delta);

    /**
     * Dibuja la entidad.
     * @param batch SpriteBatch para dibujar sprites (debe estar entre begin/end del llamador).
     * @param shapes ShapeRenderer para dibujar geometría (debe estar entre begin/end del llamador en otra pasada).
     */
    public abstract void dibujar(SpriteBatch batch, ShapeRenderer shapes);
}

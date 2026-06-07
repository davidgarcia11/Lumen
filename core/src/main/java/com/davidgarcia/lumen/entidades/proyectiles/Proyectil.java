package com.davidgarcia.lumen.entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.Entidad;

/**
 * Proyectil con dirección, velocidad y alcance máximo.
 * Cuando recorre su alcance o impacta algo, queda marcado como inactivo
 * para que el bucle de juego lo elimine de la lista de entidades.
 */
public abstract class Proyectil extends Entidad {

    protected final Vector2 direccion = new Vector2();
    protected final float velocidad;
    protected final float alcance;
    protected float distanciaRecorrida = 0f;
    protected boolean activo = true;

    protected Proyectil(float x, float y, Vector2 direccion, float velocidad, float alcance) {
        super(x, y);
        this.direccion.set(direccion).nor();
        this.velocidad = velocidad;
        this.alcance = alcance;
    }

    @Override
    public void actualizar(float delta) {
        if (!activo) return;
        float dx = direccion.x * velocidad * delta;
        float dy = direccion.y * velocidad * delta;
        posicion.x += dx;
        posicion.y += dy;
        distanciaRecorrida += (float) Math.sqrt(dx * dx + dy * dy);
        if (distanciaRecorrida >= alcance) {
            activo = false;
        }
        actualizarHitbox();
    }

    protected abstract void actualizarHitbox();

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }
}

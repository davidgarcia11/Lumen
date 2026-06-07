package com.davidgarcia.lumen.entidades.recolectables;

import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;

/**
 * Objeto del mundo que el jugador puede recoger pasando por encima.
 * Define el efecto que aplica a Lumen al ser recogido y cuántos puntos otorga.
 */
public abstract class Recolectable extends Entidad {

    protected final float tamano;
    protected boolean recogido = false;
    protected float tiempoFlotacion = 0f;

    protected Recolectable(float x, float y) {
        super(x, y);
        this.tamano = ConfiguracionJuego.RECOLECTABLE_TAMANO;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (recogido) return;
        tiempoFlotacion += delta;
        actualizarHitbox();
    }

    private void actualizarHitbox() {
        float mitad = tamano / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    public boolean estaRecogido() {
        return recogido;
    }

    /** Aplica el efecto del recolectable a Lumen y lo marca como recogido. */
    public void recoger(Personaje personaje) {
        if (recogido) return;
        aplicarEfecto(personaje);
        personaje.sumarPuntos(puntosOtorgados());
        recogido = true;
    }

    protected abstract void aplicarEfecto(Personaje personaje);
    protected abstract int puntosOtorgados();
}

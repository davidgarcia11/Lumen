package com.davidgarcia.lumen.entidades.npc;

import com.davidgarcia.lumen.entidades.Entidad;

/** Personaje no jugable. Cualquier criatura del mundo que no es el jugador. */
public abstract class NPC extends Entidad {

    protected float vida;
    protected final float vidaMaxima;
    protected final float danoAlJugador;

    protected NPC(float x, float y, float vidaMaxima, float danoAlJugador) {
        super(x, y);
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.danoAlJugador = danoAlJugador;
    }

    public boolean estaVivo() {
        return vida > 0f;
    }

    public void recibirDano(float cantidad) {
        if (cantidad <= 0f) return;
        vida = Math.max(0f, vida - cantidad);
    }

    public float getDanoAlJugador() {
        return danoAlJugador;
    }
}

package com.davidgarcia.lumen.ia.estados;

import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.ia.EstadoIA;

/** Tras recibir daño, el Devorador se aturde 1s antes de volver a perseguir. */
public class EstadoHerido implements EstadoIA<Devorador> {

    private static final float DURACION_HERIDO = 1f;

    private float tiempoEnEstado = 0f;

    @Override
    public void entrar(Devorador npc) {
        tiempoEnEstado = 0f;
    }

    @Override
    public void actualizar(Devorador npc, float delta) {
        tiempoEnEstado += delta;
        if (tiempoEnEstado >= DURACION_HERIDO) {
            npc.getMaquinaEstados().cambiarA(new EstadoDespierto());
        }
    }
}

package com.davidgarcia.lumen.ia.estados;

import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.ia.EstadoIA;

/** Tras detectar al jugador, el Mirón "fija la mirada" un breve instante antes de perseguirlo. */
public class EstadoAlerta implements EstadoIA<Miron> {

    private static final float DURACION_ALERTA = 1.5f;

    private float tiempoEnEstado = 0f;

    @Override
    public void entrar(Miron npc) {
        tiempoEnEstado = 0f;
    }

    @Override
    public void actualizar(Miron npc, float delta) {
        tiempoEnEstado += delta;

        if (npc.puedeVerJugador()) {
            npc.registrarUltimaPosicionJugador();
        }

        if (tiempoEnEstado >= DURACION_ALERTA) {
            if (npc.puedeVerJugador()) {
                npc.getMaquinaEstados().cambiarA(new EstadoPersigue());
            } else {
                npc.getMaquinaEstados().cambiarA(new EstadoVigila());
            }
        }
    }
}

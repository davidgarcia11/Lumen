package com.davidgarcia.lumen.ia.estados;

import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.ia.EstadoIA;

/** El Devorador permanece inmóvil hasta que detecta al jugador en la sala. */
public class EstadoDormido implements EstadoIA<Devorador> {

    @Override
    public void actualizar(Devorador npc, float delta) {
        if (npc.detectaJugador()) {
            npc.getMaquinaEstados().cambiarA(new EstadoDespierto());
        }
    }
}

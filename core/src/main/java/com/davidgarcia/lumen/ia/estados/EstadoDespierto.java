package com.davidgarcia.lumen.ia.estados;

import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.ia.EstadoIA;

/** El Devorador persigue al jugador directamente. */
public class EstadoDespierto implements EstadoIA<Devorador> {

    @Override
    public void actualizar(Devorador npc, float delta) {
        npc.perseguirJugador(delta);
    }
}

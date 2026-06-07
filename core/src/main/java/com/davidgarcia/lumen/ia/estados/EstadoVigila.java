package com.davidgarcia.lumen.ia.estados;

import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.ia.EstadoIA;

/** El Mirón gira despacio sobre sí mismo. Si detecta al jugador, pasa a ALERTA. */
public class EstadoVigila implements EstadoIA<Miron> {

    @Override
    public void actualizar(Miron npc, float delta) {
        npc.rotarVision(Miron.VELOCIDAD_GIRO * delta);

        if (npc.puedeVerJugador()) {
            npc.registrarUltimaPosicionJugador();
            npc.getMaquinaEstados().cambiarA(new EstadoAlerta());
        }
    }
}

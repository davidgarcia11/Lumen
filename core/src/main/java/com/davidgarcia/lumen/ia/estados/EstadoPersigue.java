package com.davidgarcia.lumen.ia.estados;

import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.ia.EstadoIA;

/** El Mirón se desplaza hacia la última posición conocida del jugador. */
public class EstadoPersigue implements EstadoIA<Miron> {

    private static final float UMBRAL_LLEGADA = 2f;

    @Override
    public void actualizar(Miron npc, float delta) {
        if (npc.puedeVerJugador()) {
            npc.registrarUltimaPosicionJugador();
        }

        if (!npc.tieneObjetivo()) {
            npc.getMaquinaEstados().cambiarA(new EstadoVigila());
            return;
        }

        Vector2 destino = npc.getUltimaPosicionJugador();
        float distancia = new Vector2(destino).sub(npc.getPosicion()).len();

        if (distancia < UMBRAL_LLEGADA) {
            npc.olvidarObjetivo();
            npc.getMaquinaEstados().cambiarA(new EstadoVigila());
            return;
        }

        npc.moverHacia(destino, delta);
    }
}

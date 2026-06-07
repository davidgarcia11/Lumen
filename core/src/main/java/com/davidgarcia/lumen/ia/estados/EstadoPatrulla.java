package com.davidgarcia.lumen.ia.estados;

import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.npc.Acechante;
import com.davidgarcia.lumen.ia.EstadoIA;

/** El Acechante recorre indefinidamente la ruta entre sus dos puntos de patrulla. */
public class EstadoPatrulla implements EstadoIA<Acechante> {

    private static final float UMBRAL_DESTINO = 1f;

    @Override
    public void actualizar(Acechante npc, float delta) {
        Vector2 destino = npc.getDestinoActual();
        Vector2 hacia = new Vector2(destino).sub(npc.getPosicion());

        if (hacia.len() < UMBRAL_DESTINO) {
            npc.invertirDestino();
            return;
        }

        hacia.nor().scl(npc.getVelocidadPatrulla() * delta);
        npc.getPosicion().add(hacia);
    }
}

package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/** NPC patrullero que va y viene entre dos puntos fijos sin detectar al jugador. */
public class Acechante extends NPC {

    private static final float VIDA = 20f;
    private static final float DANO = 25f;
    private static final float VELOCIDAD = 25f;
    private static final int TAMANO = 10;

    private static final float UMBRAL_DESTINO = 1f;

    private static final Color COLOR_CUERPO = new Color(0.10f, 0.10f, 0.15f, 1f);
    private static final Color COLOR_OJOS = new Color(0.85f, 0.20f, 0.20f, 1f);

    private final Vector2 puntoA;
    private final Vector2 puntoB;
    private Vector2 destinoActual;

    public Acechante(float ax, float ay, float bx, float by) {
        super(ax, ay, VIDA, DANO);
        this.puntoA = new Vector2(ax, ay);
        this.puntoB = new Vector2(bx, by);
        this.destinoActual = puntoB;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (!estaVivo()) return;

        Vector2 hacia = new Vector2(destinoActual).sub(posicion);
        float distancia = hacia.len();

        if (distancia < UMBRAL_DESTINO) {
            destinoActual = (destinoActual == puntoA) ? puntoB : puntoA;
            return;
        }

        hacia.nor().scl(VELOCIDAD * delta);
        posicion.add(hacia);
        actualizarHitbox();
    }

    @Override
    public void dibujar(ShapeRenderer renderer) {
        if (!estaVivo()) return;

        float mitad = TAMANO / 2f;

        renderer.setColor(COLOR_CUERPO);
        renderer.rect(posicion.x - mitad, posicion.y - mitad, TAMANO, TAMANO);

        renderer.setColor(COLOR_OJOS);
        float ojoTamano = TAMANO * 0.18f;
        float ojoOffsetX = TAMANO * 0.22f;
        float ojoOffsetY = TAMANO * 0.10f;
        renderer.rect(posicion.x - ojoOffsetX - ojoTamano / 2f,
            posicion.y + ojoOffsetY,
            ojoTamano, ojoTamano);
        renderer.rect(posicion.x + ojoOffsetX - ojoTamano / 2f,
            posicion.y + ojoOffsetY,
            ojoTamano, ojoTamano);
    }

    private void actualizarHitbox() {
        float mitad = TAMANO / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO, TAMANO);
    }
}

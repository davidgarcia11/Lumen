package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.ia.MaquinaEstados;
import com.davidgarcia.lumen.ia.estados.EstadoPatrulla;

/** NPC patrullero que va y viene entre dos puntos fijos sin detectar al jugador. */
public class Acechante extends NPC {

    private static final float VIDA = 20f;
    private static final float DANO = 25f;
    private static final float VELOCIDAD_PATRULLA = 25f;
    private static final int TAMANO = 10;

    private static final Color COLOR_CUERPO = new Color(0.10f, 0.10f, 0.15f, 1f);
    private static final Color COLOR_OJOS = new Color(0.85f, 0.20f, 0.20f, 1f);

    private final Vector2 puntoA;
    private final Vector2 puntoB;
    private Vector2 destinoActual;

    private final MaquinaEstados<Acechante> maquinaEstados;

    public Acechante(float ax, float ay, float bx, float by) {
        super(ax, ay, VIDA, DANO);
        this.puntoA = new Vector2(ax, ay);
        this.puntoB = new Vector2(bx, by);
        this.destinoActual = puntoB;
        this.maquinaEstados = new MaquinaEstados<>(this, new EstadoPatrulla());
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (!estaVivo()) return;
        maquinaEstados.actualizar(delta);
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

    public Vector2 getDestinoActual() {
        return destinoActual;
    }

    public void invertirDestino() {
        destinoActual = (destinoActual == puntoA) ? puntoB : puntoA;
    }

    public float getVelocidadPatrulla() {
        return VELOCIDAD_PATRULLA;
    }

    private void actualizarHitbox() {
        float mitad = TAMANO / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO, TAMANO);
    }
}

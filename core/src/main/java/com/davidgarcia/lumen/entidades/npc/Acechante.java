package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.ia.MaquinaEstados;
import com.davidgarcia.lumen.ia.estados.EstadoPatrulla;
import com.davidgarcia.lumen.utiles.SpritesEnemigos;

/** NPC patrullero que va y viene entre dos puntos fijos sin detectar al jugador. */
public class Acechante extends NPC {

    private static final float VIDA = 20f;
    private static final float DANO = 25f;
    private static final float VELOCIDAD_PATRULLA = 25f;
    private static final int TAMANO_HITBOX = 10;
    private static final float TAMANO_VISUAL = 18f;

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
        SpritesEnemigos.acechante.actualizar(delta);
        actualizarHitbox();
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        if (!estaVivo()) return;
        var frame = SpritesEnemigos.acechante.getFrameActual();
        batch.draw(
            frame,
            posicion.x - TAMANO_VISUAL / 2f,
            posicion.y - TAMANO_VISUAL / 2f,
            TAMANO_VISUAL,
            TAMANO_VISUAL
        );
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
        float mitad = TAMANO_HITBOX / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO_HITBOX, TAMANO_HITBOX);
    }
}

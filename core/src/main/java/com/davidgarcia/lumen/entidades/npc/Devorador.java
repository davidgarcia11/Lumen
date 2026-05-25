package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.ia.MaquinaEstados;
import com.davidgarcia.lumen.ia.estados.EstadoDormido;
import com.davidgarcia.lumen.utiles.SpritesEnemigos;

/** NPC pesado que despierta al detectar al jugador y lo persigue por toda la sala. */
public class Devorador extends NPC {

    private static final float VIDA = 60f;
    private static final float DANO = 50f;
    private static final float VELOCIDAD_PERSECUCION = 32f;
    private static final int TAMANO_HITBOX = 16;
    private static final float TAMANO_VISUAL = 24f;

    /** Distancia a la que detecta al jugador y despierta. */
    public static final float RADIO_DETECCION = 90f;

    private final Personaje objetivo;
    private final MaquinaEstados<Devorador> maquinaEstados;

    public Devorador(float x, float y, Personaje objetivo) {
        super(x, y, VIDA, DANO);
        this.objetivo = objetivo;
        this.maquinaEstados = new MaquinaEstados<>(this, new EstadoDormido());
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (!estaVivo()) return;
        maquinaEstados.actualizar(delta);
        SpritesEnemigos.devorador.actualizar(delta);
        actualizarHitbox();
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        if (!estaVivo()) return;
        var frame = SpritesEnemigos.devorador.getFrameActual();
        batch.draw(
            frame,
            posicion.x - TAMANO_VISUAL / 2f,
            posicion.y - TAMANO_VISUAL / 2f,
            TAMANO_VISUAL,
            TAMANO_VISUAL
        );
    }

    public boolean detectaJugador() {
        if (objetivo == null) return false;
        return new Vector2(objetivo.getPosicion()).sub(posicion).len() <= RADIO_DETECCION;
    }

    public void perseguirJugador(float delta) {
        if (objetivo == null) return;
        Vector2 hacia = new Vector2(objetivo.getPosicion()).sub(posicion);
        float distancia = hacia.len();
        if (distancia < 1f) return;
        hacia.nor().scl(VELOCIDAD_PERSECUCION * delta);
        posicion.add(hacia);
    }

    public MaquinaEstados<Devorador> getMaquinaEstados() {
        return maquinaEstados;
    }

    @Override
    public void recibirDano(float cantidad) {
        super.recibirDano(cantidad);
        if (estaVivo()) {
            maquinaEstados.cambiarA(new com.davidgarcia.lumen.ia.estados.EstadoHerido());
        }
    }

    private void actualizarHitbox() {
        float mitad = TAMANO_HITBOX / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO_HITBOX, TAMANO_HITBOX);
    }
}

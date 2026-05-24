package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.ia.MaquinaEstados;
import com.davidgarcia.lumen.ia.estados.EstadoDormido;

/** NPC pesado que despierta al detectar al jugador y lo persigue por toda la sala. */
public class Devorador extends NPC {

    private static final float VIDA = 60f;
    private static final float DANO = 50f;
    private static final float VELOCIDAD_PERSECUCION = 32f;
    private static final int TAMANO = 16;

    /** Distancia a la que detecta al jugador y despierta. */
    public static final float RADIO_DETECCION = 90f;

    private static final Color COLOR_CUERPO = new Color(0.05f, 0.12f, 0.10f, 1f);
    private static final Color COLOR_OJOS = new Color(0.20f, 0.85f, 0.95f, 1f);

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
        actualizarHitbox();
    }

    @Override
    public void dibujar(ShapeRenderer renderer) {
        if (!estaVivo()) return;

        float mitad = TAMANO / 2f;
        renderer.setColor(COLOR_CUERPO);
        renderer.rect(posicion.x - mitad, posicion.y - mitad, TAMANO, TAMANO);

        renderer.setColor(COLOR_OJOS);
        float ojoTamano = TAMANO * 0.20f;
        float ojoOffsetX = TAMANO * 0.25f;
        float ojoOffsetY = TAMANO * 0.10f;
        renderer.rect(posicion.x - ojoOffsetX - ojoTamano / 2f,
            posicion.y + ojoOffsetY,
            ojoTamano, ojoTamano);
        renderer.rect(posicion.x + ojoOffsetX - ojoTamano / 2f,
            posicion.y + ojoOffsetY,
            ojoTamano, ojoTamano);
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
        float mitad = TAMANO / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO, TAMANO);
    }
}

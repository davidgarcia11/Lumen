package com.davidgarcia.lumen.entidades.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.ia.MaquinaEstados;
import com.davidgarcia.lumen.ia.estados.EstadoVigila;
import com.davidgarcia.lumen.utiles.SpritesEnemigos;

/** NPC que detecta a Lumen con cono de visión. Gira lentamente y persigue si lo ve. */
public class Miron extends NPC {

    private static final float VIDA = 35f;
    private static final float DANO = 35f;
    private static final float VELOCIDAD_PERSECUCION = 35f;
    private static final int TAMANO_HITBOX = 12;
    private static final float TAMANO_VISUAL = 20f;

    public static final float ALCANCE_VISION = 60f;
    public static final float SEMIANGULO_VISION = 30f;
    public static final float VELOCIDAD_GIRO = 35f;

    private static final Color COLOR_CONO = new Color(0.95f, 0.85f, 0.30f, 0.18f);

    private float anguloVisionGrados;
    private final Vector2 ultimaPosicionVistaJugador = new Vector2();
    private boolean tieneObjetivo = false;

    private final Personaje objetivo;
    private final MaquinaEstados<Miron> maquinaEstados;

    public Miron(float x, float y, float anguloInicialGrados, Personaje objetivo) {
        super(x, y, VIDA, DANO);
        this.objetivo = objetivo;
        this.anguloVisionGrados = anguloInicialGrados;
        this.maquinaEstados = new MaquinaEstados<>(this, new EstadoVigila());
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (!estaVivo()) return;
        maquinaEstados.actualizar(delta);
        SpritesEnemigos.miron.actualizar(delta);
        actualizarHitbox();
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        if (!estaVivo()) return;
        var frame = SpritesEnemigos.miron.getFrameActual();
        batch.draw(
            frame,
            posicion.x - TAMANO_VISUAL / 2f,
            posicion.y - TAMANO_VISUAL / 2f,
            TAMANO_VISUAL,
            TAMANO_VISUAL
        );
    }

    /** Dibuja el cono de visión. Se llama desde PantallaJuego en una pasada separada con ShapeRenderer. */
    public void dibujarConoVision(ShapeRenderer renderer) {
        if (!estaVivo()) return;
        renderer.setColor(COLOR_CONO);
        int segmentos = 12;
        for (int i = 0; i < segmentos; i++) {
            float t1 = (float) i / segmentos;
            float t2 = (float) (i + 1) / segmentos;
            float a1 = anguloVisionGrados - SEMIANGULO_VISION + t1 * (2 * SEMIANGULO_VISION);
            float a2 = anguloVisionGrados - SEMIANGULO_VISION + t2 * (2 * SEMIANGULO_VISION);
            float x1 = posicion.x + MathUtils.cosDeg(a1) * ALCANCE_VISION;
            float y1 = posicion.y + MathUtils.sinDeg(a1) * ALCANCE_VISION;
            float x2 = posicion.x + MathUtils.cosDeg(a2) * ALCANCE_VISION;
            float y2 = posicion.y + MathUtils.sinDeg(a2) * ALCANCE_VISION;
            renderer.triangle(posicion.x, posicion.y, x1, y1, x2, y2);
        }
    }

    public boolean puedeVerJugador() {
        if (objetivo == null) return false;
        Vector2 haciaJugador = new Vector2(objetivo.getPosicion()).sub(posicion);
        if (haciaJugador.len() > ALCANCE_VISION) return false;
        float anguloHaciaJugador = haciaJugador.angleDeg();
        float diferencia = Math.abs(diferenciaAngular(anguloHaciaJugador, anguloVisionGrados));
        return diferencia <= SEMIANGULO_VISION;
    }

    public void moverHacia(Vector2 destino, float delta) {
        Vector2 hacia = new Vector2(destino).sub(posicion);
        float distancia = hacia.len();
        if (distancia < 1f) return;
        anguloVisionGrados = hacia.angleDeg();
        hacia.nor().scl(VELOCIDAD_PERSECUCION * delta);
        posicion.add(hacia);
    }

    public void rotarVision(float deltaGrados) {
        anguloVisionGrados = (anguloVisionGrados + deltaGrados) % 360f;
        if (anguloVisionGrados < 0f) anguloVisionGrados += 360f;
    }

    public void registrarUltimaPosicionJugador() {
        if (objetivo != null) {
            ultimaPosicionVistaJugador.set(objetivo.getPosicion());
            tieneObjetivo = true;
        }
    }

    public Vector2 getUltimaPosicionJugador() {
        return ultimaPosicionVistaJugador;
    }

    public boolean tieneObjetivo() {
        return tieneObjetivo;
    }

    public void olvidarObjetivo() {
        tieneObjetivo = false;
    }

    public float getAnguloVisionGrados() {
        return anguloVisionGrados;
    }

    public MaquinaEstados<Miron> getMaquinaEstados() {
        return maquinaEstados;
    }

    private float diferenciaAngular(float a, float b) {
        return (a - b + 540f) % 360f - 180f;
    }

    private void actualizarHitbox() {
        float mitad = TAMANO_HITBOX / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, TAMANO_HITBOX, TAMANO_HITBOX);
    }
}

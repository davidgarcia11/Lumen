package com.davidgarcia.lumen.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Lumen, el espíritu de luz controlado por el jugador. */
public class Personaje extends Entidad {

    private static final float TIEMPO_INVULNERABILIDAD = 0.5f;
    private static final float PARPADEO_FRECUENCIA = 20f;

    private final int tamano;
    private final float velocidad;
    private final float energiaMaxima;
    private final float consumoPorSegundo;

    private float energia;
    private float tiempoInvulnerable = 0f;

    private final Vector2 direccion = new Vector2();
    private final Color colorActual = new Color();

    public Personaje(float x, float y) {
        super(x, y);
        this.tamano = ConfiguracionJuego.LUMEN_TAMANO;
        this.velocidad = ConfiguracionJuego.LUMEN_VELOCIDAD;
        this.energiaMaxima = ConfiguracionJuego.LUMEN_ENERGIA_MAXIMA;
        this.consumoPorSegundo = ConfiguracionJuego.LUMEN_CONSUMO_POR_SEGUNDO;
        this.energia = energiaMaxima;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (estaExtinguido()) return;

        consumirEnergiaPorTiempo(delta);
        actualizarTiempoInvulnerable(delta);
        leerInputDireccional();

        if (!direccion.isZero()) {
            direccion.nor().scl(velocidad * delta);
            posicion.add(direccion);
        }

        limitarDentroDelMundo();
        actualizarHitbox();
    }

    @Override
    public void dibujar(ShapeRenderer renderer) {
        if (estaParpadeando()) return;
        actualizarColorSegunEnergia();
        renderer.setColor(colorActual);
        float mitad = tamano / 2f;
        renderer.rect(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    public void recibirDano(float cantidad) {
        if (cantidad <= 0f || esInvulnerable()) return;
        energia = Math.max(0f, energia - cantidad);
        tiempoInvulnerable = TIEMPO_INVULNERABILIDAD;
    }

    public void recargarEnergia(float cantidad) {
        if (cantidad <= 0f) return;
        energia = Math.min(energiaMaxima, energia + cantidad);
    }

    public float getEnergia() { return energia; }
    public float getEnergiaMaxima() { return energiaMaxima; }
    public float getPorcentajeEnergia() { return energia / energiaMaxima; }
    public boolean estaExtinguido() { return energia <= 0f; }
    public boolean esInvulnerable() { return tiempoInvulnerable > 0f; }

    private void actualizarHitbox() {
        float mitad = tamano / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    private void actualizarTiempoInvulnerable(float delta) {
        if (tiempoInvulnerable > 0f) {
            tiempoInvulnerable = Math.max(0f, tiempoInvulnerable - delta);
        }
    }

    private boolean estaParpadeando() {
        if (!esInvulnerable()) return false;
        return ((int) (tiempoInvulnerable * PARPADEO_FRECUENCIA)) % 2 == 0;
    }

    private void consumirEnergiaPorTiempo(float delta) {
        energia = Math.max(0f, energia - consumoPorSegundo * delta);
    }

    private void leerInputDireccional() {
        direccion.setZero();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direccion.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) direccion.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) direccion.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direccion.x -= 1;
    }

    private void limitarDentroDelMundo() {
        float mitad = tamano / 2f;
        posicion.x = Math.max(mitad, Math.min(posicion.x, ConfiguracionJuego.ANCHO_MUNDO - mitad));
        posicion.y = Math.max(mitad, Math.min(posicion.y, ConfiguracionJuego.ALTO_MUNDO - mitad));
    }

    private void actualizarColorSegunEnergia() {
        float porcentaje = getPorcentajeEnergia();
        float intensidad = 0.25f + 0.75f * porcentaje;
        colorActual.set(
            ConfiguracionJuego.COLOR_LUMEN.r * intensidad,
            ConfiguracionJuego.COLOR_LUMEN.g * intensidad,
            ConfiguracionJuego.COLOR_LUMEN.b * intensidad,
            1f
        );
    }
}

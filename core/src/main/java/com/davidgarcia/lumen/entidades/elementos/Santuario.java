package com.davidgarcia.lumen.entidades.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Personaje;

/**
 * Pedestal con un cristal que desbloquea una habilidad de Lumen al ser activado.
 * Requiere que el jugador esté a rango y pulse E con suficientes esencias.
 */
public class Santuario extends ElementoInteractuable {

    /** Habilidad concreta que desbloquea este santuario. */
    public enum Habilidad { RAFAGA }

    private static final Color COLOR_PEDESTAL = new Color(0.35f, 0.35f, 0.40f, 1f);
    private static final Color COLOR_CRISTAL_APAGADO = new Color(0.35f, 0.35f, 0.45f, 1f);
    private static final Color COLOR_CRISTAL_ENCENDIDO = new Color(0.85f, 0.95f, 1f, 1f);
    private static final Color COLOR_HALO = new Color(0.6f, 0.8f, 1f, 0.45f);

    private final float tamano;
    private final Habilidad habilidad;
    private final int costeEsencias;
    private boolean activado = false;
    private float tiempoAnimacion = 0f;

    public Santuario(float x, float y, Habilidad habilidad, int costeEsencias) {
        super(x, y);
        this.tamano = ConfiguracionJuego.SANTUARIO_TAMANO;
        this.habilidad = habilidad;
        this.costeEsencias = costeEsencias;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        tiempoAnimacion += delta;
        actualizarHitbox();
    }

    private void actualizarHitbox() {
        float mitad = tamano / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    public boolean estaActivado() {
        return activado;
    }

    public int getCosteEsencias() {
        return costeEsencias;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }

    /**
     * Intenta activar el santuario gastando las esencias del personaje.
     * @return true si se ha activado en esta llamada; false si ya estaba activo o faltan esencias.
     */
    public boolean intentarActivar(Personaje personaje) {
        if (activado) return false;
        if (!personaje.gastarEsencias(costeEsencias)) return false;
        if (habilidad == Habilidad.RAFAGA) {
            personaje.desbloquearRafaga();
        }
        activado = true;
        return true;
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas.
    }

    public void dibujarForma(ShapeRenderer shapes) {
        float mitad = tamano / 2f;
        // Pedestal.
        shapes.setColor(COLOR_PEDESTAL);
        shapes.rect(posicion.x - mitad, posicion.y - mitad, tamano, tamano * 0.5f);
        // Cristal central.
        float pulso = 0.9f + 0.1f * (float) Math.sin(tiempoAnimacion * 3f);
        float radio = mitad * 0.45f * pulso;
        float yCristal = posicion.y + mitad * 0.15f;
        if (activado) {
            shapes.setColor(COLOR_HALO);
            shapes.circle(posicion.x, yCristal, radio * 2.2f);
            shapes.setColor(COLOR_CRISTAL_ENCENDIDO);
            shapes.circle(posicion.x, yCristal, radio);
        } else {
            shapes.setColor(COLOR_CRISTAL_APAGADO);
            shapes.circle(posicion.x, yCristal, radio);
        }
    }
}

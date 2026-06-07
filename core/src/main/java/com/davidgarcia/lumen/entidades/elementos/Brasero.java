package com.davidgarcia.lumen.entidades.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Brasero que se enciende al ser tocado por Lumen. Tutorial de "tu luz enciende lo que tocas". */
public class Brasero extends ElementoInteractuable {

    private static final Color COLOR_PIEDRA = new Color(0.30f, 0.28f, 0.30f, 1f);
    private static final Color COLOR_LLAMA = new Color(1f, 0.75f, 0.30f, 1f);
    private static final Color COLOR_HALO = new Color(1f, 0.65f, 0.20f, 0.35f);

    private final float tamano;
    private boolean encendido = false;
    private float tiempoLlama = 0f;

    public Brasero(float x, float y) {
        super(x, y);
        this.tamano = ConfiguracionJuego.BRASERO_TAMANO;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        if (encendido) tiempoLlama += delta;
        actualizarHitbox();
    }

    private void actualizarHitbox() {
        float mitad = tamano / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    public boolean estaEncendido() {
        return encendido;
    }

    /** Enciende el brasero. Se llama desde la PantallaJuego al detectar contacto con Lumen. */
    public void encender() {
        encendido = true;
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas; ver dibujarForma().
    }

    public void dibujarForma(ShapeRenderer shapes) {
        float mitad = tamano / 2f;
        // Base de piedra.
        shapes.setColor(COLOR_PIEDRA);
        shapes.rect(posicion.x - mitad, posicion.y - mitad, tamano, tamano * 0.55f);
        if (!encendido) return;
        // Llama pulsante.
        float pulso = 0.85f + 0.15f * (float) Math.sin(tiempoLlama * 8f);
        float radioLlama = mitad * 0.7f * pulso;
        shapes.setColor(COLOR_HALO);
        shapes.circle(posicion.x, posicion.y + mitad * 0.15f, radioLlama * 1.8f);
        shapes.setColor(COLOR_LLAMA);
        shapes.circle(posicion.x, posicion.y + mitad * 0.15f, radioLlama);
    }
}

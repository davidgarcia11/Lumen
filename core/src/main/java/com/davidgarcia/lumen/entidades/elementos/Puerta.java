package com.davidgarcia.lumen.entidades.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.niveles.CondicionApertura;

import java.util.List;

/**
 * Puerta de salida de una sala. Permanece bloqueada hasta que su condición
 * de apertura (definida al construirla) se cumple, momento en el que el
 * jugador puede cruzarla para avanzar a la siguiente sala.
 */
public class Puerta extends ElementoInteractuable {

    private static final Color COLOR_BLOQUEADA = new Color(0.25f, 0.25f, 0.30f, 1f);
    private static final Color COLOR_ABIERTA_BASE = new Color(0.60f, 0.85f, 0.50f, 1f);
    private static final Color COLOR_ABIERTA_HALO = new Color(0.45f, 0.95f, 0.60f, 0.45f);

    private final float ancho;
    private final float alto;
    private final CondicionApertura condicion;
    private boolean abierta = false;
    private float tiempoAnimacion = 0f;

    public Puerta(float x, float y, CondicionApertura condicion) {
        super(x, y);
        this.ancho = ConfiguracionJuego.PUERTA_ANCHO;
        this.alto = ConfiguracionJuego.PUERTA_ALTO;
        this.condicion = condicion;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        tiempoAnimacion += delta;
        actualizarHitbox();
    }

    /** Reevalúa la condición con el estado actual de la sala. */
    public void reevaluarEstado(List<Entidad> entidades, Personaje personaje) {
        if (abierta) return;
        if (condicion.estaCumplida(entidades, personaje)) {
            abierta = true;
        }
    }

    public boolean estaAbierta() {
        return abierta;
    }

    private void actualizarHitbox() {
        hitbox.set(posicion.x - ancho / 2f, posicion.y - alto / 2f, ancho, alto);
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas.
    }

    public void dibujarForma(ShapeRenderer shapes) {
        float x = posicion.x - ancho / 2f;
        float y = posicion.y - alto / 2f;
        if (!abierta) {
            shapes.setColor(COLOR_BLOQUEADA);
            shapes.rect(x, y, ancho, alto);
            return;
        }
        // Halo verdoso pulsante al estar abierta.
        float pulso = 0.85f + 0.15f * (float) Math.sin(tiempoAnimacion * 4f);
        shapes.setColor(COLOR_ABIERTA_HALO);
        shapes.rect(x - 2f, y - 2f, ancho + 4f, alto + 4f);
        shapes.setColor(COLOR_ABIERTA_BASE.r * pulso, COLOR_ABIERTA_BASE.g * pulso, COLOR_ABIERTA_BASE.b * pulso, 1f);
        shapes.rect(x, y, ancho, alto);
    }
}

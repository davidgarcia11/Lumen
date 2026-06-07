package com.davidgarcia.lumen.entidades.recolectables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Personaje;

/** Cristal etéreo que se gasta en santuarios para desbloquear habilidades. */
public class Esencia extends Recolectable {

    private static final Color COLOR_NUCLEO = new Color(0.85f, 0.95f, 1f, 1f);
    private static final Color COLOR_HALO = new Color(0.6f, 0.8f, 1f, 0.4f);

    public Esencia(float x, float y) {
        super(x, y);
    }

    @Override
    protected void aplicarEfecto(Personaje personaje) {
        personaje.sumarEsencia();
    }

    @Override
    protected int puntosOtorgados() {
        return ConfiguracionJuego.PUNTOS_ESENCIA;
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas; ver dibujarForma().
    }

    public void dibujarForma(ShapeRenderer shapes) {
        if (recogido) return;
        // Pulsación sutil con el tiempo de flotación.
        float pulso = 0.85f + 0.15f * (float) Math.sin(tiempoFlotacion * 4f);
        float radio = (tamano / 2f) * pulso;

        shapes.setColor(COLOR_HALO);
        shapes.circle(posicion.x, posicion.y, radio * 1.6f);
        shapes.setColor(COLOR_NUCLEO);
        shapes.circle(posicion.x, posicion.y, radio * 0.6f);
    }
}

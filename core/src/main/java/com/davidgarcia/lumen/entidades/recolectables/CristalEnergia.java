package com.davidgarcia.lumen.entidades.recolectables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Personaje;

/** Cristal que restaura una cantidad fija de energía al ser recogido. */
public class CristalEnergia extends Recolectable {

    private static final Color COLOR_NUCLEO = new Color(0.7f, 1f, 0.9f, 1f);
    private static final Color COLOR_HALO = new Color(0.3f, 0.8f, 0.6f, 0.5f);

    public CristalEnergia(float x, float y) {
        super(x, y);
    }

    @Override
    protected void aplicarEfecto(Personaje personaje) {
        personaje.recibirEnergia(ConfiguracionJuego.CRISTAL_ENERGIA_RESTAURADA);
    }

    @Override
    protected int puntosOtorgados() {
        return ConfiguracionJuego.PUNTOS_CRISTAL;
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas; ver dibujarForma().
    }

    public void dibujarForma(ShapeRenderer shapes) {
        if (recogido) return;
        float pulso = 0.9f + 0.1f * (float) Math.sin(tiempoFlotacion * 5f);
        float radio = (tamano / 2f) * pulso;

        shapes.setColor(COLOR_HALO);
        shapes.circle(posicion.x, posicion.y, radio * 1.5f);
        shapes.setColor(COLOR_NUCLEO);
        shapes.circle(posicion.x, posicion.y, radio);
    }
}

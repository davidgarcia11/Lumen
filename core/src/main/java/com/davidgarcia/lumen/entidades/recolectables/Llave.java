package com.davidgarcia.lumen.entidades.recolectables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Personaje;

/** Llave física que abre la puerta de su sala. Solo puede llevarse una a la vez. */
public class Llave extends Recolectable {

    private static final Color COLOR = new Color(1f, 0.85f, 0.3f, 1f);
    private static final Color COLOR_HALO = new Color(1f, 0.7f, 0.2f, 0.4f);

    public Llave(float x, float y) {
        super(x, y);
    }

    @Override
    protected void aplicarEfecto(Personaje personaje) {
        personaje.recogerLlave();
    }

    @Override
    protected int puntosOtorgados() {
        return ConfiguracionJuego.PUNTOS_LLAVE;
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // Se dibuja en la pasada de formas.
    }

    public void dibujarForma(ShapeRenderer shapes) {
        if (recogido) return;
        float oscilacion = (float) Math.sin(tiempoFlotacion * 3f) * 1.5f;
        float y = posicion.y + oscilacion;
        float anillo = tamano * 0.30f;
        float vastago = tamano * 0.30f;

        shapes.setColor(COLOR_HALO);
        shapes.circle(posicion.x, y, tamano * 0.8f);

        shapes.setColor(COLOR);
        // Anillo de la llave.
        shapes.circle(posicion.x - vastago * 0.5f, y, anillo);
        // Vástago.
        shapes.rect(posicion.x - vastago * 0.5f, y - 1f, vastago + 4f, 2f);
        // Diente.
        shapes.rect(posicion.x + vastago * 0.6f, y - 3f, 2f, 3f);
    }
}

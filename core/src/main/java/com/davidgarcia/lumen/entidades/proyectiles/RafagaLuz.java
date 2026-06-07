package com.davidgarcia.lumen.entidades.proyectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Proyectil de luz que Lumen dispara para dañar a las sombras. */
public class RafagaLuz extends Proyectil {

    private final float dano;
    private final float tamano;

    public RafagaLuz(float x, float y, Vector2 direccion) {
        super(x, y, direccion,
            ConfiguracionJuego.RAFAGA_VELOCIDAD,
            ConfiguracionJuego.RAFAGA_ALCANCE);
        this.dano = ConfiguracionJuego.RAFAGA_DANO;
        this.tamano = ConfiguracionJuego.RAFAGA_TAMANO;
        actualizarHitbox();
    }

    public float getDano() {
        return dano;
    }

    @Override
    protected void actualizarHitbox() {
        float mitad = tamano / 2f;
        hitbox.set(posicion.x - mitad, posicion.y - mitad, tamano, tamano);
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        // El render se hace fuera del batch, con ShapeRenderer en la pasada de formas.
    }

    /** Llamar entre shapeRenderer.begin(Filled) y end() en la pasada de formas. */
    public void dibujarForma(ShapeRenderer shapes) {
        if (!activo) return;
        // Halo exterior tenue.
        shapes.setColor(new Color(1f, 0.95f, 0.6f, 0.35f));
        shapes.circle(posicion.x, posicion.y, tamano);
        // Núcleo brillante.
        shapes.setColor(ConfiguracionJuego.COLOR_LUMEN);
        shapes.circle(posicion.x, posicion.y, tamano / 2f);
    }
}

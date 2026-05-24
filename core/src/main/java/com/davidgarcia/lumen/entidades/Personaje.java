package com.davidgarcia.lumen.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Lumen, el espíritu de luz controlado por el jugador. */
public class Personaje extends Entidad {

    private final int tamano;
    private final float velocidad;
    private final Vector2 direccion = new Vector2();

    public Personaje(float x, float y) {
        super(x, y);
        this.tamano = ConfiguracionJuego.LUMEN_TAMANO;
        this.velocidad = ConfiguracionJuego.LUMEN_VELOCIDAD;
    }

    @Override
    public void actualizar(float delta) {
        leerInputDireccional();

        if (!direccion.isZero()) {
            direccion.nor().scl(velocidad * delta);
            posicion.add(direccion);
        }

        limitarDentroDelMundo();
    }

    @Override
    public void dibujar(ShapeRenderer renderer) {
        float mitad = tamano / 2f;
        renderer.setColor(ConfiguracionJuego.COLOR_LUMEN);
        renderer.rect(
            posicion.x - mitad,
            posicion.y - mitad,
            tamano,
            tamano
        );
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
}

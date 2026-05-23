package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Pantalla principal de juego: maneja al personaje y el bucle de simulación. */
public class PantallaJuego extends ScreenAdapter {

    private final Main juego;

    private OrthographicCamera camara;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private final Vector2 posicionLumen = new Vector2();
    private final Vector2 direccion = new Vector2();

    public PantallaJuego(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(ConfiguracionJuego.ANCHO_MUNDO, ConfiguracionJuego.ALTO_MUNDO, camara);
        viewport.apply(true);

        shapeRenderer = new ShapeRenderer();

        posicionLumen.set(
            ConfiguracionJuego.ANCHO_MUNDO / 2f,
            ConfiguracionJuego.ALTO_MUNDO / 2f
        );
    }

    @Override
    public void render(float delta) {
        actualizar(delta);
        dibujar();
    }

    private void actualizar(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(new PantallaMenu(juego));
            return;
        }

        direccion.setZero();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direccion.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) direccion.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) direccion.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direccion.x -= 1;

        if (!direccion.isZero()) {
            direccion.nor().scl(ConfiguracionJuego.LUMEN_VELOCIDAD * delta);
            posicionLumen.add(direccion);
        }

        limitarDentroDelMundo();
    }

    private void limitarDentroDelMundo() {
        float mitad = ConfiguracionJuego.LUMEN_TAMANO / 2f;
        posicionLumen.x = Math.max(mitad, Math.min(posicionLumen.x, ConfiguracionJuego.ANCHO_MUNDO - mitad));
        posicionLumen.y = Math.max(mitad, Math.min(posicionLumen.y, ConfiguracionJuego.ALTO_MUNDO - mitad));
    }

    private void dibujar() {
        ScreenUtils.clear(ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.r * 0.15f,
            ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.g * 0.15f,
            ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.b * 0.15f, 1f);

        camara.update();
        shapeRenderer.setProjectionMatrix(camara.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ConfiguracionJuego.COLOR_LUMEN);
        float mitad = ConfiguracionJuego.LUMEN_TAMANO / 2f;
        shapeRenderer.rect(
            posicionLumen.x - mitad,
            posicionLumen.y - mitad,
            ConfiguracionJuego.LUMEN_TAMANO,
            ConfiguracionJuego.LUMEN_TAMANO
        );
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}

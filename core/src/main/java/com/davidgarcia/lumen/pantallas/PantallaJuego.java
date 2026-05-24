package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.entidades.npc.Acechante;

import java.util.ArrayList;
import java.util.List;

/** Pantalla principal de juego: orquesta el bucle de simulación de las entidades. */
public class PantallaJuego extends ScreenAdapter {

    private final Main juego;

    private OrthographicCamera camara;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private Personaje personaje;
    private final List<Entidad> entidades = new ArrayList<>();

    public PantallaJuego(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(ConfiguracionJuego.ANCHO_MUNDO, ConfiguracionJuego.ALTO_MUNDO, camara);
        viewport.apply(true);

        shapeRenderer = new ShapeRenderer();

        personaje = new Personaje(
            ConfiguracionJuego.ANCHO_MUNDO / 2f,
            ConfiguracionJuego.ALTO_MUNDO / 2f
        );
        entidades.add(personaje);

        entidades.add(new Acechante(
            ConfiguracionJuego.ANCHO_MUNDO * 0.20f,
            ConfiguracionJuego.ALTO_MUNDO * 0.30f,
            ConfiguracionJuego.ANCHO_MUNDO * 0.80f,
            ConfiguracionJuego.ALTO_MUNDO * 0.30f
        ));
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

        if (Gdx.input.isKeyPressed(Input.Keys.J)) personaje.recibirDano(60f * delta);
        if (Gdx.input.isKeyPressed(Input.Keys.K)) personaje.recargarEnergia(60f * delta);

        for (Entidad entidad : entidades) {
            entidad.actualizar(delta);
        }

        if (personaje.estaExtinguido()) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    private void dibujar() {
        ScreenUtils.clear(
            ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.r * 0.15f,
            ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.g * 0.15f,
            ConfiguracionJuego.COLOR_ACENTO_NIVEL_1.b * 0.15f,
            1f
        );

        camara.update();
        shapeRenderer.setProjectionMatrix(camara.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entidad entidad : entidades) {
            entidad.dibujar(shapeRenderer);
        }
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

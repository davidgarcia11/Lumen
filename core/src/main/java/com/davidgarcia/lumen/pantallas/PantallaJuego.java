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
import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.entidades.npc.NPC;
import com.davidgarcia.lumen.ui.HUD;
import com.davidgarcia.lumen.ui.MenuPausa;

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
    private HUD hud;

    private MenuPausa menuPausa;
    private boolean pausado = false;

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
            ConfiguracionJuego.ALTO_MUNDO * 0.25f,
            ConfiguracionJuego.ANCHO_MUNDO * 0.80f,
            ConfiguracionJuego.ALTO_MUNDO * 0.25f
        ));

        entidades.add(new Miron(
            ConfiguracionJuego.ANCHO_MUNDO * 0.85f,
            ConfiguracionJuego.ALTO_MUNDO * 0.75f,
            225f,
            personaje
        ));

        entidades.add(new Devorador(
            ConfiguracionJuego.ANCHO_MUNDO * 0.10f,
            ConfiguracionJuego.ALTO_MUNDO * 0.80f,
            personaje
        ));

        hud = new HUD(personaje);

        menuPausa = new MenuPausa(new MenuPausa.Acciones() {
            @Override public void onReanudar() {
                reanudar();
            }
            @Override public void onVolverAlMenu() {
                juego.setScreen(new PantallaMenu(juego));
            }
            @Override public void onSalirDelJuego() {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        if (pausado) {
            dibujarMundo();
            menuPausa.dibujar(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                reanudar();
            }
        } else {
            actualizar(delta);
            dibujarMundo();
            hud.dibujar();
        }
    }

    private void actualizar(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pausar();
            return;
        }

        for (Entidad entidad : entidades) {
            entidad.actualizar(delta);
        }

        detectarColisionesPersonajeNPC();

        if (personaje.estaExtinguido()) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    private void pausar() {
        pausado = true;
        menuPausa.mostrar();
    }

    private void reanudar() {
        pausado = false;
        Gdx.input.setInputProcessor(null);
    }

    private void detectarColisionesPersonajeNPC() {
        if (personaje.esInvulnerable()) return;

        for (Entidad entidad : entidades) {
            if (!(entidad instanceof NPC)) continue;
            NPC npc = (NPC) entidad;
            if (!npc.estaVivo()) continue;
            if (personaje.getHitbox().overlaps(npc.getHitbox())) {
                personaje.recibirDano(npc.getDanoAlJugador());
                return;
            }
        }
    }

    private void dibujarMundo() {
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
        if (hud != null) hud.resize(width, height);
        if (menuPausa != null) menuPausa.resize(width, height);
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (hud != null) hud.dispose();
        if (menuPausa != null) menuPausa.dispose();
    }
}

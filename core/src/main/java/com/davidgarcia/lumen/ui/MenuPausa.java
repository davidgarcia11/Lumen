package com.davidgarcia.lumen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.config.GestorPreferencias;

/** Menú de pausa que se muestra superpuesto sobre la PantallaJuego. */
public class MenuPausa {

    public interface Acciones {
        void onReanudar();
        void onVolverAlMenu();
        void onSalirDelJuego();
    }

    private static final Color COLOR_FONDO = new Color(0f, 0f, 0f, 0.65f);

    private final Viewport viewport;
    private final OrthographicCamera camara;
    private final Stage stage;
    private final Skin skin;
    private final ShapeRenderer shapes;

    private final TextButton botonSonido;

    public MenuPausa(Acciones acciones) {
        this.camara = new OrthographicCamera();
        this.viewport = new ScreenViewport(camara);
        this.stage = new Stage(viewport);
        this.skin = SkinFactory.crearSkinBasica();
        this.shapes = new ShapeRenderer();

        Table tabla = new Table();
        tabla.setFillParent(true);
        stage.addActor(tabla);

        Label titulo = new Label("PAUSA", skin, "titulo");

        TextButton botonReanudar = new TextButton("Reanudar", skin);
        anadirSonidoUI(botonReanudar);
        botonReanudar.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                acciones.onReanudar();
            }
        });

        botonSonido = new TextButton(textoBotonSonido(), skin);
        anadirSonidoUI(botonSonido);
        botonSonido.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                boolean nuevo = !GestorPreferencias.isSonidoActivado();
                GestorPreferencias.setSonidoActivado(nuevo);
                GestorAudio.refrescarVolumenes();
                botonSonido.setText(textoBotonSonido());
            }
        });

        TextButton botonMenu = new TextButton("Menú principal", skin);
        anadirSonidoUI(botonMenu);
        botonMenu.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                acciones.onVolverAlMenu();
            }
        });

        TextButton botonSalir = new TextButton("Salir del juego", skin);
        anadirSonidoUI(botonSalir);
        botonSalir.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                acciones.onSalirDelJuego();
            }
        });

        tabla.add(titulo).padBottom(40).row();
        tabla.add(botonReanudar).width(300).padBottom(15).row();
        tabla.add(botonSonido).width(300).padBottom(15).row();
        tabla.add(botonMenu).width(300).padBottom(15).row();
        tabla.add(botonSalir).width(300).row();
    }

    public void mostrar() {
        botonSonido.setText(textoBotonSonido());
        Gdx.input.setInputProcessor(stage);
    }

    public void dibujar(float delta) {
        dibujarFondoOscurecido();
        stage.act(delta);
        stage.draw();
    }

    private void dibujarFondoOscurecido() {
        Gdx.gl.glEnable(com.badlogic.gdx.graphics.GL20.GL_BLEND);
        shapes.setProjectionMatrix(camara.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(COLOR_FONDO);
        shapes.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapes.end();
        Gdx.gl.glDisable(com.badlogic.gdx.graphics.GL20.GL_BLEND);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapes.dispose();
    }

    private void anadirSonidoUI(TextButton boton) {
        boton.addListener(new InputListener() {
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) GestorAudio.reproducirEfecto(GestorAudio.Efecto.HOVER);
            }
        });
    }

    private String textoBotonSonido() {
        return GestorPreferencias.isSonidoActivado() ? "Sonido: ON" : "Sonido: OFF";
    }
}

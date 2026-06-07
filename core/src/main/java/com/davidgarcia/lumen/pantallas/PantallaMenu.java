package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.datos.EstadoPartida;
import com.davidgarcia.lumen.datos.GestorGuardado;
import com.davidgarcia.lumen.ui.SkinFactory;

/** Pantalla del menú principal. */
public class PantallaMenu extends ScreenAdapter {

    private final Main juego;
    private Stage stage;
    private Skin skin;

    public PantallaMenu(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(ConfiguracionJuego.ANCHO_VENTANA, ConfiguracionJuego.ALTO_VENTANA));
        Gdx.input.setInputProcessor(stage);
        skin = SkinFactory.crearSkinBasica();

        GestorAudio.cambiarMusica(GestorAudio.PistaMusica.MENU);

        Table tabla = new Table();
        tabla.setFillParent(true);
        stage.addActor(tabla);

        boolean hayPartidaGuardada = GestorGuardado.existePartidaGuardada();

        Label titulo = new Label("LUMEN", skin, "titulo");
        Label subtitulo = new Label("Capítulo I - La Ascensión", skin);
        TextButton botonContinuar = hayPartidaGuardada ? new TextButton("Continuar", skin) : null;
        TextButton botonNuevaPartida = new TextButton("Nueva partida", skin);
        TextButton botonRecords = new TextButton("Récords", skin);
        TextButton botonInstrucciones = new TextButton("Instrucciones", skin);
        TextButton botonConfiguracion = new TextButton("Configuración", skin);
        TextButton botonSalir = new TextButton("Salir", skin);

        if (botonContinuar != null) anadirSonidoUI(botonContinuar);
        anadirSonidoUI(botonNuevaPartida);
        anadirSonidoUI(botonRecords);
        anadirSonidoUI(botonInstrucciones);
        anadirSonidoUI(botonConfiguracion);
        anadirSonidoUI(botonSalir);

        if (botonContinuar != null) {
            botonContinuar.addListener(new ChangeListener() {
                @Override public void changed(ChangeEvent event, Actor actor) {
                    EstadoPartida estado = GestorGuardado.cargar();
                    if (estado != null) {
                        juego.setScreen(new PantallaJuego(juego, estado));
                    } else {
                        juego.setScreen(new PantallaJuego(juego));
                    }
                }
            });
        }

        botonNuevaPartida.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                GestorGuardado.borrar();
                juego.setScreen(new PantallaJuego(juego));
            }
        });
        botonRecords.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(new PantallaRecords(juego));
            }
        });
        botonInstrucciones.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(new PantallaInstrucciones(juego));
            }
        });
        botonConfiguracion.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(new PantallaConfiguracion(juego));
            }
        });
        botonSalir.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        tabla.add(titulo).padBottom(20).row();
        tabla.add(subtitulo).padBottom(60).row();
        if (botonContinuar != null) tabla.add(botonContinuar).width(300).padBottom(15).row();
        tabla.add(botonNuevaPartida).width(300).padBottom(15).row();
        tabla.add(botonRecords).width(300).padBottom(15).row();
        tabla.add(botonInstrucciones).width(300).padBottom(15).row();
        tabla.add(botonConfiguracion).width(300).padBottom(15).row();
        tabla.add(botonSalir).width(300).row();
    }

    static void anadirSonidoUI(TextButton boton) {
        boton.addListener(new InputListener() {
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) GestorAudio.reproducirEfecto(GestorAudio.Efecto.HOVER);
            }
        });
        boton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                GestorAudio.reproducirEfecto(GestorAudio.Efecto.CLICK);
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(ConfiguracionJuego.COLOR_FONDO_MENU);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}

package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

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

        skin = crearSkinBasica();

        Table tabla = new Table();
        tabla.setFillParent(true);
        stage.addActor(tabla);

        Label titulo = new Label("LUMEN", skin, "titulo");
        Label subtitulo = new Label("Capítulo I - La Ascensión", skin);
        TextButton botonNuevaPartida = new TextButton("Nueva partida", skin);
        TextButton botonRecords = new TextButton("Récords", skin);
        TextButton botonInstrucciones = new TextButton("Instrucciones", skin);
        TextButton botonConfiguracion = new TextButton("Configuración", skin);
        TextButton botonSalir = new TextButton("Salir", skin);

        botonNuevaPartida.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                juego.setScreen(new PantallaJuego(juego));
            }
        });
        botonRecords.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Gdx.app.log("PantallaMenu", "Récords (pendiente de implementar)");
            }
        });
        botonInstrucciones.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Gdx.app.log("PantallaMenu", "Instrucciones (pendiente de implementar)");
            }
        });
        botonConfiguracion.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Gdx.app.log("PantallaMenu", "Configuración (pendiente de implementar)");
            }
        });
        botonSalir.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Gdx.app.exit();
            }
        });

        tabla.add(titulo).padBottom(20).row();
        tabla.add(subtitulo).padBottom(60).row();
        tabla.add(botonNuevaPartida).width(300).padBottom(15).row();
        tabla.add(botonRecords).width(300).padBottom(15).row();
        tabla.add(botonInstrucciones).width(300).padBottom(15).row();
        tabla.add(botonConfiguracion).width(300).padBottom(15).row();
        tabla.add(botonSalir).width(300).row();
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

    /** Construye una skin minimalista con la fuente por defecto de libGDX. */
    private Skin crearSkinBasica() {
        Skin s = new Skin();
        BitmapFont fuente = new BitmapFont();
        BitmapFont fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(3f);
        s.add("default-font", fuente);
        s.add("titulo-font", fuenteTitulo);

        Label.LabelStyle estiloLabel = new Label.LabelStyle(fuente, com.badlogic.gdx.graphics.Color.WHITE);
        s.add("default", estiloLabel);

        Label.LabelStyle estiloTitulo = new Label.LabelStyle(fuenteTitulo, ConfiguracionJuego.COLOR_LUMEN);
        s.add("titulo", estiloTitulo);

        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuente;
        estiloBoton.fontColor = com.badlogic.gdx.graphics.Color.LIGHT_GRAY;
        estiloBoton.overFontColor = ConfiguracionJuego.COLOR_LUMEN;
        s.add("default", estiloBoton);

        return s;
    }
}

package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.davidgarcia.lumen.ui.SkinFactory;

/** Pantalla de récords de campeones. Por ahora muestra estado vacío. */
public class PantallaRecords extends ScreenAdapter {

    private final Main juego;
    private Stage stage;
    private Skin skin;

    public PantallaRecords(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(ConfiguracionJuego.ANCHO_VENTANA, ConfiguracionJuego.ALTO_VENTANA));
        Gdx.input.setInputProcessor(stage);
        skin = SkinFactory.crearSkinBasica();

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.top().padTop(80);
        stage.addActor(tabla);

        Label titulo = new Label("RÉCORDS DE CAMPEONES", skin, "titulo");

        String cabecera = String.format("%-6s %-12s %-10s %-10s %-12s %-12s",
            "POS", "NOMBRE", "PUNTOS", "TIEMPO", "PERSONAJE", "FECHA");
        Label labelCabecera = new Label(cabecera, skin);

        Label separador = new Label(
            "----------------------------------------------------------------------",
            skin);

        Label mensajeVacio = new Label(
            "\nNadie ha completado el Capítulo I todavía.\n¿Serás el primero?\n",
            skin, "subtitulo");
        mensajeVacio.setAlignment(com.badlogic.gdx.utils.Align.center);

        TextButton botonVolver = new TextButton("Volver", skin);
        botonVolver.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        tabla.add(titulo).padBottom(50).row();
        tabla.add(labelCabecera).padBottom(8).row();
        tabla.add(separador).padBottom(8).row();
        tabla.add(mensajeVacio).padBottom(50).row();
        tabla.add(botonVolver).width(200);
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

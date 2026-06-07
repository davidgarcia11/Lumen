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
import com.davidgarcia.lumen.datos.GestorRecords;
import com.davidgarcia.lumen.datos.Puntuacion;
import com.davidgarcia.lumen.ui.SkinFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Pantalla de récords de campeones: muestra el Top 10 persistido. */
public class PantallaRecords extends ScreenAdapter {

    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

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
        tabla.add(titulo).padBottom(50).row();

        List<Puntuacion> records = GestorRecords.cargarTodas();
        if (records.isEmpty()) {
            anadirMensajeVacio(tabla);
        } else {
            anadirTablaRecords(tabla, records);
        }

        TextButton botonVolver = new TextButton("Volver", skin);
        botonVolver.addListener(new InputListener() {
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) GestorAudio.reproducirEfecto(GestorAudio.Efecto.HOVER);
            }
        });
        botonVolver.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                GestorAudio.reproducirEfecto(GestorAudio.Efecto.CLICK);
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        tabla.add(botonVolver).width(200).padTop(40);
    }

    private void anadirMensajeVacio(Table tabla) {
        Label mensaje = new Label(
            "\nNadie ha completado el Capítulo I todavía.\n¿Serás el primero?\n",
            skin, "subtitulo");
        mensaje.setAlignment(com.badlogic.gdx.utils.Align.center);
        tabla.add(mensaje).padBottom(20).row();
    }

    private void anadirTablaRecords(Table tabla, List<Puntuacion> records) {
        String cabecera = String.format("%-5s %-12s %-8s %-8s %-12s",
            "POS", "NOMBRE", "PUNTOS", "TIEMPO", "FECHA");
        tabla.add(new Label(cabecera, skin)).padBottom(8).row();
        tabla.add(new Label(repetir('-', cabecera.length()), skin)).padBottom(8).row();

        for (int i = 0; i < records.size(); i++) {
            Puntuacion p = records.get(i);
            String fila = String.format("%-5s %-12s %-8d %-8s %-12s",
                (i + 1),
                limitar(p.getNombre(), 12),
                p.getPuntos(),
                formatearTiempo(p.getSegundos()),
                FORMATO_FECHA.format(new Date(p.getFechaMillis())));
            tabla.add(new Label(fila, skin)).padBottom(4).row();
        }
    }

    private static String formatearTiempo(int segundos) {
        return String.format("%02d:%02d", segundos / 60, segundos % 60);
    }

    private static String limitar(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max) : s;
    }

    private static String repetir(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(c);
        return sb.toString();
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

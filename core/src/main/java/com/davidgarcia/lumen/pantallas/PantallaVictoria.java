package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.datos.GestorRecords;
import com.davidgarcia.lumen.datos.Puntuacion;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.ui.SkinFactory;

/**
 * Pantalla final del juego: muestra el texto narrativo del Capítulo I con
 * efecto typewriter. Al completarlo, si la puntuación entra en el Top 10
 * pide el nombre del jugador; si no, espera a que se pulse una tecla para
 * volver al menú principal.
 */
public class PantallaVictoria extends ScreenAdapter {

    private static final String[] LINEAS = {
        "Lumen emerge al amanecer.",
        "El aire frío de la jungla recibe su luz",
        "por primera vez en siglos.",
        "",
        "A sus espaldas, el templo guarda silencio.",
        "Pero en lo más profundo, algo todavía vigila.",
        "Y algo ha empezado a despertar.",
        "",
        "Continuará..."
    };

    /** Caracteres por segundo del efecto typewriter. */
    private static final float VELOCIDAD_TYPEWRITER = 28f;

    private final Main juego;
    private final int puntosFinales;
    private final int segundosFinales;
    private final boolean entraEnTop;

    private Viewport viewport;
    private OrthographicCamera camara;
    private SpriteBatch batch;
    private BitmapFont fuenteTitulo;
    private BitmapFont fuenteTexto;
    private BitmapFont fuentePie;
    private GlyphLayout layout;

    private Stage stageEntradaNombre;
    private Skin skin;
    private TextField campoNombre;
    private boolean recordGuardado = false;

    private float tiempo = 0f;
    private int caracteresTotales;
    private boolean completado = false;

    public PantallaVictoria(Main juego, Personaje personaje) {
        this.juego = juego;
        this.puntosFinales = personaje.getPuntos();
        this.segundosFinales = personaje.getTiempoJugadoSegundos();
        this.entraEnTop = GestorRecords.entraEnTop10(puntosFinales);
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new ExtendViewport(
            ConfiguracionJuego.ANCHO_VENTANA,
            ConfiguracionJuego.ALTO_VENTANA,
            camara
        );
        batch = new SpriteBatch();
        fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(3f);
        fuenteTexto = new BitmapFont();
        fuenteTexto.getData().setScale(2f);
        fuentePie = new BitmapFont();
        fuentePie.getData().setScale(1.5f);
        layout = new GlyphLayout();

        caracteresTotales = 0;
        for (String linea : LINEAS) caracteresTotales += linea.length();

        GestorAudio.cambiarMusica(GestorAudio.PistaMusica.MENU);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.04f, 0.04f, 0.08f, 1f);

        tiempo += delta;
        if (!completado) {
            int visibles = (int) (tiempo * VELOCIDAD_TYPEWRITER);
            if (visibles >= caracteresTotales) {
                completado = true;
                if (entraEnTop) prepararEntradaNombre();
            }
        }

        camara.update();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        dibujarContenido();
        batch.end();

        if (stageEntradaNombre != null) {
            stageEntradaNombre.act(delta);
            stageEntradaNombre.draw();
        }

        if (completado && !entraEnTop) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                volverAlMenu();
            }
        }
    }

    private void prepararEntradaNombre() {
        skin = SkinFactory.crearSkinBasica();
        stageEntradaNombre = new Stage(viewport);
        Gdx.input.setInputProcessor(stageEntradaNombre);

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.bottom().padBottom(80);
        stageEntradaNombre.addActor(tabla);

        Label etiqueta = new Label("¡Has entrado en el Top 10! Introduce tu nombre:", skin);

        campoNombre = new TextField(GestorRecords.getUltimoNombreUsado(), skin);
        campoNombre.setMaxLength(GestorRecords.MAX_CARACTERES_NOMBRE);
        campoNombre.setAlignment(com.badlogic.gdx.utils.Align.center);
        stageEntradaNombre.setKeyboardFocus(campoNombre);

        TextButton botonGuardar = new TextButton("Guardar récord", skin);
        PantallaMenu.anadirSonidoUI(botonGuardar);
        botonGuardar.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                guardarRecord();
            }
        });

        tabla.add(etiqueta).padBottom(15).row();
        tabla.add(campoNombre).width(260).padBottom(15).row();
        tabla.add(botonGuardar).width(260);
    }

    private void guardarRecord() {
        if (recordGuardado) return;
        String nombre = campoNombre.getText().trim();
        if (nombre.isEmpty()) nombre = "Anónimo";
        Puntuacion puntuacion = new Puntuacion(
            nombre,
            puntosFinales,
            segundosFinales,
            System.currentTimeMillis()
        );
        GestorRecords.registrar(puntuacion);
        GestorRecords.setUltimoNombreUsado(nombre);
        recordGuardado = true;
        volverAlMenu();
    }

    private void dibujarContenido() {
        float anchoMundo = viewport.getWorldWidth();
        float altoMundo = viewport.getWorldHeight();

        fuenteTitulo.setColor(ConfiguracionJuego.COLOR_ACENTO_NIVEL_2);
        String titulo = "LA ASCENSIÓN";
        layout.setText(fuenteTitulo, titulo);
        fuenteTitulo.draw(batch, titulo, (anchoMundo - layout.width) / 2f, altoMundo - 80);

        int restantes = (int) (tiempo * VELOCIDAD_TYPEWRITER);
        if (completado) restantes = caracteresTotales;

        fuenteTexto.setColor(Color.WHITE);
        float y = altoMundo - 220;
        float altoLinea = fuenteTexto.getLineHeight() + 6f;

        for (String linea : LINEAS) {
            int n = Math.min(restantes, linea.length());
            if (n <= 0 && linea.length() > 0) break;
            String visible = linea.substring(0, Math.max(0, n));
            if (!visible.isEmpty()) {
                layout.setText(fuenteTexto, visible);
                fuenteTexto.draw(batch, visible, (anchoMundo - layout.width) / 2f, y);
            }
            y -= altoLinea;
            restantes -= linea.length();
            if (restantes <= 0) break;
        }

        if (!completado) return;

        fuentePie.setColor(ConfiguracionJuego.COLOR_ACENTO_NIVEL_2);
        String puntos = "Puntuación final: " + puntosFinales + "   Tiempo: " + formatearTiempo(segundosFinales);
        layout.setText(fuentePie, puntos);
        fuentePie.draw(batch, puntos, (anchoMundo - layout.width) / 2f, 220);

        if (!entraEnTop) {
            float parpadeo = (float) Math.sin(tiempo * 4f);
            fuentePie.setColor(1f, 1f, 1f, 0.55f + 0.45f * (parpadeo * 0.5f + 0.5f));
            String pie = "Pulsa cualquier tecla para volver al menú";
            layout.setText(fuentePie, pie);
            fuentePie.draw(batch, pie, (anchoMundo - layout.width) / 2f, 80);
        }
    }

    private String formatearTiempo(int segundos) {
        return String.format("%02d:%02d", segundos / 60, segundos % 60);
    }

    private void volverAlMenu() {
        juego.setScreen(new PantallaMenu(juego));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if (stageEntradaNombre != null) stageEntradaNombre.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (fuenteTitulo != null) fuenteTitulo.dispose();
        if (fuenteTexto != null) fuenteTexto.dispose();
        if (fuentePie != null) fuentePie.dispose();
        if (stageEntradaNombre != null) stageEntradaNombre.dispose();
        if (skin != null) skin.dispose();
    }
}

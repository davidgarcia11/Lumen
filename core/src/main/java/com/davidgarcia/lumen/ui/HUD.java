package com.davidgarcia.lumen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.niveles.GestorNiveles;

/** HUD del juego: barra de energía, puntos, nivel/sala actuales. */
public class HUD {

    private static final int CRISTALES = 10;
    private static final float MARGEN = 16f;
    private static final float CRISTAL_ANCHO = 16f;
    private static final float CRISTAL_ALTO = 10f;
    private static final float CRISTAL_SEPARACION = 2f;
    private static final float ANCHO_CONTADOR_PUNTOS = 160f;

    private static final Color COLOR_BORDE = new Color(1f, 1f, 1f, 0.6f);
    private static final Color COLOR_VACIO = new Color(0.15f, 0.15f, 0.20f, 1f);

    private final Viewport viewport;
    private final OrthographicCamera camara;
    private final SpriteBatch batch;
    private final ShapeRenderer shapes;
    private final BitmapFont fuente;

    private final Personaje personaje;
    private final GestorNiveles gestorNiveles;

    public HUD(Personaje personaje, GestorNiveles gestorNiveles) {
        this.personaje = personaje;
        this.gestorNiveles = gestorNiveles;
        this.camara = new OrthographicCamera();
        this.viewport = new ScreenViewport(camara);
        this.batch = new SpriteBatch();
        this.shapes = new ShapeRenderer();
        this.fuente = new BitmapFont();
        this.fuente.getData().setScale(1.5f);
    }

    public void dibujar() {
        camara.update();
        shapes.setProjectionMatrix(camara.combined);
        batch.setProjectionMatrix(camara.combined);

        dibujarBarraEnergia();
        dibujarTextos();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        shapes.dispose();
        fuente.dispose();
    }

    private void dibujarBarraEnergia() {
        float xInicio = MARGEN;
        float yInicio = viewport.getWorldHeight() - MARGEN - CRISTAL_ALTO;
        int cristalesLlenos = (int) Math.ceil(personaje.getPorcentajeEnergia() * CRISTALES);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < CRISTALES; i++) {
            float x = xInicio + i * (CRISTAL_ANCHO + CRISTAL_SEPARACION);
            if (i < cristalesLlenos) {
                shapes.setColor(ConfiguracionJuego.COLOR_LUMEN);
            } else {
                shapes.setColor(COLOR_VACIO);
            }
            shapes.rect(x, yInicio, CRISTAL_ANCHO, CRISTAL_ALTO);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(COLOR_BORDE);
        for (int i = 0; i < CRISTALES; i++) {
            float x = xInicio + i * (CRISTAL_ANCHO + CRISTAL_SEPARACION);
            shapes.rect(x, yInicio, CRISTAL_ANCHO, CRISTAL_ALTO);
        }
        shapes.end();
    }

    private void dibujarTextos() {
        batch.begin();
        float yBarra = viewport.getWorldHeight() - MARGEN - CRISTAL_ALTO;

        fuente.setColor(Color.WHITE);
        fuente.draw(batch, etiquetaActual(), MARGEN, yBarra - 8);

        String textoPuntos = "Puntos: " + personaje.getPuntos();
        float xPuntos = viewport.getWorldWidth() - MARGEN - ANCHO_CONTADOR_PUNTOS;
        float yPuntos = viewport.getWorldHeight() - MARGEN;
        fuente.draw(batch, textoPuntos, xPuntos, yPuntos);

        batch.end();
    }

    private String etiquetaActual() {
        return gestorNiveles.getSalaActual().getIdentificador()
            + "  " + gestorNiveles.getNivelActual().getNombre();
    }
}

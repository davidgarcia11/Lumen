package com.davidgarcia.lumen.utiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** Sprites animados de los enemigos, generados proceduralmente. */
public final class SpritesEnemigos {

    private static final int TAMANO_FRAME = 20;
    private static final int CENTRO = TAMANO_FRAME / 2;
    private static final int FRAMES = 4;

    private static final Color COLOR_ACECHANTE = new Color(0.12f, 0.10f, 0.18f, 1f);
    private static final Color COLOR_OJOS_ACECHANTE = new Color(0.95f, 0.20f, 0.25f, 1f);

    private static final Color COLOR_MIRON = new Color(0.18f, 0.14f, 0.22f, 1f);
    private static final Color COLOR_OJOS_MIRON = new Color(0.95f, 0.85f, 0.30f, 1f);

    private static final Color COLOR_DEVORADOR = new Color(0.08f, 0.08f, 0.12f, 1f);
    private static final Color COLOR_OJOS_DEVORADOR = new Color(0.95f, 0.40f, 0.95f, 1f);

    public static Animacion acechante;
    public static Animacion miron;
    public static Animacion devorador;

    private SpritesEnemigos() {}

    static void generar() {
        acechante = generarSiluetaConOjos(COLOR_ACECHANTE, COLOR_OJOS_ACECHANTE, 6.5f, 7.5f, 0.16f);
        miron = generarSiluetaConOjos(COLOR_MIRON, COLOR_OJOS_MIRON, 7.0f, 8.0f, 0.18f);
        devorador = generarSiluetaConOjos(COLOR_DEVORADOR, COLOR_OJOS_DEVORADOR, 7.5f, 8.5f, 0.20f);
    }

    /**
     * Genera una animación de 4 frames con una silueta circular y dos ojos brillantes.
     * El cuerpo "respira": pequeña pulsación de radio entre frames.
     */
    private static Animacion generarSiluetaConOjos(Color colorCuerpo, Color colorOjos,
                                                   float radioBase, float radioPico,
                                                   float duracionPorFrame) {
        TextureRegion[] frames = new TextureRegion[FRAMES];
        float[] radios = { radioBase, (radioBase + radioPico) / 2f, radioPico, (radioBase + radioPico) / 2f };

        for (int i = 0; i < FRAMES; i++) {
            Pixmap pixmap = GeneradorPixmap.crearPixmap(TAMANO_FRAME, TAMANO_FRAME);

            // Cuerpo (silueta circular).
            GeneradorPixmap.rellenarCirculo(pixmap, CENTRO, CENTRO, radios[i], colorCuerpo);

            // Dos ojos brillantes (siempre en el mismo sitio relativo).
            int desplazamientoX = 2;
            int desplazamientoY = 1;
            GeneradorPixmap.rellenarCirculo(pixmap, CENTRO - desplazamientoX, CENTRO + desplazamientoY, 1.2f, colorOjos);
            GeneradorPixmap.rellenarCirculo(pixmap, CENTRO + desplazamientoX, CENTRO + desplazamientoY, 1.2f, colorOjos);

            frames[i] = GeneradorPixmap.aTextureRegion(pixmap);
            GestorAssets.registrar(frames[i]);
        }

        return new Animacion(duracionPorFrame, frames);
    }
}

package com.davidgarcia.lumen.utiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Sprites animados de Lumen, generados proceduralmente. */
public final class SpritesLumen {

    private static final int TAMANO_FRAME = 24;
    private static final int CENTRO = TAMANO_FRAME / 2;
    private static final int FRAMES = 4;

    public static Animacion idle;
    public static Animacion andarAbajo;
    public static Animacion andarArriba;
    public static Animacion andarDerecha;
    public static Animacion andarIzquierda;

    private SpritesLumen() {}

    static void generar() {
        idle = generarIdle();
        andarAbajo = generarAndar(0f, -1f);
        andarArriba = generarAndar(0f, 1f);
        andarDerecha = generarAndar(1f, 0f);
        andarIzquierda = generarAndar(-1f, 0f);
    }

    /** Idle: esfera con halo pulsante (4 frames con radios distintos). */
    private static Animacion generarIdle() {
        TextureRegion[] frames = new TextureRegion[FRAMES];
        float[] radiosHalo = { 7.0f, 8.5f, 9.5f, 8.5f };
        float[] radiosNucleo = { 3.5f, 4.0f, 4.5f, 4.0f };

        for (int i = 0; i < FRAMES; i++) {
            Pixmap pixmap = GeneradorPixmap.crearPixmap(TAMANO_FRAME, TAMANO_FRAME);
            GeneradorPixmap.rellenarCirculoConHalo(
                pixmap, CENTRO, CENTRO,
                radiosNucleo[i], radiosHalo[i],
                ConfiguracionJuego.COLOR_LUMEN,
                conAlfa(ConfiguracionJuego.COLOR_LUMEN, 0.45f)
            );
            frames[i] = GeneradorPixmap.aTextureRegion(pixmap);
            GestorAssets.registrar(frames[i]);
        }

        return new Animacion(0.18f, frames);
    }

    /** Animación de andar: esfera con halo + estela opuesta al movimiento. */
    private static Animacion generarAndar(float dirX, float dirY) {
        TextureRegion[] frames = new TextureRegion[FRAMES];
        float[] radiosHalo = { 8.0f, 9.0f, 8.5f, 7.5f };
        float[] radiosNucleo = { 4.0f, 4.5f, 4.0f, 3.5f };
        float[] longitudesEstela = { 4f, 6f, 5f, 3f };

        for (int i = 0; i < FRAMES; i++) {
            Pixmap pixmap = GeneradorPixmap.crearPixmap(TAMANO_FRAME, TAMANO_FRAME);

            // Estela en dirección opuesta al movimiento (3 puntos decrecientes).
            float longEstela = longitudesEstela[i];
            int puntos = 3;
            for (int p = 1; p <= puntos; p++) {
                float t = p / (float) puntos;
                int ex = (int) (CENTRO - dirX * longEstela * t);
                int ey = (int) (CENTRO - dirY * longEstela * t);
                float radio = 2.0f * (1f - t * 0.5f);
                float alfa = 0.5f * (1f - t);
                GeneradorPixmap.rellenarCirculo(pixmap, ex, ey, radio,
                    conAlfa(ConfiguracionJuego.COLOR_LUMEN, alfa));
            }

            // Núcleo + halo en el centro.
            GeneradorPixmap.rellenarCirculoConHalo(
                pixmap, CENTRO, CENTRO,
                radiosNucleo[i], radiosHalo[i],
                ConfiguracionJuego.COLOR_LUMEN,
                conAlfa(ConfiguracionJuego.COLOR_LUMEN, 0.45f)
            );

            frames[i] = GeneradorPixmap.aTextureRegion(pixmap);
            GestorAssets.registrar(frames[i]);
        }

        return new Animacion(0.12f, frames);
    }

    private static Color conAlfa(Color base, float alfa) {
        return new Color(base.r, base.g, base.b, alfa);
    }
}

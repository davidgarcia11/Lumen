package com.davidgarcia.lumen.utiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** Utilidades para generar TextureRegion programáticamente con Pixmap. */
public final class GeneradorPixmap {

    private GeneradorPixmap() {}

    /** Crea un Pixmap del tamaño indicado, transparente al inicio. */
    public static Pixmap crearPixmap(int ancho, int alto) {
        Pixmap pixmap = new Pixmap(ancho, alto, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        return pixmap;
    }

    /** Dibuja un círculo relleno con anti-aliasing simple por muestreo. */
    public static void rellenarCirculo(Pixmap pixmap, int cx, int cy, float radio, Color color) {
        int rEntero = (int) Math.ceil(radio) + 1;
        for (int x = cx - rEntero; x <= cx + rEntero; x++) {
            for (int y = cy - rEntero; y <= cy + rEntero; y++) {
                if (x < 0 || y < 0 || x >= pixmap.getWidth() || y >= pixmap.getHeight()) continue;
                float dx = x - cx;
                float dy = y - cy;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist <= radio) {
                    pixmap.drawPixel(x, y, Color.rgba8888(color));
                }
            }
        }
    }

    /** Dibuja un círculo con halo: núcleo opaco + anillo translúcido degradado. */
    public static void rellenarCirculoConHalo(Pixmap pixmap, int cx, int cy,
                                              float radioNucleo, float radioHalo,
                                              Color colorNucleo, Color colorHalo) {
        int rEntero = (int) Math.ceil(radioHalo) + 1;
        for (int x = cx - rEntero; x <= cx + rEntero; x++) {
            for (int y = cy - rEntero; y <= cy + rEntero; y++) {
                if (x < 0 || y < 0 || x >= pixmap.getWidth() || y >= pixmap.getHeight()) continue;
                float dx = x - cx;
                float dy = y - cy;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist <= radioNucleo) {
                    pixmap.drawPixel(x, y, Color.rgba8888(colorNucleo));
                } else if (dist <= radioHalo) {
                    float t = (dist - radioNucleo) / (radioHalo - radioNucleo);
                    float alfa = colorHalo.a * (1f - t);
                    int rgba = Color.rgba8888(colorHalo.r, colorHalo.g, colorHalo.b, alfa);
                    pixmap.drawPixel(x, y, rgba);
                }
            }
        }
    }

    /** Rellena un rectángulo entero. */
    public static void rellenarRectangulo(Pixmap pixmap, int x, int y, int ancho, int alto, Color color) {
        pixmap.setColor(color);
        pixmap.fillRectangle(x, y, ancho, alto);
    }

    /** Convierte el Pixmap a TextureRegion y desecha el Pixmap. La textura resultante NO se libera aquí. */
    public static TextureRegion aTextureRegion(Pixmap pixmap) {
        Texture textura = new Texture(pixmap);
        textura.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        pixmap.dispose();
        return new TextureRegion(textura);
    }
}

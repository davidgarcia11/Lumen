package com.davidgarcia.lumen.utiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor centralizado de assets visuales generados proceduralmente.
 * Genera todos los sprites al arrancar el juego y los mantiene en memoria.
 */
public final class GestorAssets {

    private static boolean inicializado = false;
    private static final List<Texture> texturas = new ArrayList<>();

    private GestorAssets() {}

    public static void inicializar() {
        if (inicializado) return;

        SpritesLumen.generar();
        SpritesEnemigos.generar();

        inicializado = true;
    }

    /** Registra una textura para que sea liberada en dispose(). */
    static void registrar(TextureRegion region) {
        if (region != null && region.getTexture() != null) {
            texturas.add(region.getTexture());
        }
    }

    public static void dispose() {
        for (Texture t : texturas) t.dispose();
        texturas.clear();
        inicializado = false;
    }
}

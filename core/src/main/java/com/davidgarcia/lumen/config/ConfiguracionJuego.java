package com.davidgarcia.lumen.config;

import com.badlogic.gdx.graphics.Color;

/** Constantes globales del juego: dimensiones, paleta, identidad. */
public final class ConfiguracionJuego {

    // ─── Identidad ──────────────────────────────────────────────────────────
    public static final String TITULO = "Lumen — Capítulo I: La Ascensión";
    public static final String VERSION = "1.0.0";

    // ─── Resolución y ventana ───────────────────────────────────────────────
    /** Ancho lógico del mundo de juego en píxeles (un cuarto de Full HD). */
    public static final int ANCHO_MUNDO = 480;
    /** Alto lógico del mundo de juego en píxeles. */
    public static final int ALTO_MUNDO = 270;

    /** Ancho de la ventana del sistema operativo al arrancar. */
    public static final int ANCHO_VENTANA = ANCHO_MUNDO * 4;  // 1920
    /** Alto de la ventana del sistema operativo al arrancar. */
    public static final int ALTO_VENTANA = ALTO_MUNDO * 4;    // 1080

    // ─── Paleta de colores ──────────────────────────────────────────────────
    public static final Color COLOR_FONDO_MENU = new Color(0.04f, 0.04f, 0.08f, 1f);
    public static final Color COLOR_ACENTO_NIVEL_1 = new Color(0.45f, 0.55f, 0.95f, 1f);
    public static final Color COLOR_ACENTO_NIVEL_2 = new Color(0.95f, 0.75f, 0.35f, 1f);
    public static final Color COLOR_LUMEN = new Color(0.95f, 0.97f, 1.0f, 1f);

    private ConfiguracionJuego() {
        // Clase de constantes; no se instancia.
    }
}

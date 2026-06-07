package com.davidgarcia.lumen.config;

import com.badlogic.gdx.graphics.Color;

/** Constantes globales del juego: dimensiones, paleta, identidad. */
public final class ConfiguracionJuego {

    // ─── Identidad ──────────────────────────────────────────────────────────
    public static final String TITULO = "Lumen — Capítulo I: La Ascensión";
    public static final String VERSION = "1.0.0";

    // ─── Resolución y ventana ───────────────────────────────────────────────
    public static final int ANCHO_MUNDO = 480;
    public static final int ALTO_MUNDO = 270;
    public static final int ANCHO_VENTANA = ANCHO_MUNDO * 4;
    public static final int ALTO_VENTANA = ALTO_MUNDO * 4;

    // ─── Paleta de colores ──────────────────────────────────────────────────
    public static final Color COLOR_FONDO_MENU = new Color(0.04f, 0.04f, 0.08f, 1f);
    public static final Color COLOR_ACENTO_NIVEL_1 = new Color(0.45f, 0.55f, 0.95f, 1f);
    public static final Color COLOR_ACENTO_NIVEL_2 = new Color(0.95f, 0.75f, 0.35f, 1f);
    public static final Color COLOR_LUMEN = new Color(0.95f, 0.97f, 1.0f, 1f);

    // ─── Lumen ──────────────────────────────────────────────────────────────
    /** Tamaño de la hitbox cuadrada de Lumen, en unidades del mundo. */
    public static final int LUMEN_TAMANO_HITBOX = 12;
    /** Tamaño visual del sprite de Lumen, en unidades del mundo. */
    public static final float LUMEN_TAMANO_VISUAL = 24f;
    public static final float LUMEN_VELOCIDAD = 80f;
    /** Energía máxima al inicio de un nivel o tras cargar partida. */
    public static final float LUMEN_ENERGIA_MAXIMA = 100f;
    /** Cuánta energía pierde por segundo solo por estar en el mundo (consumo pasivo). */
    public static final float LUMEN_CONSUMO_POR_SEGUNDO = 1.5f;

    // ─── Invulnerabilidad ───────────────────────────────────────────────────
    /** Duración (en segundos) de los i-frames tras recibir daño. */
    public static final float INVULNERABILIDAD_DURACION = 0.5f;

    // ─── Ráfaga de luz ──────────────────────────────────────────────────────
    /** Velocidad de avance del proyectil de ráfaga. */
    public static final float RAFAGA_VELOCIDAD = 180f;
    /** Distancia máxima que recorre la ráfaga antes de desaparecer. */
    public static final float RAFAGA_ALCANCE = 90f;
    /** Daño que inflige una ráfaga al impactar el cuerpo de un NPC. */
    public static final float RAFAGA_DANO = 12f;
    /** Tiempo (segundos) entre disparos consecutivos. */
    public static final float RAFAGA_COOLDOWN = 0.35f;
    /** Energía consumida al disparar una ráfaga. */
    public static final float RAFAGA_COSTE_ENERGIA = 4f;
    /** Tamaño visual del proyectil. */
    public static final float RAFAGA_TAMANO = 8f;

    // ─── Puntuación ─────────────────────────────────────────────────────────
    public static final int PUNTOS_ACECHANTE = 20;
    public static final int PUNTOS_MIRON = 40;
    public static final int PUNTOS_DEVORADOR = 80;
    public static final int PUNTOS_ESENCIA = 25;
    public static final int PUNTOS_CRISTAL = 10;
    public static final int PUNTOS_LLAVE = 15;
    public static final int PUNTOS_COMPLETAR_SALA = 200;

    // ─── Recolectables ──────────────────────────────────────────────────────
    /** Energía que restaura un cristal al recogerlo. */
    public static final float CRISTAL_ENERGIA_RESTAURADA = 25f;
    /** Tamaño visual y de hitbox de los recolectables, en unidades del mundo. */
    public static final float RECOLECTABLE_TAMANO = 10f;

    // ─── Elementos interactuables ───────────────────────────────────────────
    /** Tamaño de la hitbox física de un brasero. */
    public static final float BRASERO_TAMANO = 14f;
    /** Tamaño de la hitbox del santuario. */
    public static final float SANTUARIO_TAMANO = 22f;
    /** Distancia máxima a la que Lumen puede activar un santuario con tecla E. */
    public static final float RANGO_INTERACCION = 22f;
    /** Coste en esencias para desbloquear la ráfaga en el santuario. */
    public static final int SANTUARIO_RAFAGA_COSTE = 2;

    // ─── Puerta de salida ───────────────────────────────────────────────────
    /** Anchura visual y de hitbox de la puerta. */
    public static final float PUERTA_ANCHO = 18f;
    /** Altura visual y de hitbox de la puerta. */
    public static final float PUERTA_ALTO = 28f;

    private ConfiguracionJuego() {
        // Clase de constantes; no se instancia.
    }
}

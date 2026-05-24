package com.davidgarcia.lumen.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Acceso centralizado a las preferencias del jugador, persistidas entre sesiones. */
public final class GestorPreferencias {

    public enum Dificultad { FACIL, NORMAL, DIFICIL }

    private static final String NOMBRE_PREFS = "lumen.prefs";

    private static final String CLAVE_VOLUMEN_MUSICA = "volumen_musica";
    private static final String CLAVE_VOLUMEN_EFECTOS = "volumen_efectos";
    private static final String CLAVE_DIFICULTAD = "dificultad";

    private static final int VOLUMEN_MUSICA_POR_DEFECTO = 70;
    private static final int VOLUMEN_EFECTOS_POR_DEFECTO = 80;
    private static final Dificultad DIFICULTAD_POR_DEFECTO = Dificultad.NORMAL;

    private static Preferences prefs;

    private GestorPreferencias() {}

    private static Preferences prefs() {
        if (prefs == null) prefs = Gdx.app.getPreferences(NOMBRE_PREFS);
        return prefs;
    }

    public static int getVolumenMusica() {
        return prefs().getInteger(CLAVE_VOLUMEN_MUSICA, VOLUMEN_MUSICA_POR_DEFECTO);
    }

    public static void setVolumenMusica(int valor) {
        prefs().putInteger(CLAVE_VOLUMEN_MUSICA, clamp(valor));
        prefs().flush();
    }

    public static int getVolumenEfectos() {
        return prefs().getInteger(CLAVE_VOLUMEN_EFECTOS, VOLUMEN_EFECTOS_POR_DEFECTO);
    }

    public static void setVolumenEfectos(int valor) {
        prefs().putInteger(CLAVE_VOLUMEN_EFECTOS, clamp(valor));
        prefs().flush();
    }

    public static Dificultad getDificultad() {
        String valor = prefs().getString(CLAVE_DIFICULTAD, DIFICULTAD_POR_DEFECTO.name());
        try {
            return Dificultad.valueOf(valor);
        } catch (IllegalArgumentException e) {
            return DIFICULTAD_POR_DEFECTO;
        }
    }

    public static void setDificultad(Dificultad dificultad) {
        prefs().putString(CLAVE_DIFICULTAD, dificultad.name());
        prefs().flush();
    }

    private static int clamp(int valor) {
        return Math.max(0, Math.min(100, valor));
    }
}

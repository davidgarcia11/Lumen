package com.davidgarcia.lumen.datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

/**
 * Persiste y recupera la partida en curso mediante un único snapshot
 * serializado a JSON dentro del fichero de preferencias del juego.
 */
public final class GestorGuardado {

    private static final String NOMBRE_PREFS = "lumen.prefs";
    private static final String CLAVE_PARTIDA = "partida_guardada";

    private static Preferences prefs;
    private static final Json json = new Json();

    private GestorGuardado() {}

    private static Preferences prefs() {
        if (prefs == null) prefs = Gdx.app.getPreferences(NOMBRE_PREFS);
        return prefs;
    }

    public static boolean existePartidaGuardada() {
        return !prefs().getString(CLAVE_PARTIDA, "").isEmpty();
    }

    public static void guardar(EstadoPartida estado) {
        if (estado == null) return;
        String serializado = json.toJson(estado, EstadoPartida.class);
        prefs().putString(CLAVE_PARTIDA, serializado);
        prefs().flush();
    }

    /** Devuelve la partida persistida o null si no hay o el fichero está corrupto. */
    public static EstadoPartida cargar() {
        String serializado = prefs().getString(CLAVE_PARTIDA, "");
        if (serializado.isEmpty()) return null;
        try {
            return json.fromJson(EstadoPartida.class, serializado);
        } catch (Exception e) {
            Gdx.app.error("GestorGuardado", "Partida guardada corrupta, se descarta", e);
            borrar();
            return null;
        }
    }

    public static void borrar() {
        prefs().remove(CLAVE_PARTIDA);
        prefs().flush();
    }
}

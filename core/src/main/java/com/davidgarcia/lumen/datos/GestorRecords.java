package com.davidgarcia.lumen.datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestor del Top 10 de puntuaciones. Persiste el ranking entre sesiones
 * en el mismo fichero de preferencias que el resto de opciones.
 *
 * <p>La lista se mantiene ordenada de mayor a menor puntuación; solo las
 * 10 mejores entradas se conservan.
 */
public final class GestorRecords {

    public static final int TAMANO_MAX = 10;
    public static final int MAX_CARACTERES_NOMBRE = 10;

    private static final String NOMBRE_PREFS = "lumen.prefs";
    private static final String CLAVE_RECORDS = "records_top10";
    private static final String CLAVE_ULTIMO_NOMBRE = "records_ultimo_nombre";

    private static Preferences prefs;
    private static final Json json = new Json();

    private GestorRecords() {}

    private static Preferences prefs() {
        if (prefs == null) prefs = Gdx.app.getPreferences(NOMBRE_PREFS);
        return prefs;
    }

    /** Devuelve la lista persistida ordenada de mejor a peor. Lista vacía si no hay. */
    public static List<Puntuacion> cargarTodas() {
        String serializado = prefs().getString(CLAVE_RECORDS, "");
        if (serializado.isEmpty()) return new ArrayList<>();
        try {
            @SuppressWarnings("unchecked")
            ArrayList<Puntuacion> lista = json.fromJson(ArrayList.class, Puntuacion.class, serializado);
            if (lista == null) return new ArrayList<>();
            Collections.sort(lista);
            return lista;
        } catch (Exception e) {
            Gdx.app.error("GestorRecords", "Fichero de records corrupto, se descarta", e);
            return new ArrayList<>();
        }
    }

    /** Indica si una puntuación entraría en el Top 10. */
    public static boolean entraEnTop10(int puntos) {
        List<Puntuacion> actuales = cargarTodas();
        if (actuales.size() < TAMANO_MAX) return true;
        return puntos > actuales.get(TAMANO_MAX - 1).getPuntos();
    }

    /**
     * Inserta la puntuación en el ranking si entra en el top 10.
     * @return true si se ha guardado, false si quedaba fuera del corte.
     */
    public static boolean registrar(Puntuacion nueva) {
        List<Puntuacion> lista = cargarTodas();
        lista.add(nueva);
        Collections.sort(lista);
        while (lista.size() > TAMANO_MAX) lista.remove(lista.size() - 1);
        if (!lista.contains(nueva)) return false;
        guardar(lista);
        return true;
    }

    private static void guardar(List<Puntuacion> lista) {
        String serializado = json.toJson(lista, ArrayList.class, Puntuacion.class);
        prefs().putString(CLAVE_RECORDS, serializado);
        prefs().flush();
    }

    public static String getUltimoNombreUsado() {
        return prefs().getString(CLAVE_ULTIMO_NOMBRE, "");
    }

    public static void setUltimoNombreUsado(String nombre) {
        if (nombre == null) return;
        String recortado = nombre.length() > MAX_CARACTERES_NOMBRE
            ? nombre.substring(0, MAX_CARACTERES_NOMBRE)
            : nombre;
        prefs().putString(CLAVE_ULTIMO_NOMBRE, recortado);
        prefs().flush();
    }

    /** Borra todas las puntuaciones. Útil para testing manual. */
    public static void borrarTodo() {
        prefs().remove(CLAVE_RECORDS);
        prefs().flush();
    }
}

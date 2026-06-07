package com.davidgarcia.lumen.datos;

/**
 * Entrada del Top 10 de récords. Inmutable.
 * Las instancias se ordenan por puntuación descendente y, a igualdad de puntos,
 * por tiempo ascendente (más rápido es mejor).
 */
public class Puntuacion implements Comparable<Puntuacion> {

    private final String nombre;
    private final int puntos;
    private final int segundos;
    /** Milisegundos desde epoch en el momento del registro. */
    private final long fechaMillis;

    /** Constructor sin argumentos requerido por el serializador JSON de libGDX. */
    public Puntuacion() {
        this("", 0, 0, 0L);
    }

    public Puntuacion(String nombre, int puntos, int segundos, long fechaMillis) {
        this.nombre = nombre == null ? "" : nombre;
        this.puntos = puntos;
        this.segundos = segundos;
        this.fechaMillis = fechaMillis;
    }

    public String getNombre() { return nombre; }
    public int getPuntos() { return puntos; }
    public int getSegundos() { return segundos; }
    public long getFechaMillis() { return fechaMillis; }

    @Override
    public int compareTo(Puntuacion otra) {
        if (this.puntos != otra.puntos) return Integer.compare(otra.puntos, this.puntos);
        return Integer.compare(this.segundos, otra.segundos);
    }
}

package com.davidgarcia.lumen.datos;

/**
 * Snapshot mínimo de una partida en curso, suficiente para reanudarla al
 * inicio de una sala. No persiste estado intermedio dentro de la sala
 * (NPCs vivos, braseros encendidos, posición exacta): al cargar, la sala
 * se construye fresca y Lumen aparece en su spawn con energía completa.
 *
 * <p>POJO mutable con constructor sin argumentos para que el serializador
 * JSON de libGDX pueda reconstruirlo.
 */
public class EstadoPartida {

    private int indiceNivel;
    private int indiceSala;
    private int puntos;
    private int esencias;
    private boolean tieneRafaga;
    private int segundosJugados;
    private long fechaGuardadoMillis;

    public EstadoPartida() {}

    public EstadoPartida(int indiceNivel, int indiceSala, int puntos, int esencias,
                         boolean tieneRafaga, int segundosJugados, long fechaGuardadoMillis) {
        this.indiceNivel = indiceNivel;
        this.indiceSala = indiceSala;
        this.puntos = puntos;
        this.esencias = esencias;
        this.tieneRafaga = tieneRafaga;
        this.segundosJugados = segundosJugados;
        this.fechaGuardadoMillis = fechaGuardadoMillis;
    }

    public int getIndiceNivel() { return indiceNivel; }
    public int getIndiceSala() { return indiceSala; }
    public int getPuntos() { return puntos; }
    public int getEsencias() { return esencias; }
    public boolean isTieneRafaga() { return tieneRafaga; }
    public int getSegundosJugados() { return segundosJugados; }
    public long getFechaGuardadoMillis() { return fechaGuardadoMillis; }

    // Setters requeridos por el serializador.
    public void setIndiceNivel(int v) { this.indiceNivel = v; }
    public void setIndiceSala(int v) { this.indiceSala = v; }
    public void setPuntos(int v) { this.puntos = v; }
    public void setEsencias(int v) { this.esencias = v; }
    public void setTieneRafaga(boolean v) { this.tieneRafaga = v; }
    public void setSegundosJugados(int v) { this.segundosJugados = v; }
    public void setFechaGuardadoMillis(long v) { this.fechaGuardadoMillis = v; }
}

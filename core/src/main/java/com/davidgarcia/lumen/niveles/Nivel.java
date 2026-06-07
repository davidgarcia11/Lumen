package com.davidgarcia.lumen.niveles;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

/** Agrupación de salas que comparten temática visual. */
public class Nivel {

    private final int numero;
    private final String nombre;
    private final Color colorAcento;
    private final List<Sala> salas;

    public Nivel(int numero, String nombre, Color colorAcento, List<Sala> salas) {
        this.numero = numero;
        this.nombre = nombre;
        this.colorAcento = colorAcento;
        this.salas = salas;
    }

    public int getNumero() { return numero; }
    public String getNombre() { return nombre; }
    public Color getColorAcento() { return colorAcento; }
    public List<Sala> getSalas() { return salas; }
    public int getCantidadSalas() { return salas.size(); }
    public Sala getSala(int indice) { return salas.get(indice); }
}

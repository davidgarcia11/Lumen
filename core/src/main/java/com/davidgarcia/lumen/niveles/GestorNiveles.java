package com.davidgarcia.lumen.niveles;

import com.badlogic.gdx.graphics.Color;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Gestor del progreso del jugador a través de los niveles y salas.
 * Mantiene el estado de qué sala está activa y proporciona la lista de
 * entidades correspondiente. Las transiciones se piden explícitamente.
 */
public class GestorNiveles {

    private final List<Nivel> niveles;
    private int indiceNivel;
    private int indiceSala;

    private final Personaje personaje;
    private List<Entidad> entidadesActuales = new ArrayList<>();

    public GestorNiveles(Personaje personaje) {
        this.personaje = personaje;
        this.niveles = Arrays.asList(
            new Nivel(1, "Catacumbas",
                ConfiguracionJuego.COLOR_ACENTO_NIVEL_1,
                Arrays.asList(Salas.n1s1(), Salas.n1s2(), Salas.n1s3())),
            new Nivel(2, "Templo",
                ConfiguracionJuego.COLOR_ACENTO_NIVEL_2,
                Arrays.asList(Salas.n2s1(), Salas.n2s2(), Salas.n2s3()))
        );
        this.indiceNivel = 0;
        this.indiceSala = 0;
    }

    /** Carga la sala inicial (Nivel 1, Sala 1) y devuelve sus entidades. */
    public List<Entidad> cargarSalaInicial() {
        indiceNivel = 0;
        indiceSala = 0;
        return cargarSalaActual();
    }

    /** Vuelve a la primera sala del nivel actual y devuelve sus entidades frescas. */
    public List<Entidad> reiniciarNivelActual() {
        indiceSala = 0;
        return cargarSalaActual();
    }

    /**
     * Carga directamente una sala arbitraria (usado al reanudar una partida
     * guardada). Los índices se acotan al rango válido por seguridad.
     */
    public List<Entidad> cargarSalaPorIndices(int nivel, int sala) {
        this.indiceNivel = Math.max(0, Math.min(nivel, niveles.size() - 1));
        int totalSalas = niveles.get(indiceNivel).getCantidadSalas();
        this.indiceSala = Math.max(0, Math.min(sala, totalSalas - 1));
        return cargarSalaActual();
    }

    public int getIndiceNivel() { return indiceNivel; }
    public int getIndiceSala() { return indiceSala; }

    /** Avanza a la siguiente sala. Si era la última del nivel, salta al primer sala del siguiente nivel. */
    public List<Entidad> avanzarSala() {
        Nivel nivelActual = niveles.get(indiceNivel);
        if (indiceSala + 1 < nivelActual.getCantidadSalas()) {
            indiceSala++;
        } else if (indiceNivel + 1 < niveles.size()) {
            indiceNivel++;
            indiceSala = 0;
        } else {
            // Era la última sala del último nivel: ya está el juego completado.
            return entidadesActuales;
        }
        return cargarSalaActual();
    }

    private List<Entidad> cargarSalaActual() {
        Sala sala = niveles.get(indiceNivel).getSala(indiceSala);
        entidadesActuales = new ArrayList<>();
        entidadesActuales.add(personaje);
        entidadesActuales.addAll(sala.generarEnemigos(personaje));
        return entidadesActuales;
    }

    public boolean haCompletadoElJuego() {
        return indiceNivel == niveles.size() - 1
            && indiceSala == niveles.get(indiceNivel).getCantidadSalas() - 1
            && entidadesActuales.size() == 1; // Solo el personaje, todos los enemigos eliminados o salida tomada.
    }

    public Nivel getNivelActual() { return niveles.get(indiceNivel); }
    public Sala getSalaActual() { return niveles.get(indiceNivel).getSala(indiceSala); }
    public int getNumeroNivelActual() { return niveles.get(indiceNivel).getNumero(); }
    public int getNumeroSalaActual() { return indiceSala + 1; }
    public int getTotalSalasNivelActual() { return niveles.get(indiceNivel).getCantidadSalas(); }
    public Color getColorAcentoActual() { return niveles.get(indiceNivel).getColorAcento(); }

    public List<Entidad> getEntidadesActuales() { return entidadesActuales; }
}

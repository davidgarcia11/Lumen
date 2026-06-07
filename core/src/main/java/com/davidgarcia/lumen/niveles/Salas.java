package com.davidgarcia.lumen.niveles;

import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.entidades.elementos.Brasero;
import com.davidgarcia.lumen.entidades.elementos.Puerta;
import com.davidgarcia.lumen.entidades.elementos.Santuario;
import com.davidgarcia.lumen.entidades.npc.Acechante;
import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.entidades.recolectables.CristalEnergia;
import com.davidgarcia.lumen.entidades.recolectables.Esencia;
import com.davidgarcia.lumen.entidades.recolectables.Llave;

import java.util.ArrayList;
import java.util.List;

/** Factory de salas: define qué enemigos pueblan cada sala del juego. */
public final class Salas {

    private static final float ANCHO = ConfiguracionJuego.ANCHO_MUNDO;
    private static final float ALTO = ConfiguracionJuego.ALTO_MUNDO;
    /** Posición X estándar de la puerta de salida (junto al borde derecho). */
    private static final float PUERTA_X = ANCHO - ConfiguracionJuego.PUERTA_ANCHO / 2f;
    private static final float PUERTA_Y = ALTO * 0.5f;

    private Salas() {}

    private static Puerta puertaDeSalida(CondicionApertura condicion) {
        return new Puerta(PUERTA_X, PUERTA_Y, condicion);
    }

    // ─── Nivel 1: Catacumbas ────────────────────────────────────────────────

    /** N1-S1: introducción suave, un único Acechante patrullando + esencia tutorial. */
    public static Sala n1s1() {
        return new Sala("N1-S1", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Acechante(
                ANCHO * 0.25f, ALTO * 0.5f,
                ANCHO * 0.75f, ALTO * 0.5f
            ));
            entidades.add(new Esencia(ANCHO * 0.5f, ALTO * 0.25f));
            entidades.add(puertaDeSalida(CondicionApertura.siempreAbierta()));
            return entidades;
        });
    }

    /** N1-S2: Mirón + brasero a tocar + segunda esencia para llegar a 2 antes del santuario. */
    public static Sala n1s2() {
        return new Sala("N1-S2", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Acechante(
                ANCHO * 0.20f, ALTO * 0.30f,
                ANCHO * 0.80f, ALTO * 0.30f
            ));
            entidades.add(new Miron(
                ANCHO * 0.50f, ALTO * 0.75f,
                270f,
                personaje
            ));
            entidades.add(new Brasero(ANCHO * 0.15f, ALTO * 0.65f));
            entidades.add(new Esencia(ANCHO * 0.85f, ALTO * 0.65f));
            entidades.add(puertaDeSalida(CondicionApertura.braserosEncendidos()));
            return entidades;
        });
    }

    /** N1-S3: sala final de catacumbas con Acechante, Mirón, Devorador + cristal de energía. */
    public static Sala n1s3() {
        return new Sala("N1-S3", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Acechante(
                ANCHO * 0.15f, ALTO * 0.20f,
                ANCHO * 0.45f, ALTO * 0.20f
            ));
            entidades.add(new Miron(
                ANCHO * 0.80f, ALTO * 0.50f,
                180f,
                personaje
            ));
            entidades.add(new Devorador(
                ANCHO * 0.50f, ALTO * 0.85f,
                personaje
            ));
            entidades.add(new CristalEnergia(ANCHO * 0.25f, ALTO * 0.75f));
            entidades.add(new Santuario(
                ANCHO * 0.50f, ALTO * 0.50f,
                Santuario.Habilidad.RAFAGA,
                ConfiguracionJuego.SANTUARIO_RAFAGA_COSTE
            ));
            entidades.add(puertaDeSalida(CondicionApertura.matarTodos()));
            return entidades;
        });
    }

    // ─── Nivel 2: Templo ────────────────────────────────────────────────────

    /** N2-S1: arranque del templo, dos Mirones cruzando visiones + llave provisional. */
    public static Sala n2s1() {
        return new Sala("N2-S1", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Miron(
                ANCHO * 0.25f, ALTO * 0.50f,
                0f,
                personaje
            ));
            entidades.add(new Miron(
                ANCHO * 0.75f, ALTO * 0.50f,
                180f,
                personaje
            ));
            entidades.add(new Llave(ANCHO * 0.5f, ALTO * 0.85f));
            entidades.add(puertaDeSalida(CondicionApertura.llaveObtenida()));
            return entidades;
        });
    }

    /** N2-S2: sala del Devorador despertando, con Acechante de apoyo. */
    public static Sala n2s2() {
        return new Sala("N2-S2", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Acechante(
                ANCHO * 0.20f, ALTO * 0.85f,
                ANCHO * 0.80f, ALTO * 0.85f
            ));
            entidades.add(new Devorador(
                ANCHO * 0.50f, ALTO * 0.55f,
                personaje
            ));
            entidades.add(puertaDeSalida(CondicionApertura.matarTodos()));
            return entidades;
        });
    }

    /** N2-S3: sala final con dos Devoradores y un Mirón vigilando. */
    public static Sala n2s3() {
        return new Sala("N2-S3", personaje -> {
            List<Entidad> entidades = new ArrayList<>();
            entidades.add(new Miron(
                ANCHO * 0.50f, ALTO * 0.85f,
                270f,
                personaje
            ));
            entidades.add(new Devorador(
                ANCHO * 0.20f, ALTO * 0.60f,
                personaje
            ));
            entidades.add(new Devorador(
                ANCHO * 0.80f, ALTO * 0.60f,
                personaje
            ));
            entidades.add(puertaDeSalida(CondicionApertura.matarTodos()));
            return entidades;
        });
    }
}

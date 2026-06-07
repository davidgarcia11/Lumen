package com.davidgarcia.lumen.niveles;

import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
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

    private Salas() {}

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
            return entidades;
        });
    }

    /** N1-S2: aparece el primer Mirón con cono de visión. */
    public static Sala n1s2() {
        return new Sala("N1-S2", personaje -> {
            List<Entidad> enemigos = new ArrayList<>();
            enemigos.add(new Acechante(
                ANCHO * 0.20f, ALTO * 0.30f,
                ANCHO * 0.80f, ALTO * 0.30f
            ));
            enemigos.add(new Miron(
                ANCHO * 0.50f, ALTO * 0.75f,
                270f,
                personaje
            ));
            return enemigos;
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
            entidades.add(new Llave(ANCHO * 0.5f, ALTO * 0.15f));
            return entidades;
        });
    }

    /** N2-S2: sala del Devorador despertando, con Acechante de apoyo. */
    public static Sala n2s2() {
        return new Sala("N2-S2", personaje -> {
            List<Entidad> enemigos = new ArrayList<>();
            enemigos.add(new Acechante(
                ANCHO * 0.20f, ALTO * 0.85f,
                ANCHO * 0.80f, ALTO * 0.85f
            ));
            enemigos.add(new Devorador(
                ANCHO * 0.50f, ALTO * 0.55f,
                personaje
            ));
            return enemigos;
        });
    }

    /** N2-S3: sala final con dos Devoradores y un Mirón vigilando. */
    public static Sala n2s3() {
        return new Sala("N2-S3", personaje -> {
            List<Entidad> enemigos = new ArrayList<>();
            enemigos.add(new Miron(
                ANCHO * 0.50f, ALTO * 0.85f,
                270f,
                personaje
            ));
            enemigos.add(new Devorador(
                ANCHO * 0.20f, ALTO * 0.60f,
                personaje
            ));
            enemigos.add(new Devorador(
                ANCHO * 0.80f, ALTO * 0.60f,
                personaje
            ));
            return enemigos;
        });
    }
}

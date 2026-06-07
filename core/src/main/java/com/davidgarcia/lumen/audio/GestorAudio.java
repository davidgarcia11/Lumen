package com.davidgarcia.lumen.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.davidgarcia.lumen.config.GestorPreferencias;

import java.util.HashMap;
import java.util.Map;

/** Gestor centralizado de música y efectos del juego. */
public final class GestorAudio {

    public enum PistaMusica {
        MENU("audio/musica/menu.mp3"),
        JUEGO("audio/musica/juego.mp3");

        final String ruta;
        PistaMusica(String ruta) { this.ruta = ruta; }
    }

    public enum Efecto {
        HOVER("audio/efectos/hover.ogg"),
        CLICK("audio/efectos/click.ogg"),
        DANO("audio/efectos/dano.ogg"),
        DESPERTAR("audio/efectos/despertar.ogg"),
        RAFAGA("audio/efectos/rafaga.ogg"),
        IMPACTO("audio/efectos/impacto.ogg");

        final String ruta;
        Efecto(String ruta) { this.ruta = ruta; }
    }

    private static final Map<PistaMusica, Music> musicas = new HashMap<>();
    private static final Map<Efecto, Sound> efectos = new HashMap<>();
    private static PistaMusica pistaActual = null;
    private static boolean inicializado = false;

    private GestorAudio() {}

    /** Inicializa el gestor cargando todos los recursos. Llamar desde Main.create(). */
    public static void inicializar() {
        if (inicializado) return;

        for (PistaMusica pista : PistaMusica.values()) {
            cargarMusicaSiExiste(pista);
        }

        for (Efecto efecto : Efecto.values()) {
            cargarEfectoSiExiste(efecto);
        }

        inicializado = true;
    }

    public static void cambiarMusica(PistaMusica pista) {
        if (pistaActual == pista) return;
        detenerMusica();
        Music musica = musicas.get(pista);
        if (musica == null) return;
        musica.setLooping(true);
        musica.setVolume(volumenMusicaNormalizado());
        musica.play();
        pistaActual = pista;
    }

    public static void detenerMusica() {
        if (pistaActual != null) {
            Music actual = musicas.get(pistaActual);
            if (actual != null) actual.stop();
        }
        pistaActual = null;
    }

    public static void reproducirEfecto(Efecto efecto) {
        if (!GestorPreferencias.isSonidoActivado()) return;
        Sound sonido = efectos.get(efecto);
        if (sonido == null) return;
        sonido.play(volumenEfectosNormalizado());
    }

    /** Reaplica los volúmenes de las preferencias a la música activa. Llamar tras cambios en Configuración. */
    public static void refrescarVolumenes() {
        if (pistaActual == null) return;
        Music actual = musicas.get(pistaActual);
        if (actual != null) actual.setVolume(volumenMusicaNormalizado());
    }

    public static void dispose() {
        for (Music m : musicas.values()) m.dispose();
        for (Sound s : efectos.values()) s.dispose();
        musicas.clear();
        efectos.clear();
        pistaActual = null;
        inicializado = false;
    }

    private static void cargarMusicaSiExiste(PistaMusica pista) {
        FileHandle archivo = Gdx.files.internal(pista.ruta);
        if (archivo.exists()) {
            musicas.put(pista, Gdx.audio.newMusic(archivo));
        } else {
            Gdx.app.log("GestorAudio", "Música no encontrada: " + pista.ruta);
        }
    }

    private static void cargarEfectoSiExiste(Efecto efecto) {
        FileHandle archivo = Gdx.files.internal(efecto.ruta);
        if (archivo.exists()) {
            efectos.put(efecto, Gdx.audio.newSound(archivo));
        } else {
            Gdx.app.log("GestorAudio", "Efecto no encontrado: " + efecto.ruta);
        }
    }

    private static float volumenMusicaNormalizado() {
        if (!GestorPreferencias.isSonidoActivado()) return 0f;
        return GestorPreferencias.getVolumenMusica() / 100f;
    }

    private static float volumenEfectosNormalizado() {
        if (!GestorPreferencias.isSonidoActivado()) return 0f;
        return GestorPreferencias.getVolumenEfectos() / 100f;
    }
}

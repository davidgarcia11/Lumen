package com.davidgarcia.lumen.niveles;

import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.entidades.elementos.Brasero;
import com.davidgarcia.lumen.entidades.npc.NPC;

import java.util.List;

/**
 * Regla que decide cuándo la puerta de salida de una sala se desbloquea.
 * Se evalúa cada frame con las entidades actuales y el personaje del jugador.
 */
@FunctionalInterface
public interface CondicionApertura {

    boolean estaCumplida(List<Entidad> entidades, Personaje personaje);

    /** La puerta nunca está bloqueada. Útil para salas tutorial sin obstáculo. */
    static CondicionApertura siempreAbierta() {
        return (entidades, personaje) -> true;
    }

    /** Hay que eliminar a todos los NPCs vivos de la sala. */
    static CondicionApertura matarTodos() {
        return (entidades, personaje) -> {
            for (Entidad e : entidades) {
                if (e instanceof NPC && ((NPC) e).estaVivo()) return false;
            }
            return true;
        };
    }

    /** Todos los braseros de la sala deben estar encendidos. */
    static CondicionApertura braserosEncendidos() {
        return (entidades, personaje) -> {
            boolean habia = false;
            for (Entidad e : entidades) {
                if (e instanceof Brasero) {
                    habia = true;
                    if (!((Brasero) e).estaEncendido()) return false;
                }
            }
            return habia;
        };
    }

    /** El personaje debe llevar una llave; al cruzar la puerta se consume. */
    static CondicionApertura llaveObtenida() {
        return (entidades, personaje) -> personaje.tieneLlave();
    }
}

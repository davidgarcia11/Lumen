package com.davidgarcia.lumen.ia;

import com.davidgarcia.lumen.entidades.npc.NPC;

/** Estado de IA genérico. Cada estado define qué hacer al entrar, durante y al salir. */
public interface EstadoIA<T extends NPC> {

    /** Se invoca una vez al activarse este estado en la máquina. */
    default void entrar(T npc) {}

    /** Se invoca cada frame mientras este estado esté activo. */
    void actualizar(T npc, float delta);

    /** Se invoca una vez al desactivarse este estado. */
    default void salir(T npc) {}
}

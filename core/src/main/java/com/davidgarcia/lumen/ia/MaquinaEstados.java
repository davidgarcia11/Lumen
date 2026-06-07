package com.davidgarcia.lumen.ia;

import com.davidgarcia.lumen.entidades.npc.NPC;

/** Máquina de estados finitos para un NPC. Gestiona transiciones y delega comportamiento al estado activo. */
public class MaquinaEstados<T extends NPC> {

    private final T duenio;
    private EstadoIA<T> estadoActual;

    public MaquinaEstados(T duenio, EstadoIA<T> estadoInicial) {
        this.duenio = duenio;
        this.estadoActual = estadoInicial;
        estadoActual.entrar(duenio);
    }

    public void actualizar(float delta) {
        estadoActual.actualizar(duenio, delta);
    }

    public void cambiarA(EstadoIA<T> nuevoEstado) {
        if (nuevoEstado == estadoActual) return;
        estadoActual.salir(duenio);
        estadoActual = nuevoEstado;
        estadoActual.entrar(duenio);
    }

    public EstadoIA<T> getEstadoActual() {
        return estadoActual;
    }
}

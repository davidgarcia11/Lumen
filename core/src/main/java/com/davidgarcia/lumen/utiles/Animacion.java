package com.davidgarcia.lumen.utiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** Envoltura sobre Animation<TextureRegion> con tiempo interno y modo de reproducción. */
public class Animacion {

    private final Animation<TextureRegion> animacion;
    private float tiempo;
    private boolean pausada;

    public Animacion(float duracionPorFrame, TextureRegion... frames) {
        this.animacion = new Animation<>(duracionPorFrame, frames);
        this.animacion.setPlayMode(Animation.PlayMode.LOOP);
        this.tiempo = 0f;
        this.pausada = false;
    }

    public void actualizar(float delta) {
        if (!pausada) tiempo += delta;
    }

    public TextureRegion getFrameActual() {
        return animacion.getKeyFrame(tiempo);
    }

    public void reiniciar() {
        tiempo = 0f;
    }

    public void setPausada(boolean pausada) {
        this.pausada = pausada;
    }

    public void setModo(Animation.PlayMode modo) {
        animacion.setPlayMode(modo);
    }

    public boolean haTerminado() {
        return animacion.isAnimationFinished(tiempo);
    }
}

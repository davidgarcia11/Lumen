package com.davidgarcia.lumen;

import com.badlogic.gdx.Game;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.pantallas.PantallaMenu;

/** Punto de entrada del juego Lumen. */
public class Main extends Game {

    @Override
    public void create() {
        GestorAudio.inicializar();
        setScreen(new PantallaMenu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        GestorAudio.dispose();
    }
}

package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.ui.SkinFactory;

/** Pantalla con los controles y el objetivo del juego. */
public class PantallaInstrucciones extends ScreenAdapter {

    private final Main juego;
    private Stage stage;
    private Skin skin;

    public PantallaInstrucciones(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(ConfiguracionJuego.ANCHO_VENTANA, ConfiguracionJuego.ALTO_VENTANA));
        Gdx.input.setInputProcessor(stage);
        skin = SkinFactory.crearSkinBasica();

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.top().padTop(60);
        stage.addActor(tabla);

        Label titulo = new Label("INSTRUCCIONES", skin, "titulo");

        String texto =
            "Eres Lumen, un espíritu de luz que despierta en lo más profundo de\n" +
                "un templo perdido. Asciende sala a sala hasta la superficie antes\n" +
                "de que tu luz se extinga.\n" +
                "\n" +
                "CONTROLES\n" +
                "\n" +
                "  W A S D         Mover en 8 direcciones\n" +
                "  Espacio         Ráfaga de luz (cuando se desbloquee)\n" +
                "  Shift izq.      Dash con invulnerabilidad (cuando se desbloquee)\n" +
                "  E               Interactuar\n" +
                "  Esc             Volver al menú principal\n" +
                "\n" +
                "CONSEJOS\n" +
                "\n" +
                "  - Tu luz se consume con el tiempo. No te entretengas.\n" +
                "  - Recoge cristales para recuperar energía.\n" +
                "  - Recoge esencias para desbloquear habilidades en los santuarios.\n" +
                "  - Puedes esquivar a las sombras: el combate es opcional.";

        Label cuerpo = new Label(texto, skin);
        cuerpo.setAlignment(com.badlogic.gdx.utils.Align.left);

        TextButton botonVolver = new TextButton("Volver", skin);
        botonVolver.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        tabla.add(titulo).padBottom(40).row();
        tabla.add(cuerpo).padBottom(40).row();
        tabla.add(botonVolver).width(200);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(ConfiguracionJuego.COLOR_FONDO_MENU);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}

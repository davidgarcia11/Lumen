package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.config.GestorPreferencias;
import com.davidgarcia.lumen.ui.SkinFactory;

/** Pantalla de configuración: volúmenes y dificultad. */
public class PantallaConfiguracion extends ScreenAdapter {

    private final Main juego;
    private Stage stage;
    private Skin skin;

    private Label etiquetaMusica;
    private Label etiquetaEfectos;
    private Label etiquetaDificultad;

    public PantallaConfiguracion(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(ConfiguracionJuego.ANCHO_VENTANA, ConfiguracionJuego.ALTO_VENTANA));
        Gdx.input.setInputProcessor(stage);
        skin = SkinFactory.crearSkinBasica();
        prepararEstiloSlider();

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.top().padTop(80);
        stage.addActor(tabla);

        Label titulo = new Label("CONFIGURACIÓN", skin, "titulo");

        Slider sliderMusica = crearSliderVolumen(GestorPreferencias.getVolumenMusica());
        sliderMusica.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                int valor = (int) sliderMusica.getValue();
                GestorPreferencias.setVolumenMusica(valor);
                GestorAudio.refrescarVolumenes();
                etiquetaMusica.setText("Música:    " + valor);
            }
        });
        etiquetaMusica = new Label("Música:    " + GestorPreferencias.getVolumenMusica(), skin);

        Slider sliderEfectos = crearSliderVolumen(GestorPreferencias.getVolumenEfectos());
        sliderEfectos.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                int valor = (int) sliderEfectos.getValue();
                GestorPreferencias.setVolumenEfectos(valor);
                etiquetaEfectos.setText("Efectos:   " + valor);
            }
        });
        etiquetaEfectos = new Label("Efectos:   " + GestorPreferencias.getVolumenEfectos(), skin);

        TextButton botonDificultad = new TextButton(textoDificultad(), skin);
        anadirHover(botonDificultad);
        botonDificultad.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                GestorAudio.reproducirEfecto(GestorAudio.Efecto.CLICK);
                cambiarDificultad();
                botonDificultad.setText(textoDificultad());
                etiquetaDificultad.setText("Dificultad: " + GestorPreferencias.getDificultad().name());
            }
        });
        etiquetaDificultad = new Label("Dificultad: " + GestorPreferencias.getDificultad().name(), skin);

        TextButton botonVolver = new TextButton("Volver", skin);
        anadirHover(botonVolver);
        botonVolver.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                GestorAudio.reproducirEfecto(GestorAudio.Efecto.CLICK);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        tabla.add(titulo).colspan(2).padBottom(50).row();

        tabla.add(etiquetaMusica).width(220).padBottom(15);
        tabla.add(sliderMusica).width(300).padBottom(15).row();

        tabla.add(etiquetaEfectos).width(220).padBottom(15);
        tabla.add(sliderEfectos).width(300).padBottom(15).row();

        tabla.add(etiquetaDificultad).width(220).padBottom(40);
        tabla.add(botonDificultad).width(300).padBottom(40).row();

        tabla.add(botonVolver).colspan(2).width(200);
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

    private void prepararEstiloSlider() {
        Slider.SliderStyle estilo = new Slider.SliderStyle();
        estilo.background = skin.newDrawable("pixel-blanco", new Color(0.2f, 0.2f, 0.25f, 1f));
        estilo.background.setMinHeight(8);
        estilo.knob = skin.newDrawable("pixel-blanco", ConfiguracionJuego.COLOR_LUMEN);
        estilo.knob.setMinWidth(12);
        estilo.knob.setMinHeight(20);
        skin.add("default-horizontal", estilo);
    }

    private Slider crearSliderVolumen(int valorInicial) {
        Slider slider = new Slider(0f, 100f, 1f, false, skin);
        slider.setValue(valorInicial);
        return slider;
    }

    private void anadirHover(TextButton boton) {
        boton.addListener(new InputListener() {
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) GestorAudio.reproducirEfecto(GestorAudio.Efecto.HOVER);
            }
        });
    }

    private String textoDificultad() {
        switch (GestorPreferencias.getDificultad()) {
            case FACIL:   return "< Fácil >";
            case NORMAL:  return "< Normal >";
            case DIFICIL: return "< Difícil >";
            default:      return "< Normal >";
        }
    }

    private void cambiarDificultad() {
        GestorPreferencias.Dificultad actual = GestorPreferencias.getDificultad();
        GestorPreferencias.Dificultad[] valores = GestorPreferencias.Dificultad.values();
        GestorPreferencias.Dificultad siguiente = valores[(actual.ordinal() + 1) % valores.length];
        GestorPreferencias.setDificultad(siguiente);
    }
}

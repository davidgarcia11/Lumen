package com.davidgarcia.lumen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.davidgarcia.lumen.config.ConfiguracionJuego;

/** Construye la skin básica del juego para reutilizar entre pantallas. */
public final class SkinFactory {

    private SkinFactory() {}

    public static Skin crearSkinBasica() {
        Skin skin = new Skin();

        BitmapFont fuente = new BitmapFont();
        BitmapFont fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(3f);

        skin.add("default-font", fuente);
        skin.add("titulo-font", fuenteTitulo);

        skin.add("default", new Label.LabelStyle(fuente, Color.WHITE));
        skin.add("titulo", new Label.LabelStyle(fuenteTitulo, ConfiguracionJuego.COLOR_LUMEN));
        skin.add("subtitulo", new Label.LabelStyle(fuente, ConfiguracionJuego.COLOR_LUMEN));

        TextButton.TextButtonStyle estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuente;
        estiloBoton.fontColor = Color.LIGHT_GRAY;
        estiloBoton.overFontColor = ConfiguracionJuego.COLOR_LUMEN;
        skin.add("default", estiloBoton);

        skin.add("pixel-blanco", crearTextureRegionBlanca());

        TextField.TextFieldStyle estiloTextField = new TextField.TextFieldStyle();
        estiloTextField.font = fuente;
        estiloTextField.fontColor = Color.WHITE;
        estiloTextField.cursor = skin.newDrawable("pixel-blanco", ConfiguracionJuego.COLOR_LUMEN);
        estiloTextField.selection = skin.newDrawable("pixel-blanco", new Color(0.4f, 0.6f, 0.9f, 0.5f));
        estiloTextField.background = skin.newDrawable("pixel-blanco", new Color(0.10f, 0.10f, 0.15f, 0.85f));
        skin.add("default", estiloTextField);

        return skin;
    }

    /** Textura 1x1 blanca registrada como TextureRegion para que la skin pueda generar drawables tintados. */
    private static TextureRegion crearTextureRegionBlanca() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(textura);
    }
}

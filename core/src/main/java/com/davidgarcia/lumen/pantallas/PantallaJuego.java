package com.davidgarcia.lumen.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.davidgarcia.lumen.Main;
import com.davidgarcia.lumen.audio.GestorAudio;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.Entidad;
import com.davidgarcia.lumen.entidades.Personaje;
import com.davidgarcia.lumen.entidades.elementos.Brasero;
import com.davidgarcia.lumen.entidades.elementos.Puerta;
import com.davidgarcia.lumen.entidades.elementos.Santuario;
import com.davidgarcia.lumen.entidades.npc.Acechante;
import com.davidgarcia.lumen.entidades.npc.Devorador;
import com.davidgarcia.lumen.entidades.npc.Miron;
import com.davidgarcia.lumen.entidades.npc.NPC;
import com.davidgarcia.lumen.entidades.proyectiles.RafagaLuz;
import com.davidgarcia.lumen.entidades.recolectables.CristalEnergia;
import com.davidgarcia.lumen.entidades.recolectables.Esencia;
import com.davidgarcia.lumen.entidades.recolectables.Llave;
import com.davidgarcia.lumen.entidades.recolectables.Recolectable;
import com.davidgarcia.lumen.niveles.GestorNiveles;
import com.davidgarcia.lumen.ui.HUD;
import com.davidgarcia.lumen.ui.MenuPausa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Pantalla principal de juego: orquesta el bucle de simulación de las entidades. */
public class PantallaJuego extends ScreenAdapter {

    private final Main juego;

    private OrthographicCamera camara;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Personaje personaje;
    private GestorNiveles gestorNiveles;
    private List<Entidad> entidades;
    private final List<RafagaLuz> proyectiles = new ArrayList<>();
    private HUD hud;

    private MenuPausa menuPausa;
    private boolean pausado = false;

    public PantallaJuego(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(ConfiguracionJuego.ANCHO_MUNDO, ConfiguracionJuego.ALTO_MUNDO, camara);
        viewport.apply(true);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        GestorAudio.cambiarMusica(GestorAudio.PistaMusica.JUEGO);

        personaje = new Personaje(
            ConfiguracionJuego.ANCHO_MUNDO / 2f,
            ConfiguracionJuego.ALTO_MUNDO / 2f
        );
        gestorNiveles = new GestorNiveles(personaje);
        entidades = gestorNiveles.cargarSalaInicial();

        hud = new HUD(personaje, gestorNiveles);

        menuPausa = new MenuPausa(new MenuPausa.Acciones() {
            @Override public void onReanudar() {
                reanudar();
            }
            @Override public void onVolverAlMenu() {
                juego.setScreen(new PantallaMenu(juego));
            }
            @Override public void onSalirDelJuego() {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        if (pausado) {
            dibujarMundo();
            menuPausa.dibujar(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                reanudar();
            }
        } else {
            actualizar(delta);
            dibujarMundo();
            hud.dibujar();
        }
    }

    private void actualizar(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pausar();
            return;
        }

        for (Entidad entidad : entidades) {
            entidad.actualizar(delta);
        }

        recogerDisparosDelPersonaje();
        actualizarProyectiles(delta);
        detectarImpactosProyectilNPC();
        eliminarProyectilesInactivos();
        eliminarNpcsMuertosYSumarPuntos();
        recogerRecolectablesEnContacto();
        encenderBraserosEnContacto();
        gestionarInteraccionSantuario();
        reevaluarPuertas();
        gestionarAvanceSala();

        detectarColisionesPersonajeNPC();

        if (personaje.estaExtinguido()) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    private void reevaluarPuertas() {
        for (Entidad e : entidades) {
            if (e instanceof Puerta) {
                ((Puerta) e).reevaluarEstado(entidades, personaje);
            }
        }
    }

    private void gestionarAvanceSala() {
        for (Entidad e : entidades) {
            if (!(e instanceof Puerta)) continue;
            Puerta puerta = (Puerta) e;
            if (!puerta.estaAbierta()) continue;
            if (personaje.getHitbox().overlaps(puerta.getHitbox())) {
                avanzarASiguienteSala();
                return;
            }
        }
    }

    private void avanzarASiguienteSala() {
        personaje.sumarPuntos(ConfiguracionJuego.PUNTOS_COMPLETAR_SALA);
        personaje.recibirEnergia(ConfiguracionJuego.BONUS_ENERGIA_COMPLETAR_SALA);
        if (personaje.tieneLlave()) personaje.consumirLlave();
        List<Entidad> siguientes = gestorNiveles.avanzarSala();
        if (siguientes == entidades) {
            // Era la última sala del juego: la pantalla de victoria llega en Fase 5.
            juego.setScreen(new PantallaMenu(juego));
            return;
        }
        entidades = siguientes;
        proyectiles.clear();
        recolocarPersonaje();
    }

    private void recogerDisparosDelPersonaje() {
        RafagaLuz nuevo = personaje.consumirDisparoPendiente();
        if (nuevo != null) {
            proyectiles.add(nuevo);
            GestorAudio.reproducirEfecto(GestorAudio.Efecto.RAFAGA);
        }
    }

    private void actualizarProyectiles(float delta) {
        for (RafagaLuz p : proyectiles) {
            p.actualizar(delta);
        }
    }

    private void detectarImpactosProyectilNPC() {
        for (RafagaLuz proyectil : proyectiles) {
            if (!proyectil.estaActivo()) continue;
            for (Entidad entidad : entidades) {
                if (!(entidad instanceof NPC)) continue;
                NPC npc = (NPC) entidad;
                if (!npc.estaVivo()) continue;
                if (proyectil.getHitbox().overlaps(npc.getHitbox())) {
                    npc.recibirDano(proyectil.getDano());
                    proyectil.desactivar();
                    GestorAudio.reproducirEfecto(GestorAudio.Efecto.IMPACTO);
                    break;
                }
            }
        }
    }

    private void eliminarProyectilesInactivos() {
        proyectiles.removeIf(p -> !p.estaActivo());
    }

    private void eliminarNpcsMuertosYSumarPuntos() {
        Iterator<Entidad> it = entidades.iterator();
        while (it.hasNext()) {
            Entidad e = it.next();
            if (!(e instanceof NPC)) continue;
            NPC npc = (NPC) e;
            if (!npc.estaVivo()) {
                personaje.sumarPuntos(puntosPorTipo(npc));
                it.remove();
            }
        }
    }

    private int puntosPorTipo(NPC npc) {
        if (npc instanceof Devorador) return ConfiguracionJuego.PUNTOS_DEVORADOR;
        if (npc instanceof Miron) return ConfiguracionJuego.PUNTOS_MIRON;
        if (npc instanceof Acechante) return ConfiguracionJuego.PUNTOS_ACECHANTE;
        return 0;
    }

    private void recogerRecolectablesEnContacto() {
        Iterator<Entidad> it = entidades.iterator();
        while (it.hasNext()) {
            Entidad e = it.next();
            if (!(e instanceof Recolectable)) continue;
            Recolectable r = (Recolectable) e;
            if (r.estaRecogido()) continue;
            if (personaje.getHitbox().overlaps(r.getHitbox())) {
                r.recoger(personaje);
                it.remove();
            }
        }
    }

    private void encenderBraserosEnContacto() {
        for (Entidad e : entidades) {
            if (!(e instanceof Brasero)) continue;
            Brasero b = (Brasero) e;
            if (b.estaEncendido()) continue;
            if (personaje.getHitbox().overlaps(b.getHitbox())) {
                b.encender();
            }
        }
    }

    private void gestionarInteraccionSantuario() {
        if (!Gdx.input.isKeyJustPressed(Input.Keys.E)) return;
        Santuario objetivo = santuarioAlAlcance();
        if (objetivo == null) return;
        boolean activado = objetivo.intentarActivar(personaje);
        if (activado) {
            GestorAudio.reproducirEfecto(GestorAudio.Efecto.DESPERTAR);
        }
    }

    private void dibujarIndicadorInteraccion(ShapeRenderer shapes) {
        Santuario s = santuarioAlAlcance();
        if (s == null || s.estaActivado()) return;
        // Pequeño punto pulsante encima del santuario para indicar interacción.
        float yBase = s.getPosicion().y + ConfiguracionJuego.SANTUARIO_TAMANO * 0.7f;
        shapes.setColor(1f, 1f, 1f, 0.9f);
        shapes.circle(s.getPosicion().x, yBase, 2.5f);
    }

    private Santuario santuarioAlAlcance() {
        float rangoCuadrado = ConfiguracionJuego.RANGO_INTERACCION * ConfiguracionJuego.RANGO_INTERACCION;
        Santuario mejor = null;
        float mejorDistCuadrada = Float.MAX_VALUE;
        for (Entidad e : entidades) {
            if (!(e instanceof Santuario)) continue;
            Santuario s = (Santuario) e;
            float dx = s.getPosicion().x - personaje.getPosicion().x;
            float dy = s.getPosicion().y - personaje.getPosicion().y;
            float distCuadrada = dx * dx + dy * dy;
            if (distCuadrada > rangoCuadrado) continue;
            if (distCuadrada < mejorDistCuadrada) {
                mejor = s;
                mejorDistCuadrada = distCuadrada;
            }
        }
        return mejor;
    }

    private void recolocarPersonaje() {
        personaje.getPosicion().set(
            ConfiguracionJuego.ANCHO_MUNDO / 2f,
            ConfiguracionJuego.ALTO_MUNDO * 0.15f
        );
    }

    private void pausar() {
        pausado = true;
        menuPausa.mostrar();
    }

    private void reanudar() {
        pausado = false;
        Gdx.input.setInputProcessor(null);
    }

    private void detectarColisionesPersonajeNPC() {
        if (personaje.esInvulnerable()) return;

        for (Entidad entidad : entidades) {
            if (!(entidad instanceof NPC)) continue;
            NPC npc = (NPC) entidad;
            if (!npc.estaVivo()) continue;
            if (personaje.getHitbox().overlaps(npc.getHitbox())) {
                personaje.recibirDano(npc.getDanoAlJugador());
                GestorAudio.reproducirEfecto(GestorAudio.Efecto.DANO);
                return;
            }
        }
    }

    private void dibujarMundo() {
        var colorBase = gestorNiveles.getColorAcentoActual();
        ScreenUtils.clear(
            colorBase.r * 0.15f,
            colorBase.g * 0.15f,
            colorBase.b * 0.15f,
            1f
        );

        camara.update();
        batch.setProjectionMatrix(camara.combined);
        shapeRenderer.setProjectionMatrix(camara.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entidad entidad : entidades) {
            if (entidad instanceof Miron) {
                ((Miron) entidad).dibujarConoVision(shapeRenderer);
            }
        }
        for (RafagaLuz proyectil : proyectiles) {
            proyectil.dibujarForma(shapeRenderer);
        }
        for (Entidad entidad : entidades) {
            if (entidad instanceof Puerta)         ((Puerta) entidad).dibujarForma(shapeRenderer);
            else if (entidad instanceof Brasero)   ((Brasero) entidad).dibujarForma(shapeRenderer);
            else if (entidad instanceof Santuario) ((Santuario) entidad).dibujarForma(shapeRenderer);
            else if (entidad instanceof Esencia)   ((Esencia) entidad).dibujarForma(shapeRenderer);
            else if (entidad instanceof CristalEnergia) ((CristalEnergia) entidad).dibujarForma(shapeRenderer);
            else if (entidad instanceof Llave)     ((Llave) entidad).dibujarForma(shapeRenderer);
        }
        dibujarIndicadorInteraccion(shapeRenderer);
        shapeRenderer.end();

        batch.begin();
        for (Entidad entidad : entidades) {
            entidad.dibujar(batch, shapeRenderer);
        }
        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if (hud != null) hud.resize(width, height);
        if (menuPausa != null) menuPausa.resize(width, height);
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (hud != null) hud.dispose();
        if (menuPausa != null) menuPausa.dispose();
    }
}

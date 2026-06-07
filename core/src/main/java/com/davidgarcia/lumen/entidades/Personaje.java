package com.davidgarcia.lumen.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.davidgarcia.lumen.config.ConfiguracionJuego;
import com.davidgarcia.lumen.entidades.proyectiles.RafagaLuz;
import com.davidgarcia.lumen.utiles.Animacion;
import com.davidgarcia.lumen.utiles.SpritesLumen;

/** Personaje controlado por el jugador. */
public class Personaje extends Entidad {

    private enum Direccion { ABAJO, ARRIBA, IZQUIERDA, DERECHA }

    private final Vector2 direccionMovimiento = new Vector2();

    private final float velocidad;
    private final float energiaMaxima;
    private final float consumoPorSegundo;

    private float energia;
    private float invulnerabilidadRestante = 0f;
    private float tiempoParpadeo = 0f;
    private int puntos = 0;

    private boolean tieneRafaga = false;
    private float cooldownRafaga = 0f;
    private RafagaLuz disparoPendiente = null;

    private int esencias = 0;
    private boolean tieneLlave = false;

    private Direccion direccionActual = Direccion.ABAJO;
    private boolean moviendose = false;

    public Personaje(float x, float y) {
        super(x, y);
        this.velocidad = ConfiguracionJuego.LUMEN_VELOCIDAD;
        this.energiaMaxima = ConfiguracionJuego.LUMEN_ENERGIA_MAXIMA;
        this.consumoPorSegundo = ConfiguracionJuego.LUMEN_CONSUMO_POR_SEGUNDO;
        this.energia = energiaMaxima;
        actualizarHitbox();
    }

    @Override
    public void actualizar(float delta) {
        leerEntrada();
        mover(delta);
        consumirEnergia(delta);
        actualizarCooldownRafaga(delta);
        actualizarInvulnerabilidad(delta);
        actualizarAnimaciones(delta);
        actualizarHitbox();
    }

    private void leerEntrada() {
        direccionMovimiento.setZero();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direccionMovimiento.y += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) direccionMovimiento.y -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) direccionMovimiento.x += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direccionMovimiento.x -= 1f;
        moviendose = !direccionMovimiento.isZero();
        if (moviendose) {
            direccionMovimiento.nor();
            actualizarDireccionVisual();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            intentarDisparar();
        }
    }

    private void actualizarDireccionVisual() {
        if (Math.abs(direccionMovimiento.x) > Math.abs(direccionMovimiento.y)) {
            direccionActual = direccionMovimiento.x > 0 ? Direccion.DERECHA : Direccion.IZQUIERDA;
        } else {
            direccionActual = direccionMovimiento.y > 0 ? Direccion.ARRIBA : Direccion.ABAJO;
        }
    }

    private void mover(float delta) {
        posicion.x += direccionMovimiento.x * velocidad * delta;
        posicion.y += direccionMovimiento.y * velocidad * delta;
        clampAlMundo();
    }

    private void clampAlMundo() {
        float margen = ConfiguracionJuego.LUMEN_TAMANO_HITBOX / 2f;
        posicion.x = Math.max(margen, Math.min(posicion.x, ConfiguracionJuego.ANCHO_MUNDO - margen));
        posicion.y = Math.max(margen, Math.min(posicion.y, ConfiguracionJuego.ALTO_MUNDO - margen));
    }

    private void consumirEnergia(float delta) {
        energia = Math.max(0f, energia - consumoPorSegundo * delta);
    }

    private void actualizarCooldownRafaga(float delta) {
        if (cooldownRafaga > 0f) cooldownRafaga -= delta;
    }

    private void intentarDisparar() {
        if (!tieneRafaga) return;
        if (cooldownRafaga > 0f) return;
        if (energia <= ConfiguracionJuego.RAFAGA_COSTE_ENERGIA) return;
        Vector2 dir = direccionDisparoActual();
        disparoPendiente = new RafagaLuz(posicion.x, posicion.y, dir);
        energia -= ConfiguracionJuego.RAFAGA_COSTE_ENERGIA;
        cooldownRafaga = ConfiguracionJuego.RAFAGA_COOLDOWN;
    }

    private Vector2 direccionDisparoActual() {
        switch (direccionActual) {
            case ARRIBA:    return new Vector2(0f, 1f);
            case IZQUIERDA: return new Vector2(-1f, 0f);
            case DERECHA:   return new Vector2(1f, 0f);
            case ABAJO:
            default:        return new Vector2(0f, -1f);
        }
    }

    /** Devuelve el disparo generado este frame (o null) y lo limpia. La PantallaJuego lo inserta en su lista. */
    public RafagaLuz consumirDisparoPendiente() {
        RafagaLuz d = disparoPendiente;
        disparoPendiente = null;
        return d;
    }

    private void actualizarInvulnerabilidad(float delta) {
        if (invulnerabilidadRestante > 0f) {
            invulnerabilidadRestante -= delta;
            tiempoParpadeo += delta;
        } else {
            tiempoParpadeo = 0f;
        }
    }

    private void actualizarAnimaciones(float delta) {
        SpritesLumen.idle.actualizar(delta);
        SpritesLumen.andarAbajo.actualizar(delta);
        SpritesLumen.andarArriba.actualizar(delta);
        SpritesLumen.andarDerecha.actualizar(delta);
        SpritesLumen.andarIzquierda.actualizar(delta);
    }

    private void actualizarHitbox() {
        float lado = ConfiguracionJuego.LUMEN_TAMANO_HITBOX;
        hitbox.set(posicion.x - lado / 2f, posicion.y - lado / 2f, lado, lado);
    }

    @Override
    public void dibujar(SpriteBatch batch, ShapeRenderer shapes) {
        if (estaParpadeandoInvisible()) return;

        Animacion animacion = animacionActual();
        var frame = animacion.getFrameActual();
        float anchoSprite = ConfiguracionJuego.LUMEN_TAMANO_VISUAL;
        float altoSprite = ConfiguracionJuego.LUMEN_TAMANO_VISUAL;

        batch.draw(
            frame,
            posicion.x - anchoSprite / 2f,
            posicion.y - altoSprite / 2f,
            anchoSprite,
            altoSprite
        );
    }

    private Animacion animacionActual() {
        if (!moviendose) return SpritesLumen.idle;
        switch (direccionActual) {
            case ARRIBA:    return SpritesLumen.andarArriba;
            case IZQUIERDA: return SpritesLumen.andarIzquierda;
            case DERECHA:   return SpritesLumen.andarDerecha;
            case ABAJO:
            default:        return SpritesLumen.andarAbajo;
        }
    }

    private boolean estaParpadeandoInvisible() {
        if (invulnerabilidadRestante <= 0f) return false;
        return ((int) (tiempoParpadeo * 20)) % 2 == 0;
    }

    public void recibirDano(float dano) {
        if (invulnerabilidadRestante > 0f) return;
        energia = Math.max(0f, energia - dano);
        invulnerabilidadRestante = ConfiguracionJuego.INVULNERABILIDAD_DURACION;
        tiempoParpadeo = 0f;
    }

    public boolean esInvulnerable() {
        return invulnerabilidadRestante > 0f;
    }

    public float getEnergia() { return energia; }
    public float getEnergiaMaxima() { return energiaMaxima; }
    public boolean estaExtinguido() { return energia <= 0f; }
    public int getPuntos() { return puntos; }
    public float getPorcentajeEnergia() {
        return energia / energiaMaxima;
    }
    public void sumarPuntos(int puntos) { this.puntos += puntos; }

    public boolean tieneRafaga() { return tieneRafaga; }
    public void desbloquearRafaga() { tieneRafaga = true; }

    public int getEsencias() { return esencias; }
    public void sumarEsencia() { esencias++; }
    public boolean gastarEsencias(int cantidad) {
        if (esencias < cantidad) return false;
        esencias -= cantidad;
        return true;
    }

    public boolean tieneLlave() { return tieneLlave; }
    public void recogerLlave() { tieneLlave = true; }
    public void consumirLlave() { tieneLlave = false; }

    public void recibirEnergia(float cantidad) {
        if (cantidad <= 0f) return;
        energia = Math.min(energiaMaxima, energia + cantidad);
    }

    /**
     * Restablece el estado del personaje para reintentar el nivel actual:
     * energía al máximo y consumibles a cero. Conserva los puntos acumulados.
     */
    public void reiniciarParaNuevoNivel() {
        energia = energiaMaxima;
        invulnerabilidadRestante = 0f;
        tiempoParpadeo = 0f;
        cooldownRafaga = 0f;
        disparoPendiente = null;
        esencias = 0;
        tieneLlave = false;
        tieneRafaga = false;
    }
}

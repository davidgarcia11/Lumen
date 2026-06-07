# Lumen — Capítulo I: La Ascensión

> Videojuego *top-down* 2D desarrollado con libGDX.
> Actividad de Aprendizaje · 2ª Evaluación · Curso 2025-2026
> Programación Multimedia y Dispositivos Móviles · SEAS / Fundación San Valero

---

## Sobre el juego

**Lumen** es un pequeño espíritu de luz que despierta en las profundidades olvidadas de un templo perdido bajo la jungla. Su misión: ascender hasta la superficie antes de extinguirse.

Su luz es su identidad, su arma y su vulnerabilidad. Ilumina el camino, debilita a las criaturas de sombra que custodian el templo y le permite resolver los rompecabezas que separan las salas, pero se consume con el tiempo y con cada daño recibido.

A través de **6 salas distribuidas en 2 niveles**, el jugador decide cuándo enfrentarse a la oscuridad y cuándo esquivarla, recolectando esencias para desbloquear la **ráfaga de luz** en el santuario olvidado del templo.

## Cómo ejecutar

Requisitos: **Java 17+** y un sistema con OpenGL.

```bash
git clone https://github.com/davidgarcia11/Lumen.git
cd Lumen
./gradlew lwjgl3:run
```

Descarga directa del ejecutable: [Releases](https://github.com/davidgarcia11/Lumen/releases).

## Controles

| Tecla | Acción |
|---|---|
| W / A / S / D | Movimiento en 8 direcciones |
| Espacio | Ráfaga de luz (una vez desbloqueada en el santuario) |
| E | Activar santuario cuando estás cerca |
| Esc | Abrir / cerrar menú de pausa |

## Características

- **Movimiento 8 direcciones** con vector normalizado.
- **Sistema de luz** como recurso unificado: vida, iluminación y combustible.
- **Combate opcional**: esquivar enemigos suele ser tan válido como combatirlos.
- **Santuario** que desbloquea la ráfaga de luz a cambio de 2 esencias.
- **3 tipos de criaturas de sombra** con IA propia (Acechante, Mirón, Devorador) implementadas con el patrón State.
- **Top 10 de Récords de Campeones** persistente con nombre, tiempo y fecha.
- **Guardar y cargar partida** con autosave por sala.
- **Menú de pausa in-game** con sonido on/off, reintentar nivel, menú y salir.
- **Configuración persistente**: volumen de música, volumen de efectos y dificultad.

## Requisitos del enunciado cubiertos

### Primera entrega — obligatorios (5/5)

| Requisito | Cubierto en |
|---|---|
| Personaje principal + inicio/final/objetivo + ≥2 niveles diferenciados | `entidades/Personaje`, `niveles/{Nivel,Sala,Salas}`, `pantallas/{PantallaMenu,PantallaJuego,PantallaVictoria}` |
| Información en pantalla actualizada (puntuación, energía, nivel) | `ui/HUD` |
| Menús + instrucciones + rejugar sin salir + ≥2 opciones configurables | `pantallas/{PantallaMenu,PantallaConfiguracion,PantallaInstrucciones,PantallaRecords}`, 3 opciones (música, efectos, dificultad) |
| ≥3 NPCs distintos interactuando con el jugador | `entidades/npc/{Acechante,Miron,Devorador}` |
| Sonido + animaciones en todos los caracteres con movimiento | `audio/GestorAudio`, `utiles/{SpritesLumen,SpritesEnemigos,Animacion}` |

### Segunda entrega — opcionales (5/5)

| Opcional | Cubierto en |
|---|---|
| **A**. GitHub gestionado (commits, PRs, issues, releases, wiki) | Historial completo de ramas `feature/*`, PRs `#13`-`#24`, [release v1.0.0](https://github.com/davidgarcia11/Lumen/releases/tag/v1.0.0) |
| **B**. Top 10 con nombre + persistencia | `datos/{Puntuacion,GestorRecords}`, `pantallas/{PantallaVictoria,PantallaRecords}` |
| **C**. IA en NPCs | `ia/` con `MaquinaEstados` + 7 estados (patrón State del Gang of Four) |
| **D**. Menú in-game (sonido, reintentar, menú, salir) | `ui/MenuPausa` |
| **I**. Guardar y cargar partida | `datos/{EstadoPartida,GestorGuardado}` + autosave en `PantallaJuego` |

## Arquitectura

Patrones y decisiones de diseño:

- **POO clásica** (clases con responsabilidad clara, herencia donde aporta, parametrización donde solo cambian valores).
- **Patrón Screen de libGDX** para separar pantallas con su propio ciclo de vida.
- **Patrón State** para las máquinas de estados de los NPCs.
- **Hitboxes diferenciadas** entre núcleo (daña al jugador) y cuerpo (recibe daño de la ráfaga).
- **Resolución lógica** de 480×270 píxeles escalada con `FitViewport` y filtro `Nearest Neighbor`.

Documentación completa: [`docs/DISENO.md`](docs/DISENO.md).

## Tecnologías

- **Java 17** — lenguaje de desarrollo.
- **libGDX 1.14.1** — motor 2D multiplataforma.
- **Gradle 8.x** — sistema de build (incluido vía wrapper).
- **Pixel art** generado por código como estilo visual (sin assets gráficos externos).

## Estructura del proyecto

```
core/                     ← lógica del juego (modulo compartible entre plataformas)
  src/main/java/com/davidgarcia/lumen/
    Main.java
    audio/                ← GestorAudio
    config/               ← ConfiguracionJuego, GestorPreferencias
    datos/                ← Puntuacion, GestorRecords, EstadoPartida, GestorGuardado
    entidades/            ← Personaje + npc/, proyectiles/, recolectables/, elementos/
    ia/                   ← MaquinaEstados + estados/
    niveles/              ← Nivel, Sala, Salas, GestorNiveles, CondicionApertura
    pantallas/            ← Menú, Juego, Configuración, Instrucciones, Records, Victoria
    ui/                   ← HUD, MenuPausa, SkinFactory
    utiles/               ← Sprites, Animacion, GestorAssets, GeneradorPixmap
lwjgl3/                   ← launcher de escritorio
assets/                   ← música y efectos de sonido
docs/DISENO.md            ← documento de diseño
```

## Estructura de ramas

- `main` — versión publicada (v1.0.0).
- `develop` — rama de integración.
- `feature/*`, `fix/*` — ramas de trabajo, integradas vía Pull Request.

## Autor

David García — Alumno de Programación Multimedia y Dispositivos Móviles, SEAS / Fundación San Valero.

## Licencia y créditos

Proyecto académico. Los assets gráficos se generan por código. Los recursos de audio utilizados son obras de terceros con licencias compatibles.

### Audio

**Música** — Kevin MacLeod ([incompetech.com](https://incompetech.com/)), bajo licencia [Creative Commons: By Attribution 4.0](https://creativecommons.org/licenses/by/4.0/):
- `audio/musica/menu.mp3`
- `audio/musica/juego.mp3`

**Efectos** — Kenney ([kenney.nl](https://kenney.nl/)), bajo licencia [Creative Commons CC0](https://creativecommons.org/publicdomain/zero/1.0/):
- Pack *UI Audio* — efectos `hover.ogg`, `click.ogg`.
- Pack *Impact Sounds* — efectos `dano.ogg`, `despertar.ogg`.

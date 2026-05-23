# Lumen — Capítulo I: La Ascensión

> Videojuego *top-down* 2D desarrollado con libGDX.
> Actividad de Aprendizaje · 2ª Evaluación · Curso 2025-2026
> Programación Multimedia y Dispositivos Móviles · SEAS / Fundación San Valero

---

## Sobre el juego

**Lumen** es un pequeño espíritu de luz que despierta en las profundidades olvidadas de un templo perdido bajo la jungla. Su misión: ascender hasta la superficie antes de extinguirse.

Su luz es su identidad, su arma y su vulnerabilidad. Ilumina el camino, debilita a las criaturas de sombra que custodian el templo y le permite resolver los rompecabezas que separan las salas, pero se consume con el tiempo y con cada daño recibido.

A través de doce salas distribuidas en dos niveles, el jugador decide cuándo enfrentarse a la oscuridad y cuándo esquivarla, recolectando esencias para desbloquear nuevas habilidades en los santuarios olvidados del templo.

## Características

- **Movimiento 8 direcciones** con vector normalizado.
- **Sistema de luz** como recurso unificado: vida, iluminación y combustible.
- **Combate opcional**: el jugador puede esquivar a los enemigos en lugar de enfrentarlos.
- **3 personajes seleccionables** con estadísticas y mecánicas únicas (Luminis, Ignis, Aethen).
- **Sistema de habilidades por santuarios**: ráfaga de luz, dash con invulnerabilidad y aura permanente.
- **IA con máquinas de estados** para los 4 tipos de criaturas de sombra.
- **Top 10 de Récords de Campeones** persistente.
- **Guardar y cargar partida**.
- **Menú de pausa in-game**.

## Controles

| Tecla | Acción |
|---|---|
| W / A / S / D | Movimiento en 8 direcciones |
| Espacio | Ráfaga de luz (una vez desbloqueada) |
| Shift izquierdo | Dash con invulnerabilidad (una vez desbloqueado) |
| E | Interactuar (palancas, santuarios, inscripciones, puertas) |
| Esc | Abrir/cerrar menú de pausa |

## Tecnologías

- **libGDX 1.14.1** — motor de videojuegos 2D multiplataforma.
- **Java 17** — lenguaje de desarrollo.
- **Gradle** — sistema de build (incluido vía wrapper).
- **Tiled** — editor externo de mapas (`.tmx`).
- **Pixel art** 16-bit como estilo visual.

## Documentación

El proyecto incluye documentación detallada:

- [`docs/DISENO.md`](docs/DISENO.md) — Documento de diseño completo (concepto, mecánicas, niveles, arquitectura técnica).

## Estado del proyecto

Proyecto en desarrollo. Consulta la pestaña [Releases](https://github.com/davidgarcia11/Lumen/releases) para ver las versiones publicadas.

- 🚧 Fase de diseño completada.
- 🚧 Implementación en curso.

## Estructura de ramas

- `main` — versiones estables (releases v1.0 y v2.0).
- `develop` — rama de integración del desarrollo.
- `feature/*`, `fix/*`, `docs/*` — ramas de trabajo, integradas vía Pull Request.

## Autor

David García — Alumno de Programación Multimedia y Dispositivos Móviles, SEAS / Fundación San Valero.

## Licencia y créditos

Proyecto académico. Los assets gráficos y de audio utilizados son recursos gratuitos de terceros con licencias compatibles (Kenney, OpenGameArt, itch.io u otros). Los créditos completos se publicarán en la wiki del repositorio.

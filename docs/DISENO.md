# Documento de Diseño — Lumen

> **Capítulo I: La Ascensión**
>
> Videojuego top-down 2D desarrollado con libGDX
> Actividad de Aprendizaje · 2ª Evaluación · Curso 2025-2026
> Programación Multimedia y Dispositivos Móviles · SEAS / Fundación San Valero
> Autor: David García

---

## Nota sobre el alcance del documento

Este documento define **Lumen — Capítulo I: La Ascensión**, primera entrega de un proyecto que se diseña con visión de expansión futura. En cada sección se distinguen dos capas:

- 🎯 **Scope del entregable**: lo que se implementa para esta Actividad de Aprendizaje y constituye el producto evaluable.
- 🔮 **Visión a futuro**: características diseñadas pero deliberadamente fuera del scope académico, que perfilan hacia dónde podría crecer el juego.

El criterio de evaluación de este trabajo debe aplicarse exclusivamente sobre el **Scope del entregable**. La capa de Visión a futuro se incluye para mostrar visión de producto y dejar el proyecto abierto a continuación, no como compromiso de implementación.

---

## Índice

1. [Concepto y pitch](#1-concepto-y-pitch)
2. [Temática y ambientación](#2-temática-y-ambientación)
3. [Mecánicas principales](#3-mecánicas-principales)
4. [Personaje y controles](#4-personaje-y-controles)
5. [Enemigos y NPCs](#5-enemigos-y-npcs)
6. [Niveles](#6-niveles)
7. [HUD e información en pantalla](#7-hud-e-información-en-pantalla)
8. [Sistema de progresión](#8-sistema-de-progresión)
9. [Personajes seleccionables](#9-personajes-seleccionables)
10. [Arquitectura técnica](#10-arquitectura-técnica)

---

## 1. Concepto y pitch

**Lumen** es un videojuego *top-down* 2D de exploración y puzzle ligero ambientado en un templo perdido bajo la jungla. El jugador encarna a **Lumen**, un pequeño espíritu de luz que despierta en las profundidades olvidadas del templo y debe ascender hasta la superficie antes de extinguirse.

Su luz es su identidad, su arma y su vulnerabilidad: ilumina las salas, debilita a las criaturas de sombra que custodian el templo y le permite resolver pequeños rompecabezas, pero se consume con el tiempo y con el daño recibido. El jugador avanza sala a sala, decidiendo cuándo enfrentarse a la oscuridad y cuándo esquivarla, hasta alcanzar la superficie y completar su ascensión.

### Tres pilares del juego

- **Luz como recurso central**: la energía vital del protagonista es a la vez su iluminación, su arma y su vida. Toda mecánica gira en torno a este recurso.
- **Combate opcional**: el jugador puede esquivar a los enemigos en lugar de combatirlos. Esto premia el ingenio sobre la fuerza bruta y se adapta a distintos estilos de juego.
- **Salas como unidades de puzzle**: cada sala es un pequeño rompecabezas autocontenido (palancas, llaves, plataformas de presión, orden de eliminación de enemigos) cuya resolución abre la salida hacia la siguiente.

### Audiencia y duración

Partidas cortas (~10 minutos) pensadas para una sentada rápida. Estructura modular que invita a rejugar para mejorar la puntuación.

### Justificación del nombre

*Lumen* es la unidad de medida del flujo luminoso en el Sistema Internacional. El nombre refuerza el tema central del juego — la luz como esencia del protagonista — y otorga identidad propia al título.

### Final del Capítulo I

Al completar el segundo nivel, Lumen emerge a la superficie. La pantalla final muestra un mensaje narrativo con efecto *typewriter*:

> *Lumen emerge al amanecer.*
> *El aire frío de la jungla recibe su luz por primera vez en siglos.*
>
> *A sus espaldas, el templo guarda silencio.*
> *Pero en lo más profundo, algo todavía vigila.*
> *Y algo ha empezado a despertar.*
>
> **Continuará...**

Este cierre deja explícitamente abierta la narrativa para una hipotética continuación.

---

## 2. Temática y ambientación

### Estilo visual

Pixel art moderno de 16 bits, con paleta rica y nivel de detalle medio. Este estilo es accesible a través de packs de assets gratuitos (Kenney, OpenGameArt, itch.io) y resulta suficientemente expresivo para transmitir la atmósfera deseada sin requerir habilidades artísticas avanzadas.

### Paleta y atmósfera general

Alto contraste. La base del juego son los **negros profundos**, sobre los que destacan **acentos brillantes de color** que aportan identidad visual a cada nivel y refuerzan la sensación de luz frente a oscuridad — eje temático del juego.

### Estética del protagonista (Lumen)

Pequeña figura humanoide luminosa, o alternativamente una esfera/llama con halo. Animación idle con una pulsación sutil de su luz. El brillo del personaje se atenúa visualmente conforme se consume su energía, ofreciendo así un refuerzo visual directo del recurso central de juego.

### Estética de los enemigos

Criaturas de sombra. Siluetas oscuras prácticamente planas, sin detalles internos, con ojos brillantes (blancos, rojos o cyan) como única "personalidad" visible. Esta estética unificada facilita la búsqueda y creación de assets coherentes entre sí, y refuerza la dualidad temática luz/oscuridad.

### Sonido y atmósfera sonora

- **Nivel 1**: ambiente reverberante de cueva, con goteos lejanos y ecos. Música minimalista y etérea.
- **Nivel 2**: sonidos más naturales (viento que se cuela por grietas, hojas distantes). Música que mantiene la base etérea pero introduce notas más cálidas.

### Progresión visual entre niveles

🎯 **Scope del entregable** — Dos niveles obligatorios:

| | **Nivel 1 — Catacumbas profundas** | **Nivel 2 — Templo superior** |
|---|---|---|
| **Acentos de color** | Azulados / violetas | Dorados / ámbar |
| **Sensación** | Fría, mineral, húmeda, claustrofóbica | Cálida, ritual, abierta |
| **Estructura espacial** | Pasillos estrechos, salas pequeñas | Salas amplias, columnas, vestíbulos |
| **Elementos decorativos** | Estalactitas, agua estancada, musgo | Relieves, altares, ofrendas, grietas con luz natural |

🔮 **Visión a futuro** — Niveles posibles en capítulos posteriores: la jungla exterior al amanecer, la cumbre del templo bajo el sol pleno, o el descenso a niveles más profundos en una secuela donde "algo ha despertado".

### Progresión narrativa-jugable

El viaje de Lumen es una **ascensión hacia la luz exterior**. La dificultad aumenta por mecánicas (enemigos más rápidos y variados, salas más complejas con puzzles encadenados), no por incremento de oscuridad. Los enemigos del nivel 2 representan a los guardianes más activos del templo, que intentan detener a Lumen ahora que está cerca de escapar.

---

## 3. Mecánicas principales

### Verbo principal del juego

**Explorar, esquivar y, cuando convenga, iluminar.**

El jugador atraviesa el templo sala a sala. En cada sala debe resolver un pequeño puzzle para acceder a la siguiente. El combate es una herramienta opcional: en muchas situaciones es más eficiente esquivar a los enemigos que enfrentarlos.

### Sistema de luz / energía

🎯 **Scope del entregable**:

La energía es el recurso central del juego. Funciona simultáneamente como vida, como iluminación visual y como combustible para habilidades activas.

- **Inicio**: Lumen empieza al 100% al comenzar un nivel o al cargar partida.
- **Consumo lento por tiempo**: la energía disminuye gradualmente con el paso del tiempo. Genera sensación de no poder demorarse eternamente, pero sin presión asfixiante.
- **Consumo rápido por daño**: al recibir el impacto de un enemigo, se pierde una cantidad significativa de energía de golpe.
- **Consumo puntual por habilidad activa**: usar la ráfaga de luz o el dash consume energía adicional.
- **Visual**: el brillo del sprite de Lumen se atenúa progresivamente conforme baja la energía. Esto da feedback visual constante al jugador sin necesidad de mirar el HUD.
- **Game Over**: cuando la energía llega a 0, Lumen se extingue.

### Recuperación de energía

🎯 **Scope del entregable**:

Mezcla de fuentes pasivas y de logro, para que el jugador siempre tenga oportunidades de recuperarse:

- **Cristales de luz** distribuidos por las salas. Se recogen automáticamente al pasar por encima. Otorgan una cantidad fija de energía.
- **Bonus por completar sala**: al resolver el puzzle y cruzar a la siguiente sala, se recupera un porcentaje fijo de energía. Refuerza la sensación de logro y permite balancear la dificultad: salas duras dan bonus mayor.
- **Bonus por combate exitoso**: eliminar a un enemigo otorga puntos + una pequeña cantidad de energía (aproximadamente la mitad de la que costó la habilidad usada para eliminarlo). El combate no es penalizado, pero tampoco es la mejor fuente de energía: es una opción más.

🔮 **Visión a futuro**: santuarios de luz (puntos de guardado in-world que recargan al 100%), reliquias que aumentan la energía máxima, etc.

### Habilidades — Desbloqueo progresivo

🎯 **Scope del entregable**:

Lumen adquiere sus capacidades a lo largo del juego, no las tiene todas desde el inicio. Este desbloqueo se justifica narrativamente como Lumen "recordando" sus poderes a medida que asciende. El sistema concreto de desbloqueo (santuarios + esencias coleccionables) se describe en la sección 4.

| Habilidad | Etapa de desbloqueo | Coste | Comportamiento |
|---|---|---|---|
| **Movimiento básico** | Desde el inicio del Nivel 1 | — | Desplazamiento en 8 direcciones. Esquivar enemigos. |
| **Ráfaga de luz** | Mitad del Nivel 1 | Energía adicional al usarla | Ataque activo: emite un destello en su dirección actual que daña a los enemigos en un radio frontal. Tiene un pequeño *cooldown*. |
| **Dash con invulnerabilidad** | Inicio del Nivel 2 | Energía adicional al usarlo | Impulso corto y rápido en la dirección actual. Durante el dash, Lumen atraviesa enemigos sin recibir daño. |
| **Aura permanente** | Mitad del Nivel 2 | — | Daño continuo (poco a poco) a los enemigos que entran en el radio de luz natural de Lumen. Funcionamiento pasivo. |

> **Nota de diseño** — Orden de desbloqueo: la ráfaga (activa) se obtiene antes que el aura (pasiva). Esto invierte la intuición habitual y es deliberado: refuerza la curva de implicación del jugador, que primero aprende a esquivar, después aprende a gestionar un recurso al atacar activamente, y finalmente alcanza el clímax de poder con un aura permanente. El dash se sitúa en la transición entre niveles, ofreciendo una recompensa por completar el Nivel 1 y una herramienta nueva justo cuando los enemigos del Nivel 2 demandan mayor agilidad.

🔮 **Visión a futuro**: más habilidades elementales según el espíritu elegido (escudo de luz, salto/teleportación corta, vínculo con cristales para teletransportarse entre ellos, etc.).

### Elementos de puzzle en las salas

🎯 **Scope del entregable**:

Mezcla variada para que cada sala se sienta diferente:

- **Activación pasiva**: Lumen activa elementos (braseros, runas) simplemente acercándose o rozándolos. Su naturaleza luminosa los enciende.
- **Llaves físicas**: objetos recogibles que abren puertas concretas. Útiles para puzzles de "buscar antes de avanzar".
- **Palancas**: activan o desactivan mecanismos (puertas, plataformas, trampas). Algunas alternan estados, otras son de un solo uso.
- **Plataformas de presión**: requieren ser pisadas para mantener un mecanismo activo. Algunas necesitan que Lumen las mantenga, otras se activan al colocar un objeto encima, otras requieren a un enemigo.
- **Antorchas / braseros**: se encienden con la ráfaga o al rozarlos, y algunas se apagan con presencia prolongada. Encender en orden específico revela salidas.
- **Losas con memoria**: se iluminan al ser pisadas y mantienen su iluminación durante unos segundos antes de apagarse. Permiten puzzles de timing/movimiento.
- **Manipulación de enemigos**: usar la IA de las sombras (cazadores que persiguen a Lumen) para guiarlas a posiciones específicas (plataformas de presión).
- **Combat puzzle**: salas donde la puerta solo se abre al eliminar a todos los enemigos.

Cada sala combina 1-2 tipos. La idea es que **el conjunto del juego** ofrezca variedad, no cada sala individual.

🔮 **Visión a futuro**: bloques empujables, espejos para redirigir rayos de luz, salas con tiempo límite, jefes de zona.

### Condiciones de victoria y derrota

🎯 **Scope del entregable**:

- **Victoria de nivel**: alcanzar la sala de salida del nivel actual.
- **Victoria del juego**: completar el Nivel 2 y ver la pantalla narrativa final ("Continuará...").
- **Derrota**: la energía llega a 0. Game Over con opción de reintentar el nivel o volver al menú.

---

## 4. Personaje y controles

### Movimiento

🎯 **Scope del entregable**:

- **8 direcciones**: las cuatro cardinales (W, A, S, D) y las cuatro diagonales (combinaciones de dos teclas adyacentes: W+A, W+D, S+A, S+D).
- **Vector normalizado**: la velocidad de Lumen es constante independientemente de la dirección. En diagonal no se mueve más rápido que en horizontal o vertical (corrección matemática obligatoria para evitar el bug clásico de top-down).
- **Velocidad base fija** a lo largo del juego. La velocidad solo se ve modificada por el dash (puntualmente, hacia arriba).
- **Animaciones direccionales**: Lumen tiene animaciones distintas para mirar arriba, abajo, izquierda y derecha (las diagonales reutilizan la animación del eje predominante).

### Esquema de controles

🎯 **Scope del entregable**:

| Tecla | Acción |
|---|---|
| **W / A / S / D** | Movimiento en 8 direcciones |
| **Espacio** | Ráfaga de luz (una vez desbloqueada) |
| **Shift izquierdo** | Dash con invulnerabilidad (una vez desbloqueado) |
| **E** | Interactuar (palancas, santuarios, puertas, recoger llaves, leer inscripciones) |
| **Esc** | Abrir/cerrar menú de pausa |

> Los controles se muestran al jugador desde la pantalla de Instrucciones del menú principal, y también en el primer tutorial dentro del juego.

🔮 **Visión a futuro**: soporte completo de gamepad/mando con stick analógico real, controles reasignables, soporte táctil para versión móvil.

### Dash — Detalles mecánicos

🎯 **Scope del entregable**:

- **Distancia recorrida**: equivalente a 3-4 veces el tamaño del sprite de Lumen (~96 a 128 píxeles aproximados, a ajustar en testing).
- **Duración**: ~150 ms (sensación de impulso corto y rápido).
- **Invulnerabilidad**: durante toda la duración del dash, Lumen no recibe daño y atraviesa enemigos (las colisiones con paredes/obstáculos sí cuentan).
- **Coste de energía**: 5% de la energía máxima por uso.
- **Cooldown**: 1 segundo desde el final del dash anterior.
- **Visual**: estela de luz detrás del personaje durante el dash.

### Sistema de desbloqueo de habilidades — Santuarios

🎯 **Scope del entregable**:

Las habilidades no se obtienen automáticamente al alcanzar cierto punto del juego. El jugador debe **interactuar con un Santuario** específico, y para activarlo necesita haber recogido un número determinado de **Esencias** distribuidos por las salas.

**Esencias**:

- Pequeños cristales fragmentados, visualmente distintos de los cristales de energía (más etéreos, semitransparentes, con animación de flotación).
- Distribución: algunos están en el camino principal (visibles, recompensa por avanzar), otros están escondidos (recompensa por explorar — detrás de palancas opcionales, en salas secundarias, en zonas a las que solo se puede llegar tras resolver algo).
- No tienen otra utilidad que activar santuarios. Una vez recogidos, se quedan en el inventario del jugador.

**Santuarios**:

- Estructura visual: pequeño pedestal de piedra con un cristal apagado en el centro. Al acercarse Lumen, el santuario reacciona visualmente.
- Interacción: el jugador pulsa **E** para activarlo.
  - Si tiene las esencias requeridas: el cristal se enciende, se reproduce un mensaje narrativo breve y la habilidad queda desbloqueada permanentemente. Las esencias requeridas se consumen.
  - Si no los tiene: muestra "Faltan X esencias" y permanece inactivo.
- Mensajes narrativos al activar (ejemplos):
  - Ráfaga de luz: *"Recuerdas… cómo proyectabas tu luz hacia delante."*
  - Dash: *"Recuerdas… que podías moverte como un destello."*
  - Aura: *"Recuerdas… quién eras antes de la oscuridad."*

**Distribución de santuarios y esencias**:

| Habilidad | Santuario en… | Esencias requeridas | Distribución de esencias |
|---|---|---|---|
| Ráfaga de luz | Sala intermedia del Nivel 1 | 2 esencias | Sala 1 (visible) y Sala 2 (oculto tras palanca o sala opcional) |
| Dash con invulnerabilidad | Sala inicial del Nivel 2 | 3 esencias | 1 en sala inicial del Nivel 2, 2 distribuidos en últimas salas del Nivel 1 |
| Aura permanente | Sala intermedia del Nivel 2 | 4 esencias | Distribuidos por todo el Nivel 2 |

> **Importante**: el juego nunca se bloquea por no tener una habilidad. Si el jugador opta por avanzar sin desbloquearla, podrá hacerlo — el desafío simplemente será mayor. Esto preserva la agencia del jugador y permite distintos estilos de juego (explorador completista vs. velocista).

🔮 **Visión a futuro — Modelo de elecciones (árbol de habilidades real)**:

El sistema de Santuarios está pensado para evolucionar a un **sistema de elecciones**. En lugar de desbloquear una versión fija de la habilidad, cada santuario ofrecería dos variantes mutuamente excluyentes:

- **Ráfaga**: Brasa (daño alto, corto alcance) vs. Estrella (daño bajo, largo alcance).
- **Dash**: Destello (corto pero múltiples cargas) vs. Cometa (largo y rápido pero con cooldown alto).
- **Aura**: Hogar (radio pequeño, daño alto) vs. Faro (radio amplio, daño bajo).

Esta evolución añadiría rejugabilidad y una segunda capa de decisión estratégica al jugador. Queda fuera del scope del entregable por su mayor coste de implementación (UI de elección, balanceo de cada variante).

---

## 5. Enemigos y NPCs

### Visión general

🎯 **Scope del entregable**:

El templo está poblado por **criaturas de sombra**, manifestaciones de la oscuridad que han crecido en ausencia de luz. No tienen voluntad propia más allá de un instinto: extinguir a quien ose iluminar el templo. Estéticamente comparten una identidad común — siluetas oscuras planas con ojos brillantes — y se diferencian por comportamiento e IA, no por adornos visuales complejos.

El juego incluye **tres tipos base** de sombras (cubre el requisito obligatorio de 3 NPCs diferentes), **un tipo adicional exclusivo del Nivel 2** (Susurro), y **variantes élite** de los tres tipos base en el Nivel 2.

### Resolución del conflicto contacto/aura

🎯 **Scope del entregable** — **Hitboxes diferenciadas**:

Cada sombra tiene **dos zonas de colisión** distintas:

- **Núcleo** (hitbox interna, pequeña): si Lumen entra en contacto con el núcleo de la sombra, **Lumen recibe daño**.
- **Cuerpo** (hitbox externa, mayor): si esta zona entra en contacto con el aura de Lumen, **la sombra recibe daño**.

Como el aura de Lumen tiene un radio **mayor** que el núcleo de la sombra, Lumen puede dañar a las sombras sin tocar su núcleo. El jugador aprende a "rozar" a las sombras sin entrar en su núcleo. Esto crea tensión táctica: el aura es un arma de proximidad pero arriesgada.

> Este sistema también se aplica a la ráfaga de luz: la ráfaga golpea al cuerpo, no al núcleo, y por tanto puede dañar sin que Lumen reciba daño él mismo si se mantiene fuera del núcleo.

### Tipos de sombras

#### 1. Acechante (patrullero — IA simple)

🎯 **Scope del entregable**:

- **Descripción**: silueta humanoide pequeña, ojos blancos. Se mueve despacio.
- **IA**: máquina de estados de 2 estados.
  - **PATRULLA**: se desplaza entre dos puntos fijos de la sala, de ida y vuelta indefinidamente.
  - **DAÑADO**: si recibe daño, retrocede ligeramente antes de continuar su patrulla.
- **No detecta a Lumen** ni lo persigue. Funciona como un obstáculo móvil predecible.
- **Daño que inflige a Lumen**: bajo.
- **Vida**: baja (1-2 ráfagas para eliminarlo).
- **Función pedagógica**: enseñar al jugador a leer patrones de movimiento y a cronometrar el paso. Es el enemigo introductorio del Nivel 1.

#### 2. Mirón (detector con cono de visión — IA reactiva)

🎯 **Scope del entregable**:

- **Descripción**: silueta más alta, ojos rojos. Permanece casi fijo en su posición y gira lentamente sobre sí mismo.
- **IA**: máquina de estados de 3 estados.
  - **VIGILA**: girando despacio sobre sí mismo. Tiene un **cono de visión** delante de él (sutilmente representado en pantalla). Si Lumen entra en el cono, transita a ALERTA.
  - **ALERTA**: se queda mirando hacia donde detectó a Lumen durante ~1.5 segundos. Si lo sigue viendo, transita a PERSIGUE. Si lo pierde de vista, vuelve a VIGILA.
  - **PERSIGUE**: se desplaza hacia la última posición conocida de Lumen. Si llega allí sin verlo, vuelve a VIGILA.
- **Daño que inflige a Lumen**: medio.
- **Vida**: media (2-3 ráfagas).
- **Función pedagógica**: introduce el sigilo. El jugador aprende a moverse por la espalda del Mirón o a esperar a que se gire.

#### 3. Devorador (cazador agresivo — IA con detección global)

🎯 **Scope del entregable**:

- **Descripción**: silueta más grande y deforme, ojos cyan. Mueve los brazos al desplazarse, dando sensación de masa.
- **IA**: máquina de estados de 3 estados.
  - **DORMIDO**: inmóvil en su posición de salida hasta que Lumen entra en la sala.
  - **DESPIERTO**: al detectar a Lumen (sin cono de visión — lo detecta en toda la sala donde está), se desplaza directamente hacia él intentando interceptarlo. Más rápido que el Acechante y el Mirón.
  - **HERIDO**: si recibe daño, retrocede 1 segundo antes de volver a DESPIERTO.
- **Daño que inflige a Lumen**: alto.
- **Vida**: alta (3-4 ráfagas).
- **Función pedagógica**: enseña al jugador a gestionar la presión y a usar las habilidades activas (ráfaga, dash) en momentos críticos.

#### 4. Susurro (atacante a distancia — exclusivo Nivel 2)

🎯 **Scope del entregable**:

- **Descripción**: silueta etérea, casi transparente. Flota un poco sobre el suelo. Ojos blanquecinos vacíos.
- **IA**: máquina de estados de 3 estados.
  - **FLOTA**: se desplaza despacio en una trayectoria predefinida (línea o lazo) por la sala.
  - **APUNTA**: al detectar a Lumen en línea de visión, se detiene y "carga" durante ~1 segundo (animación visible para que el jugador pueda reaccionar).
  - **DISPARA**: lanza un proyectil de oscuridad lento hacia la última posición conocida de Lumen. Tras disparar, vuelve a FLOTA.
- **Daño que inflige a Lumen**: medio. Pero por **proyectil**, no por contacto.
- **Vida**: baja-media (frágil, pero peligroso a distancia).
- **Función pedagógica**: rompe la regla del Nivel 1 ("estar lejos es estar a salvo"). Obliga al jugador a moverse aunque no haya enemigos cerca.

### Variantes élite (Nivel 2)

🎯 **Scope del entregable**:

Las tres sombras base aparecen también en el Nivel 2 con versiones más potentes. Visualmente se distinguen por un **borde luminoso sutil de color rojizo/violeta** alrededor de la silueta, dando al jugador la pista visual de "este es más peligroso".

| Variante | Diferencia respecto a la versión base |
|---|---|
| **Acechante Élite** | Patrulla más rápido, recorre más distancia entre puntos |
| **Mirón Élite** | Cono de visión más ancho, gira más rápido |
| **Devorador Élite** | Más vida (resiste más ráfagas), igual de rápido pero golpea más fuerte |

> Las variantes élite se implementan reaprovechando la misma clase Java que las versiones base, parametrizando velocidad, vida y daño. Esto es una decisión técnica deliberada de eficiencia y mantenibilidad.

### Resumen de NPCs hostiles

| Tipo | Nivel | Estados de IA | Daño a Lumen | Vida | Detección |
|---|---|---|---|---|---|
| Acechante | 1 y 2 | 2 (Patrulla, Dañado) | Bajo | Baja | No detecta |
| Mirón | 1 y 2 | 3 (Vigila, Alerta, Persigue) | Medio | Media | Cono de visión |
| Devorador | 1 y 2 | 3 (Dormido, Despierto, Herido) | Alto | Alta | Toda la sala |
| Susurro | 2 | 3 (Flota, Apunta, Dispara) | Medio (proyectil) | Baja-Media | Línea de visión |
| Acechante Élite | 2 | igual al base | Bajo | Baja | igual al base |
| Mirón Élite | 2 | igual al base | Medio | Media | Cono más amplio |
| Devorador Élite | 2 | igual al base | Muy Alto | Muy Alta | igual al base |

### Inscripciones (NPCs no hostiles / lore ambiental)

🎯 **Scope del entregable**:

Aparte de los enemigos, el templo contiene **inscripciones**: pequeñas marcas brillantes en algunas paredes que, al ser interactuadas con la tecla **E**, muestran texto narrativo breve. No tienen mecánica de juego ni recompensa: enriquecen el mundo y dan contexto.

- **Distribución**: 3-5 inscripciones por nivel, situadas en zonas tranquilas. Algunas en el camino principal, otras en salas opcionales (premiando la exploración).
- **Visual**: glifo brillante sutil grabado en la pared, distinguible pero no llamativo.
- **Externalización**: los textos de las inscripciones se guardan en un archivo de texto plano (`assets/textos/inscripciones.txt`) para facilitar la edición y la traducción futuras, sin necesidad de tocar código.

**Ejemplos de inscripciones**:

> *"Aquí descansaban los Guardianes de la Luz, antes de la Gran Sombra."*
>
> *"El templo se construyó para contener algo. Nunca para honrarlo."*
>
> *"Hubo otros como tú. No quedaron."*
>
> *"Los que cayeron en las profundidades susurran todavía."*
>
> *"La luz no se pierde. Se olvida."*
>
> *"No mires hacia atrás. Lo que vigila no necesita ojos."*

🔮 **Visión a futuro**: espíritus de luz atrapados que regalan esencias al ser tocados; ecos sonoros de las inscripciones (voz que susurra el texto); inscripciones que cambian según el progreso del jugador.

### Cobertura del enunciado

Esta sección cubre dos puntos del enunciado:

- ✅ **Requisito obligatorio**: *"el juego dispondrá de NPCs que deberán interactuar con el personaje del jugador (al menos deberá haber 3 NPCs diferentes)"*. Cubierto con margen: 3 tipos base (Acechante, Mirón, Devorador) + Susurro + variantes élite + inscripciones interactivas.
- ✅ **Funcionalidad opcional**: *"añadir algún tipo de IA a algún NPC del juego"*. Cubierto con variedad de comportamientos: patrullaje fijo, detección con cono de visión, cazador con detección global, ataque a distancia con carga.

---

## 6. Niveles

### Estructura general

🎯 **Scope del entregable**:

El Capítulo I se compone de **dos niveles** claramente diferenciados, cada uno dividido en **6 salas autocontenidas**. Total: **12 salas**.

| Concepto | Decisión |
|---|---|
| Modelo de cámara | **Cámara fija por sala** (estética retro estilo Zelda original) |
| Resolución lógica del juego | **480 × 270 píxeles** |
| Tamaño de tile estándar | **16 × 16 píxeles** |
| Tamaño estándar de sala | **30 × 17 tiles** (= 480 × 272 píxeles) |
| Tamaño de salas especiales | hasta **40 × 22 tiles** (santuarios y salas finales) |
| Duración objetivo por nivel | ~5 minutos |
| Duración total objetivo | ~10 minutos |

**Razón del modelo cámara fija**: simplifica la implementación (no requiere lerp, ni gestión de límites de mapa), encaja con el género (Zelda original, Binding of Isaac, etc.) y refuerza la sensación de que cada sala es un puzzle cerrado.

**Razón del tamaño 480×270**: es exactamente un cuarto de Full HD (1920×1080). Eso permite escalar cada píxel del juego a un cuadrado de 4×4 píxeles físicos en pantalla, sin distorsión ni antialiasing, conservando la nitidez del pixel art.

**Transición entre salas**: al cruzar la puerta de salida de una sala, fade negro corto (~0.3 segundos) y carga de la siguiente sala.

### Implementación técnica

🎯 **Scope del entregable**:

Las salas se construyen usando el editor externo **Tiled** (formato `.tmx`) y se cargan en libGDX mediante el módulo `gdx-tiled`. Esto permite:

- Diseñar mapas visualmente sin tocar código.
- Modificar/iterar salas sin recompilar.
- Marcar objetos (puertas, enemigos, esencias, palancas) en capas de objetos de Tiled, leídos en código.

> Este uso de Tiled cubre parcialmente la filosofía de "generador de niveles externos" (aunque no figura como funcionalidad opcional formal en este entregable), añadiendo flexibilidad al desarrollo.

---

### 🌑 Nivel 1 — Catacumbas profundas

> Acentos azulados y violetas. Salas pequeñas y pasillos estrechos. Ambiente claustrofóbico, mineral, húmedo. Sonido de goteo y ecos.

#### N1-S1 — Despertar

- **Tema**: Lumen despierta en lo más profundo del templo. Tutorial implícito de movimiento.
- **Puzzle**: la puerta de salida está bloqueada por un brasero apagado. Lumen lo enciende **simplemente acercándose** — primera lección del juego: "la luz que soy enciende lo que toca". Al encenderlo, la puerta se abre.
- **Enemigos**: ninguno.
- **Recompensas**: 1 cristal de energía (familiariza al jugador con recogerlo).
- **Inscripción**: *"Aquí descansaban los Guardianes de la Luz, antes de la Gran Sombra."*
- **Salida**: → N1-S2

#### N1-S2 — La primera sombra

- **Tema**: Tutorial implícito de enemigos.
- **Puzzle**: hay dos braseros (entrada y fondo). Ambos deben encenderse para abrir la salida. Esto obliga al jugador a cruzar la sala, volver, y volver a cruzar.
- **Enemigos**: 1 Acechante patrullando de un lado a otro entre los dos braseros.
- **Recompensas**: 1 **Esencia** visible cerca del segundo brasero.
- **Salida**: → N1-S3

#### N1-S3 — La palanca olvidada

- **Tema**: Tutorial de palancas y puzzle simple.
- **Puzzle**: la puerta de salida está cerrada. Hay 1 palanca en una esquina de la sala que la abre. Activar la palanca también revela una pequeña pared movible que oculta una esencia.
- **Enemigos**: 2 Acechantes (uno protege la palanca, otro protege la puerta).
- **Recompensas**: 1 **Esencia** oculta (revelado tras activar palanca).
- **Salida**: → N1-S4

#### N1-S4 — El primer Santuario ⭐

- **Tema**: **Sala del Santuario de la Ráfaga**. Hito del Nivel 1.
- **Puzzle**: el santuario está protegido por **tres losas con runas** que deben estar iluminadas simultáneamente. Cada losa se ilumina al ser pisada y mantiene su iluminación durante 3 segundos antes de apagarse. Lumen debe correr en zigzag para mantener las tres "calientes" a la vez. Una vez sincronizadas, el santuario se vuelve activable.
- **Activación del santuario**: tras resolver el puzzle de las losas, Lumen interactúa con `E` sobre el santuario. Si tiene 2 esencias, se desbloquea la **Ráfaga de luz** con el mensaje *"Recuerdas… cómo proyectabas tu luz hacia delante."* Las 2 esencias se consumen.
- **Enemigos**: 1 Mirón en una esquina añadiendo presión visual (puede evitarse).
- **Recompensas**: el desbloqueo de la habilidad.
- **Inscripción**: *"La luz no se pierde. Se olvida."*
- **Salida**: → N1-S5

#### N1-S5 — Las antorchas dormidas

- **Tema**: Puzzle que requiere la Ráfaga recién obtenida.
- **Puzzle**: 3 antorchas dispuestas en triángulo, todas apagadas. Hay que encenderlas todas usando la ráfaga (proyectil de luz). Cuando las tres están encendidas, se abre la puerta. El jugador descubre que la ráfaga sirve para más que combatir.
- **Enemigos**: 1 Mirón patrullando entre dos antorchas, 1 Devorador dormido junto a la tercera (despierta al acercarse — introducción al Devorador).
- **Recompensas**: 1 **Esencia** detrás de la última antorcha encendida.
- **Salida**: → N1-S6

#### N1-S6 — La salida de las catacumbas

- **Tema**: Sala compleja combinando varios elementos. Cierre del Nivel 1.
- **Puzzle**: hay una puerta cerrada con llave. La llave está custodiada por un Devorador. Tras obtenerla, hay que llevarla hasta la puerta esquivando o eliminando al Devorador y al Mirón.
- **Enemigos**: 1 Devorador (dormido inicialmente), 1 Mirón patrullando.
- **Recompensas**: 1 cristal de energía cerca de la puerta. 1 **Esencia** en zona opcional de mayor riesgo (camino arriesgado para completistas).
- **Inscripción**: *"Hubo otros como tú. No quedaron."*
- **Salida**: → transición al Nivel 2 (fade negro largo + transición narrativa).

**Esencias disponibles en Nivel 1**: 5 (2 necesarios para Ráfaga + 3 que se llevan al Nivel 2).

---

### 🌅 Nivel 2 — Templo superior

> Acentos dorados y ámbar. Salas amplias con columnas y vestíbulos. Estética ritual. Sonido de viento que se cuela y hojas distantes. Algunas grietas dejan entrar luz natural.

#### N2-S1 — Sala del Dash ⭐

- **Tema**: **Sala del Santuario del Dash**. Bienvenida al Nivel 2.
- **Puzzle**: el santuario está protegido por una **barrera de oscuridad**. Hay una antorcha encendida cerca. Lumen debe **apagarla** (manteniéndose junto a ella varios segundos, o golpeándola con ráfaga) para que la barrera caiga. Mecánica complementaria: en este templo, a veces hay que apagar la luz para revelar el camino.
- **Activación del santuario**: tras apagar la antorcha, interactuar con `E`. Si tiene 3 esencias, se desbloquea el **Dash** con el mensaje *"Recuerdas… que podías moverte como un destello."*
- **Enemigos**: ninguno. Sala de respiro.
- **Recompensas**: el desbloqueo + 1 cristal de energía.
- **Salida**: → N2-S2

#### N2-S2 — El patio de los pilares

- **Tema**: Aprender el Dash con presión moderada. Introducción al Susurro.
- **Puzzle**: la puerta de salida está cerrada y muestra un símbolo. Para abrirla, hay que **eliminar a todos los Susurros** de la sala (combat puzzle). Los pilares centrales sirven como cobertura ante los proyectiles.
- **Enemigos**: 1 Acechante Élite patrullando entre pilares, 2 Susurros flotando en lados opuestos.
- **Recompensas**: 1 **Esencia** en un rincón vigilado por uno de los Susurros.
- **Salida**: → N2-S3

#### N2-S3 — Las plataformas rituales

- **Tema**: Puzzle de plataformas de presión con elemento físico.
- **Puzzle**: 2 plataformas de presión deben estar activadas simultáneamente para abrir la puerta. Una se activa pisándola (Lumen). La otra requiere empujar un bloque encima (al rozar el bloque, Lumen lo empuja en su dirección de movimiento — mecánica nueva pero intuitiva).
- **Enemigos**: 1 Mirón Élite en el centro vigilando ambas plataformas, 1 Acechante Élite patrullando cerca del bloque.
- **Recompensas**: 1 **Esencia** en una alcoba lateral.
- **Inscripción**: *"El templo se construyó para contener algo. Nunca para honrarlo."*
- **Salida**: → N2-S4

#### N2-S4 — El Santuario del Aura ⭐

- **Tema**: **Sala del Santuario del Aura**. Puzzle único de manipulación enemiga.
- **Puzzle**: el santuario está sobre una **plataforma circular central** que solo se activa cuando los **dos Devoradores Élite** están sobre dos plataformas de presión específicas marcadas en el suelo. El jugador debe **guiar a los Devoradores** (que persiguen a Lumen al detectarlo) hacia esas plataformas sin que lo toquen. Usa la propia IA del enemigo como herramienta de puzzle.
- **Activación del santuario**: una vez activada la plataforma central, interactuar con `E`. Si tiene 4 esencias, se desbloquea el **Aura permanente** con el mensaje *"Recuerdas… quién eras antes de la oscuridad."* A partir de este momento, el aura empieza a funcionar.
- **Enemigos**: 2 Devoradores Élite.
- **Recompensas**: el desbloqueo + 2 cristales de energía cerca del santuario.
- **Salida**: → N2-S5

#### N2-S5 — La cripta de las antorchas y los susurros

- **Tema**: Sala de máxima dificultad. Combina todo lo aprendido.
- **Puzzle**: encender 4 antorchas **en un orden específico**, indicado por una inscripción al entrar (ej. *"Norte, Este, Sur, Oeste"*). Si se encienden en orden equivocado, todas se apagan y hay que empezar de cero. Requiere planificación + gestionar la presión de los enemigos simultáneamente.
- **Enemigos**: 2 Susurros flotando en posiciones que dificultan moverse en línea recta, varios Acechantes y un Mirón mixtos.
- **Recompensas**: 1 **Esencia** opcional (ya no sirve para desbloquear nada — recompensa simbólica de exploración).
- **Inscripción**: *"Los que cayeron en las profundidades susurran todavía."*
- **Salida**: → N2-S6

#### N2-S6 — La ascensión 🏁

- **Tema**: Sala final. Conclusión narrativa.
- **Puzzle**: no hay puzzle mecánico. Hay que cruzar la sala hasta la grieta de luz natural al fondo, mientras se enfrenta a la última oleada de enemigos. La luz natural va siendo cada vez más visible conforme se avanza.
- **Enemigos**: oleada final mixta — Devoradores Élite y Susurros patrullando la sala.
- **Inscripciones finales (3, opcionales)**: pequeñas inscripciones en el camino que cuentan el final del lore. Quien se detiene a leerlas se arriesga, quien corre llega antes. Ejemplos:
  > *"Tú no fuiste el primero en ascender."*
  >
  > *"No hay luz sin sombra que la espere."*
  >
  > *"Vuelve. Vuelve antes de que ella despierte del todo."*
- **Al alcanzar la grieta**: se activa la **pantalla de victoria final** con el texto narrativo del "Continuará..." (con efecto typewriter).
- **Salida**: → pantalla de victoria final → menú principal.

**Esencias disponibles en Nivel 2**: 3 (necesarios 1 adicional con los 3 traídos del Nivel 1 para activar el Santuario del Aura, que requiere 4).

---

### Tabla resumen del juego completo

| Sala | Tema | Puzzle | Enemigos | Frag. | Hito |
|---|---|---|---|---|---|
| **N1-S1** | Despertar | Encender brasero al rozar | — | — | 🌑 Inicio Capítulo I |
| **N1-S2** | Primera sombra | Encender 2 braseros | 1 Acechante | 1 | — |
| **N1-S3** | Palanca olvidada | Palanca + revelar esencia | 2 Acechantes | 1 | — |
| **N1-S4** | Santuario Ráfaga | Losas con memoria + activar santuario | 1 Mirón | — | ⭐ Desbloqueo Ráfaga |
| **N1-S5** | Antorchas dormidas | Encender 3 antorchas con ráfaga | 1 Mirón, 1 Devorador | 1 | — |
| **N1-S6** | Salida catacumbas | Llave + puerta | 1 Devorador, 1 Mirón | 1 | Fin Nivel 1 |
| **N2-S1** | Sala del Dash | Apagar antorcha + activar santuario | — | — | ⭐ Desbloqueo Dash |
| **N2-S2** | Patio de los pilares | Combat puzzle (matar Susurros) | 1 Acech. Élite, 2 Susurros | 1 | — |
| **N2-S3** | Plataformas rituales | 2 plataformas presión (Lumen + bloque) | 1 Mirón Élite, 1 Acech. Élite | 1 | — |
| **N2-S4** | Santuario del Aura | Guiar Devoradores a plataformas | 2 Devor. Élite | — | ⭐ Desbloqueo Aura |
| **N2-S5** | Cripta antorchas | 4 antorchas en orden | 2 Susurros + mixtos | 1 | — |
| **N2-S6** | La Ascensión | Cruzar oleada + inscripciones finales | Devor. Élite + Susurros | — | 🏁 Victoria final |

### Curva de dificultad

🎯 **Scope del entregable**:

La dificultad sigue una curva ascendente con pausas calculadas:

- **N1-S1, N1-S2**: tutorial implícito. Baja dificultad.
- **N1-S3, N1-S4**: introducción de elementos. Dificultad baja-media.
- **N1-S5**: aplicación de habilidad nueva. Dificultad media.
- **N1-S6**: cierre del Nivel 1 con combinación. Dificultad media-alta.
- **N2-S1**: respiro narrativo. Dificultad baja.
- **N2-S2, N2-S3**: aplicación del Dash con enemigos más rápidos. Dificultad alta.
- **N2-S4**: puzzle único + combate. Dificultad alta.
- **N2-S5**: clímax mecánico. Dificultad muy alta.
- **N2-S6**: clímax narrativo. Dificultad media-alta (la tensión proviene de la presión narrativa, no del puzzle).

🔮 **Visión a futuro**: niveles adicionales del Capítulo II (jungla exterior, cumbre del templo bajo el sol), salas opcionales, dificultades seleccionables (Normal / Difícil / Solo luz), niveles generados aleatoriamente combinando salas predefinidas.

### Cobertura del enunciado

- ✅ **Requisito obligatorio**: *"Preparar, al menos, dos niveles claramente diferenciados"*. Cubierto con dos niveles temática y mecánicamente distintos.
- ✅ **Requisito obligatorio**: *"inicio, final y un objetivo claro"*. Inicio: despertar en N1-S1. Final: ascensión en N2-S6. Objetivo: llegar a la superficie.

---

## 7. HUD e información en pantalla

### Filosofía del HUD

🎯 **Scope del entregable**:

El HUD (Heads-Up Display) sigue una filosofía minimalista: **información esencial en las esquinas, centro de pantalla limpio para la acción**. La estética visual del HUD es coherente con el resto del juego: pixel art, alto contraste, paleta adaptativa según el nivel.

### Disposición general

```
┌─────────────────────────────────────────────┐
│ [♦♦♦◆__]  N1-S3       Puntos: 1240          │
│                       Esencias: 3            │
│                       02:14                  │
│                                              │
│                                              │
│              [zona de juego]                 │
│                                              │
│                                              │
│                                              │
│                              [⚡] [☀] [✦]    │
└─────────────────────────────────────────────┘
```

| Posición | Elemento |
|---|---|
| Esquina superior izquierda | Barra de energía + nivel/sala actual |
| Esquina superior derecha | Puntos + Esencias + Tiempo de partida |
| Esquina inferior derecha | Iconos de habilidades desbloqueadas y su estado |

### Elementos permanentes del HUD

#### 1. Barra de energía

🎯 **Scope del entregable**:

- **Forma**: barra horizontal **segmentada en 10 cristales**. Cada cristal representa el 10% de la energía total.
- **Comportamiento visual**:
  - Cristales llenos: color identificativo del personaje seleccionado (por defecto, blanco-azulado).
  - Cristales vacíos: marco apagado/oscuro.
  - Cuando la energía baja del 30%: parpadeo rojizo sutil en los cristales restantes.
  - Cuando la energía baja del 20%: viñeta roja/violeta sutil en los bordes de la pantalla (sin tapar la acción).
- **Tamaño aproximado**: 80×10 píxeles.

#### 2. Nivel y sala actual

🎯 **Scope del entregable**:

- **Formato**: `N1-S3` (Nivel 1, Sala 3).
- **Función**: el jugador siempre sabe dónde está. Cubre el requisito obligatorio de "nivel actual".
- **Actualización**: cambia automáticamente al transitar entre salas.

#### 3. Puntos (puntuación)

🎯 **Scope del entregable**:

- **Formato**: `Puntos: 1240`.
- **Animación al sumar**: breve "pop" del número (escala +20% durante 0.2s). Opcionalmente, texto flotante "+50" sobre el enemigo derrotado u objeto recogido.
- **Cubre el requisito obligatorio de "puntuación"**.

#### 4. Esencias

🎯 **Scope del entregable**:

- **Formato**: `Esencias: 3` con un pequeño icono al lado.
- **Animación al recoger**: flash blanco breve + número pulsa.
- **Función**: moneda de progresión que se gasta en santuarios.

#### 5. Tiempo de partida

🎯 **Scope del entregable**:

- **Formato**: `02:14` (minutos:segundos) — cuenta hacia arriba.
- **Función**: cuenta el tiempo transcurrido en el nivel actual. Al terminar el nivel, el tiempo restante respecto al objetivo se convierte en puntos extra (ver "Sistema de puntuación" más abajo).

#### 6. Iconos de habilidades (esquina inferior derecha)

🎯 **Scope del entregable**:

Tres iconos pequeños, uno por habilidad activa:

| Habilidad | Estado bloqueada | Estado disponible | Estado en cooldown |
|---|---|---|---|
| Ráfaga (⚡) | Icono apagado/gris muy oscuro | Encendido en color identificativo | Semi-oscurecido con relleno radial |
| Dash (☀) | Apagado | Encendido | Semi-oscurecido con relleno radial |
| Aura (✦) | Apagado | Encendido (siempre, pasivo) | (No tiene cooldown) |

**Progresión del HUD**: los iconos aparecen visibles desde el inicio pero en estado bloqueado. A medida que el jugador desbloquea habilidades en los santuarios, los iconos correspondientes se encienden. Esto da feedback visual del progreso del personaje.

### Elementos contextuales del HUD

🎯 **Scope del entregable**:

Aparecen solo cuando son relevantes, no son permanentes:

- **Cuadros de diálogo / inscripciones**: caja rectangular semi-transparente en la parte inferior central. Texto con efecto **typewriter** (letras apareciendo una a una). Aparece al interactuar con inscripciones, santuarios o eventos narrativos. Se cierra con `E` o automáticamente tras unos segundos.

- **Indicador de interacción**: pequeño icono `[E]` flotando sobre el objeto interactuable más cercano cuando Lumen está a rango. Aparece sobre palancas, santuarios, antorchas, llaves, inscripciones y puertas.

- **Notificación de objeto recogido**: mensaje flotante temporal en la esquina superior derecha. Ej. *"Has obtenido una Esencia"*, *"Llave de bronce obtenida"*. Aparece 2 segundos y se desvanece.

- **Texto flotante de puntos**: al recibir puntos por una acción concreta (matar enemigo, recoger cristal, leer inscripción), aparece brevemente un `+25` sobre el lugar de la acción, ascendiendo y desvaneciéndose.

### Paleta del HUD según el nivel

🎯 **Scope del entregable**:

El HUD usa una paleta que se adapta al nivel actual, reforzando la identidad visual:

- **Nivel 1 (Catacumbas)**: acentos azulados/violetas en bordes y números.
- **Nivel 2 (Templo superior)**: acentos dorados/ámbar en bordes y números.

La estructura del HUD no cambia, solo los colores. Esto da continuidad sin monotonía.

### Sistema de puntuación

🎯 **Scope del entregable**:

El juego incluye **dos recursos paralelos** que cubren funciones distintas:

| Recurso | Función | Se gasta | Aparece en HUD como |
|---|---|---|---|
| **Puntos** | Récord global. Top 10. Comparar partidas. | No, solo sube. | `Puntos: 1240` |
| **Esencias** | Moneda de progresión: se gasta en santuarios para desbloquear habilidades. | Sí, en los santuarios. | `Esencias: 3` |

Esta separación permite premiar **dos estilos de juego distintos sin penalizar ninguno**:
- El **velocista** acumula muchos puntos por completar nivels rápido (gracias al bonus de tiempo).
- El **explorador** acumula muchas esencias y desbloquea todas las habilidades.

Y permite que un jugador combine ambos enfoques o se especialice en uno.

#### Tabla de puntos

🎯 **Scope del entregable**:

| Acción | Puntos otorgados |
|---|---|
| Eliminar Acechante | +20 |
| Eliminar Mirón | +40 |
| Eliminar Devorador | +80 |
| Eliminar Susurro | +50 |
| Eliminar Acechante Élite | +30 |
| Eliminar Mirón Élite | +60 |
| Eliminar Devorador Élite | +120 |
| Recoger cristal de luz | +10 |
| Recoger esencia | +25 (además de ganar 1 esencia) |
| Leer inscripción | +25 |
| Completar sala | +200 |
| Completar sala sin recibir daño | +50 adicionales |
| Completar nivel | +1000 |
| Bonus de tiempo restante | +10 por segundo de tiempo restante |

> Estos valores son un punto de partida orientativo. Se ajustarán en testing para conseguir el balance deseado entre estilos de juego.

#### Tiempo objetivo por nivel y bonus de tiempo

🎯 **Scope del entregable**:

Cada nivel tiene un **tiempo objetivo**. Al terminar el nivel, se calcula el tiempo restante respecto al objetivo y se convierten en puntos:

```
tiempo_restante = tiempo_objetivo - tiempo_real
si tiempo_restante > 0:
    bonus = tiempo_restante * 10
si no:
    bonus = 0   (sin penalización, simplemente sin bonus)
```

| Nivel | Tiempo objetivo |
|---|---|
| Nivel 1 — Catacumbas profundas | 5 minutos (300 segundos) |
| Nivel 2 — Templo superior | 6 minutos (360 segundos) |

**Ejemplo de cálculo**: completar el Nivel 1 en 3 minutos 30 segundos → tiempo restante = 90 segundos → bonus = **+900 puntos**.

#### Costes de los santuarios (en esencias)

🎯 **Scope del entregable**:

| Santuario | Coste | Esencias disponibles antes del santuario |
|---|---|---|
| Ráfaga (N1-S4) | 2 esencias | 2 (N1-S2 + N1-S3) — justas |
| Dash (N2-S1) | 3 esencias | 3 esencias restantes del N1 — justas |
| Aura (N2-S4) | 4 esencias | 3 del N1 + 3 del inicio N2 = hasta 6 — margen |

> Las cantidades están calibradas para que el jugador deba **explorar** y **recoger todas las esencias del camino** para desbloquear las primeras habilidades. La última (Aura) deja algo de margen para que el jugador pueda saltarse alguna esencia escondida.

### Pantallas auxiliares (resumen)

🎯 **Scope del entregable**:

Aunque no son HUD en sentido estricto, completamos aquí el inventario de pantallas que verá el jugador en ejecución:

- **Pantalla de pausa** (Esc): superposición semi-transparente sobre el juego. Botones: Reanudar, Activar/Desactivar sonido, Menú principal, Salir. Detallado en la funcionalidad opcional correspondiente (sección 10).
- **Pantalla de fin de sala / nivel**: breve resumen con animación de "conteo" arcade:
  ```
  Nivel 1 completado
  ─────────────────
  Tiempo: 3:30
  Bonus de tiempo: +900 pts
  Esencias recogidas: 3
  Sin daño en X salas: +150 pts

  Total del nivel: 1450 pts
  Total acumulado: 4290 pts
  ```
  Cada línea aparece con pequeño delay, número final con flash. Sensación arcade clásica.
- **Pantalla de Game Over**: aparece al llegar a 0 de energía. Muestra "Lumen se ha extinguido", puntos finales acumulados, y botones "Reintentar nivel" / "Volver al menú principal".
- **Pantalla de victoria final**: aparece al completar el Nivel 2. Muestra el texto narrativo del *"Continuará..."* con efecto typewriter. Botones: "Ver puntuación final", "Volver al menú".

### Cobertura del enunciado

- ✅ **Requisito obligatorio**: *"El videojuego mostrará información en pantalla al usuario (puntuación, energía, nivel actual) y ésta se actualizará cuando corresponda"*. Cubierto y ampliado con esencias, tiempo, habilidades.
- ✅ **Funcionalidad opcional**: *"Al finalizar la partida se almacenará la puntuación del jugador (con su nombre) y se mostrará el top 10 puntuaciones"*. El sistema de puntos diseñado en esta sección es la base directa de esta funcionalidad (su almacenamiento se detalla en la sección 10).

🔮 **Visión a futuro**: HUD reasignable por el jugador, modo "minimal HUD" para puristas, minimapa de la sala, animaciones más elaboradas al desbloquear habilidades, sistema de logros/medallas, comparación con récords mundiales online.

---

## 8. Sistema de progresión

### Nota sobre esta sección

Esta sección actúa como **paraguas** que pone juntos todos los sistemas que miden el progreso del jugador. Muchos elementos ya están descritos en secciones anteriores y aquí simplemente se referencian; solo se detallan en profundidad los aspectos nuevos (vidas, Top 10, persistencia, logros).

### Sistemas referenciados

🎯 **Scope del entregable**:

| Sistema | Sección donde se define |
|---|---|
| Sistema de puntos | Sección 7 — HUD |
| Sistema de esencias | Secciones 4 y 7 |
| Bonus de tiempo | Sección 7 |
| Habilidades como progresión | Sección 4 |
| Condiciones de victoria / derrota | Sección 3 |

### Sistema de vidas y muerte

🎯 **Scope del entregable** — **Una sola vida por intento de nivel**:

- Si la energía de Lumen llega a **0**, se activa la pantalla de Game Over.
- Al pulsar **"Reintentar nivel"** en Game Over, el jugador vuelve al **inicio del nivel actual** (no del juego entero).
- Al reintentar:
  - **Se conservan**: las habilidades que ya estaban desbloqueadas antes de empezar ese nivel (los santuarios activados en niveles anteriores siguen activados).
  - **Se reinician**: la posición, la energía al 100%, las esencias recogidas durante el nivel fallido (pero no las gastadas en santuarios activados — esas se mantienen), los puntos ganados durante el nivel fallido, las salas de ese nivel.
- Si el jugador pulsa **"Volver al menú principal"**, la partida se descarta entera. El progreso no se guarda automáticamente — para conservar entre sesiones está el sistema de Guardar/Cargar (funcionalidad opcional, sección 10).

**Razón de diseño**: una sola vida casa con la narrativa ("Lumen se extingue") y con el formato de partidas cortas (10 minutos totales). Tener varias vidas o checkpoints rompería la tensión y exigiría sistemas adicionales que no aportan al espíritu del juego.

🔮 **Visión a futuro**: modo "espíritu vinculado" — segundo personaje que continúa donde Lumen se apagó, con energía reducida y dificultad mayor (estilo Roguelike-light).

### Top 10 de puntuaciones — Récords de Campeones

🎯 **Scope del entregable** — **Funcionalidad opcional**:

El juego mantiene una tabla de **Récords de Campeones** que registra las 10 mejores puntuaciones obtenidas por **jugadores que han completado el juego entero** (alcanzando el final del Nivel 2).

#### Condiciones para entrar en el ranking

- Solo se registra la partida si el jugador llega a la **pantalla de victoria final** (final del Nivel 2).
- Si la partida termina por Game Over, **no se registra** (mantiene el ranking como un logro prestigioso de "haber terminado el juego").
- La puntuación final que se registra es: `puntos acumulados + bonus de tiempo de ambos niveles + bonus de "sin daño" + bonus final por completar el juego`.

#### Datos almacenados por entrada

Cada entrada del Top 10 guarda:

- **Nombre del jugador** (hasta 10 caracteres).
- **Puntuación final** (entero).
- **Tiempo total** de la partida (minutos:segundos).
- **Personaje usado** (uno de los tres seleccionables — ver sección 9).
- **Fecha** de la partida (DD/MM/AAAA).

#### Introducción del nombre

🎯 **Scope del entregable**:

- Al alcanzar la pantalla de victoria final, si la puntuación entra en el Top 10, aparece un cuadro de diálogo pidiendo el nombre.
- **Formato**: campo de texto libre de hasta 10 caracteres alfanuméricos.
- **Memoria**: se recuerda el último nombre introducido y se prerrellena el campo en futuras partidas. El jugador puede editarlo si quiere.
- **Implementación técnica**: componente `TextField` de Scene2D para el input, `Preferences` de libGDX para guardar el último nombre usado.

#### Pantalla de Récords

🎯 **Scope del entregable**:

Accesible desde el menú principal mediante un botón "Récords". Muestra:

```
─── RÉCORDS DE CAMPEONES ────────────────────────────────

  POS  NOMBRE        PUNTOS   TIEMPO   PERSONAJE   FECHA
  ─────────────────────────────────────────────────────
   1   DavidG         8420     09:23   Luminis    23/05/2026
   2   María          7890     11:05   Velis      18/05/2026
   3   ...
   ...

─────────────────────────────────────────────────────────
                       [ Volver ]
```

Si todavía no hay récords (primera vez que se ejecuta el juego), se muestra un mensaje:

> *"Nadie ha completado el Capítulo I todavía. ¿Serás el primero?"*

#### Persistencia del ranking

🎯 **Scope del entregable**:

El Top 10 se guarda en un archivo persistente local mediante `Preferences` de libGDX (clave-valor) o en un fichero JSON, almacenado en la ubicación estándar del sistema operativo (`~/.prefs/lumen.json` o equivalente). Esto sobrevive a:

- Cierres del juego.
- Reinicios del ordenador.
- Reinstalaciones del juego (siempre que no se borre la carpeta de preferencias).

El archivo es **legible y editable manualmente** por el usuario si quisiera (no se ofusca). Es un detalle deliberado: para un juego académico tiene sentido permitir la inspección.

🔮 **Visión a futuro**: rankings online sincronizados con un servidor remoto; rankings por categoría (Speedrun, 100%, Sin daño); compartir partidas con otros jugadores; rankings filtrados por personaje usado.

### Persistencia entre partidas

🎯 **Scope del entregable**:

| Datos | Cuándo se guardan | Dónde se guardan |
|---|---|---|
| Configuración (volumen, opciones) | Al modificar | `Preferences` de libGDX |
| Último nombre usado | Al introducir uno nuevo | `Preferences` |
| Récords del Top 10 | Al completar el juego con récord | `Preferences` o JSON |
| Partida guardada (Guardar/Cargar) | Al pulsar "Guardar partida" | Archivo JSON dedicado |

> El sistema de Guardar/Cargar es una funcionalidad opcional cubierta en la sección 10 (Arquitectura técnica) junto con sus detalles de implementación.

### Logros internos

🔮 **Visión a futuro**:

Sistema de "logros" o "medallas" no incluidos en el scope del entregable pero diseñados aquí para mostrar visión de producto:

- **"Despertar"** — Completar el Nivel 1.
- **"Ascensión"** — Completar el Capítulo I.
- **"Sigiloso"** — Completar un nivel sin ser detectado por ningún Mirón.
- **"Pacifista"** — Completar el Capítulo I sin eliminar a ningún enemigo.
- **"Cazador"** — Eliminar a todos los enemigos del Capítulo I.
- **"Coleccionista"** — Recoger todas las esencias del Capítulo I.
- **"Lector"** — Leer todas las inscripciones del Capítulo I.
- **"Velocista"** — Completar el Capítulo I en menos de 8 minutos.
- **"Espíritu Puro"** — Completar el Capítulo I sin perder energía por daño (solo por tiempo).
- **"Memorias completas"** — Activar los tres santuarios.

Cada logro tendría:
- Icono pixel art representativo.
- Nombre y descripción.
- Fecha de obtención.
- Pantalla de "Logros" accesible desde el menú.

El sistema requeriría persistencia adicional (qué logros tiene desbloqueados cada perfil) y UI dedicada. Queda fuera del scope académico por coste de implementación, pero la arquitectura del juego se diseña para poder añadirlo sin refactorizar (sección 10).

### Cobertura del enunciado

- ✅ **Funcionalidad opcional**: *"Al finalizar la partida se almacenará la puntuación del jugador (con su nombre) y se mostrará el top 10 puntuaciones"*. Cubierto con el sistema de Récords de Campeones detallado en esta sección.
- ✅ **Condiciones de inicio/final/objetivo claro** del juego: cubierto entre las secciones 3 y 8 (concepto, mecánicas, condiciones de victoria/derrota, sistema de muerte).

---

## 9. Personajes seleccionables

### Concepto

🎯 **Scope del entregable** — **Funcionalidad opcional**:

El jugador puede elegir entre **tres espíritus de luz** distintos al iniciar una nueva partida. No son skins cosméticas: cada personaje tiene **estadísticas y mecánicas únicas** que premian estilos de juego completamente distintos.

Narrativamente, el jugador no elige "a Lumen" como tal, sino **qué esencia ancestral** lo acompañará en la ascensión. Cada esencia representa una manifestación distinta de la luz: equilibrada, voraz o sigilosa.

### Los tres espíritus

#### 🤍 LUMINIS — El Heredero

> *"Equilibrado, sereno, el primer espíritu que despertó. Para los que buscan el camino antes que la prisa."*

- **Color identificativo**: blanco-azulado.
- **Personalidad mecánica**: equilibrado en todo. Sin debilidades ni fortalezas marcadas. Es el punto de partida del juego y la referencia para los demás personajes.
- **Estilo de juego que premia**: descubrir el juego, primera partida, jugadores que aún no saben qué les va a gustar más.
- **Es el personaje preseleccionado** por defecto la primera vez que se ejecuta el juego.

**Stats base (referencia para los otros dos)**:

| Atributo | Valor |
|---|---|
| Energía máxima | 100 (referencia) |
| Velocidad de movimiento | 100 (referencia) |
| Daño de la ráfaga | 100 (referencia) |
| Radio del aura | 100 (referencia) |
| Daño del aura (por segundo) | 100 (referencia) |
| Cooldown del dash | 100 (referencia) |
| Energía consumida por habilidades | 100 (referencia) |
| Consumo pasivo de energía por tiempo | 100 (referencia) |
| Sigilo (detección por Mirones) | 100 (referencia) |

#### 🔥 IGNIS — La Llama Voraz

> *"Veloz e implacable, pero se consume con rapidez. Para los que arden brillante y breve."*

- **Color identificativo**: rojo-anaranjado.
- **Personalidad mecánica**: el "glass cannon" — pega fuerte y se mueve rápido, pero es frágil y se consume rápido.
- **Estilo de juego que premia**: combate directo, speedrun, asumir riesgos, completar el juego en el menor tiempo posible.

**Stats (vs Luminis)**:

| Atributo | % | Efecto |
|---|---|---|
| Energía máxima | 80% | Menos resistencia al daño |
| Velocidad de movimiento | 115% | Más rápido por el mapa |
| Daño de la ráfaga | 140% | Golpea muy fuerte |
| Radio del aura | 100% | Igual |
| Daño del aura | 130% | Aura más letal |
| Cooldown del dash | 80% | Puede dashear más a menudo |
| Energía consumida por habilidades | 120% | Cada uso cuesta más |
| Consumo pasivo por tiempo | 120% | Se apaga antes incluso quieto |

#### 💜 AETHEN — El Sigiloso

> *"Resistente y paciente, casi una sombra entre sombras. Para los que prefieren observar antes que actuar."*

- **Color identificativo**: violeta apagado.
- **Personalidad mecánica**: el "tank explorador" — duradero, pero lento y débil. Premia el sigilo y la paciencia.
- **Estilo de juego que premia**: exploración paciente, leer todas las inscripciones, recoger todas las esencias, esquivar enemigos sin combatir.

**Stats (vs Luminis)**:

| Atributo | % | Efecto |
|---|---|---|
| Energía máxima | 130% | Mucho más resistente al daño |
| Velocidad de movimiento | 85% | Más lento |
| Daño de la ráfaga | 70% | Poco daño |
| Radio del aura | 85% | Aura más pequeña |
| Daño del aura | 70% | Aura poco letal |
| Cooldown del dash | 130% | Más espera entre dashes |
| Energía consumida por habilidades | 80% | Cada uso cuesta menos |
| Consumo pasivo por tiempo | 70% | Energía se consume despacio |
| Sigilo | 120% | Los Mirones tardan 20% más en detectarlo |

> **Mecánica única de Aethen**: el sigilo no es solo numérico, es funcional. Su luz es más tenue, lo que se traduce mecánicamente en que los Mirones requieren más tiempo en estado ALERTA antes de transitar a PERSIGUE. Esto es una diferencia **cualitativa**, no solo cuantitativa, entre personajes.

### Estrategias emergentes

🎯 **Scope del entregable**:

Cada personaje rompe el juego en una dirección distinta, lo que da rejugabilidad real:

| Estilo | Personaje recomendado | Cómo maximiza puntos |
|---|---|---|
| Speedrun puro | **Ignis** | Bonus de tiempo enorme + combate eficiente |
| Completista exploración | **Aethen** | Inscripciones todas leídas + todas las esencias |
| Equilibrado / Primera partida | **Luminis** | Mix de todo |

Tres jugadores con personajes distintos tendrán partidas estructuralmente diferentes, no solo numéricamente diferentes.

### Pantalla de selección de personaje

🎯 **Scope del entregable**:

Aparece tras pulsar "Nueva partida" en el menú principal y antes de iniciar el Nivel 1. Tiene un tono **narrativo**, no de "menú de opciones".

```
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│                  ELIGE TU ESENCIA                            │
│                                                              │
│     ╔════════════╗  ╔════════════╗  ╔════════════╗          │
│     ║            ║  ║            ║  ║            ║          │
│     ║   [Sprite  ║  ║   [Sprite  ║  ║   [Sprite  ║          │
│     ║   animado] ║  ║   animado] ║  ║   animado] ║          │
│     ║            ║  ║            ║  ║            ║          │
│     ║   LUMINIS  ║  ║   IGNIS    ║  ║   AETHEN   ║          │
│     ╚════════════╝  ╚════════════╝  ╚════════════╝          │
│                          ▲                                    │
│                                                              │
│   "Llama Voraz. Veloz e implacable, pero se consume          │
│    con rapidez. Para los que arden brillante y breve."       │
│                                                              │
│      Energía     ███████░░░                                  │
│      Velocidad   █████████░                                  │
│      Daño        ██████████                                  │
│      Sigilo      █████░░░░░                                  │
│      Duración    ██████░░░░                                  │
│                                                              │
│              [← A/D →]  [Espacio: confirmar]                 │
└──────────────────────────────────────────────────────────────┘
```

#### Elementos visuales

- **Título "ELIGE TU ESENCIA"**: centrado, tipografía pixel art mayor.
- **Tres tarjetas** horizontales con el sprite animado idle de cada personaje (pulsando, como en juego). La carta del personaje seleccionado se distingue por:
  - Un **halo de color identificativo** del personaje rodeando la tarjeta.
  - Una pequeña flecha o marca `▲` debajo señalándola.
  - Tarjetas no seleccionadas con tono ligeramente apagado.
- **Frase narrativa** debajo de las tarjetas, cambia según el personaje seleccionado.
- **5 barras de stats** comparativas: Energía, Velocidad, Daño, Sigilo, Duración. Cada barra muestra el porcentaje relativo respecto al máximo entre los tres personajes.
- **Controles** abajo: `A/D` para cambiar, `Espacio` o `Enter` para confirmar. También se puede usar el ratón haciendo clic en la tarjeta.

#### Frases narrativas

- **LUMINIS**: *"El Heredero. Equilibrado, sereno, el primer espíritu que despertó. Para los que buscan el camino antes que la prisa."*
- **IGNIS**: *"Llama Voraz. Veloz e implacable, pero se consume con rapidez. Para los que arden brillante y breve."*
- **AETHEN**: *"El Sigiloso. Resistente y paciente, casi una sombra entre sombras. Para los que prefieren observar antes que actuar."*

#### Música y ambientación

Música etérea contemplativa, distinta de la del juego. Sin tensión: el jugador está tomando una decisión meditada, no en pleno combate.

#### Comportamiento

- Por defecto aparece preseleccionado **Luminis** la primera vez. En sesiones posteriores, se preselecciona el último personaje usado (recordado mediante `Preferences`).
- Al pulsar `Espacio` o `Enter`, breve animación de "confirmación" (la tarjeta crece ligeramente, flash de luz) y transición a la primera sala del Nivel 1.

### Implicaciones técnicas

🎯 **Scope del entregable**:

- Cada personaje es una **instancia de la clase `Personaje`** parametrizada con sus stats. No son tres clases distintas: una clase, tres configuraciones. Esto es buena práctica de POO.
- Los stats viven en un archivo de configuración externo (JSON o constantes en una clase `ConfiguracionPersonajes`), **no hardcodeados en el código del juego**. Permite rebalancear los personajes sin recompilar.
- El sprite de cada personaje es distinto pero **las animaciones reutilizan la misma estructura** (todos tienen idle, andar en 4 direcciones, dashear, recibir daño, atacar con ráfaga). Solo cambia el set de imágenes.
- El **color identificativo** del personaje se propaga a varios elementos del juego: barra de energía en el HUD, estela del dash, halo del aura, color de la ráfaga, halo del sprite. Esto refuerza la identidad visual y hace que cada partida se sienta distinta.

### Cobertura del enunciado

- ✅ **Funcionalidad opcional**: *"El usuario podrá escoger entre diferentes personajes del juego (al menos tres) con características diferentes."* Cubierto con tres personajes que se diferencian en estadísticas numéricas, mecánicas únicas (sigilo de Aethen), color identificativo, descripción narrativa y estilo de juego premiado.

🔮 **Visión a futuro**:

- **Más personajes** desbloqueables: un espíritu de hielo (ralentiza enemigos al pasar cerca), un espíritu de viento (puede encadenar dos dashes seguidos), un espíritu de tierra (su aura empuja a los enemigos en lugar de dañarlos).
- **Personajes desbloqueables**: empezar solo con Luminis y desbloquear Ignis y Aethen al cumplir ciertas condiciones (completar el juego con Luminis, completar el juego sin combatir, etc.).
- **Árbol de habilidades por personaje** (conectar con la visión a futuro del Modelo 3 del sistema de Santuarios): cada personaje tendría variantes específicas de las habilidades acordes con su personalidad mecánica.
- **Estadísticas por personaje** en el menú: número de partidas con cada uno, mejor puntuación, tiempo total jugado, logros conseguidos.
- **Rankings filtrados** por personaje usado en el Top 10.

---

## 10. Arquitectura técnica

### Filosofía general de diseño

🎯 **Scope del entregable**:

El código sigue cinco principios fundamentales:

1. **Programación Orientada a Objetos real**, no clases-contenedores de funciones. El enunciado lo exige explícitamente. Esto significa: clases con responsabilidades claras, encapsulación (atributos privados con getters/setters donde tenga sentido), herencia donde aporte, polimorfismo donde sea natural.

2. **Separación por responsabilidades**: las pantallas no contienen lógica de juego, los enemigos no dibujan UI, el HUD no sabe del nivel actual más allá de leerlo. Cada clase hace una cosa.

3. **Configuración externa**: stats de personajes, valores de puzzles, textos de inscripciones → en archivos de configuración, no hardcodeados. Rebalancear sin recompilar.

4. **Patrón Screen de libGDX**: cada pantalla del juego (menú, juego, pausa, game over...) es una clase distinta que implementa `Screen`. La clase principal `Main extends Game` orquesta el cambio entre pantallas.

5. **Documentación**: comentarios Javadoc en clases públicas con descripción breve. Sin comentarios obvios. Nombres de clases, métodos y variables en español, consistentes con el resto del documento.

### Estructura de paquetes

🎯 **Scope del entregable**:

```
com.davidgarcia.lumen/
│
├── Main.java                          ← Punto de entrada. extends Game.
│
├── pantallas/                         ← Cada pantalla = una clase Screen
│   ├── PantallaMenu.java
│   ├── PantallaSeleccionPersonaje.java
│   ├── PantallaJuego.java
│   ├── PantallaPausa.java
│   ├── PantallaFinNivel.java
│   ├── PantallaGameOver.java
│   ├── PantallaVictoriaFinal.java
│   ├── PantallaRecords.java
│   ├── PantallaInstrucciones.java
│   └── PantallaConfiguracion.java
│
├── entidades/                         ← Todo lo que vive en el mundo de juego
│   ├── Entidad.java                   ← Clase abstracta base
│   ├── Personaje.java                 ← El espíritu de luz (Lumen)
│   ├── npc/
│   │   ├── NPC.java                   ← Clase abstracta
│   │   ├── Acechante.java
│   │   ├── Miron.java
│   │   ├── Devorador.java
│   │   └── Susurro.java
│   ├── proyectiles/
│   │   ├── Proyectil.java
│   │   ├── RafagaLuz.java
│   │   └── ProyectilOscuridad.java
│   ├── recolectables/
│   │   ├── Recolectable.java
│   │   ├── CristalLuz.java
│   │   ├── Esencia.java
│   │   └── Llave.java
│   └── elementos/                     ← Objetos interactuables (no son NPCs)
│       ├── ElementoInteractuable.java
│       ├── Palanca.java
│       ├── Brasero.java
│       ├── Antorcha.java
│       ├── PlataformaPresion.java
│       ├── LosaRuna.java
│       ├── Bloque.java
│       ├── Puerta.java
│       ├── Santuario.java
│       └── Inscripcion.java
│
├── niveles/                           ← Carga y gestión de niveles
│   ├── Nivel.java
│   ├── Sala.java
│   ├── GestorNiveles.java
│   └── CargadorMapas.java             ← Carga archivos .tmx de Tiled
│
├── ia/                                ← Máquinas de estados de NPCs
│   ├── EstadoIA.java                  ← Interfaz
│   ├── MaquinaEstados.java
│   └── estados/
│       ├── EstadoPatrulla.java
│       ├── EstadoVigila.java
│       ├── EstadoAlerta.java
│       ├── EstadoPersigue.java
│       ├── EstadoDormido.java
│       ├── EstadoDespierto.java
│       ├── EstadoHerido.java
│       ├── EstadoFlota.java
│       ├── EstadoApunta.java
│       └── EstadoDispara.java
│
├── ui/                                ← HUD y elementos in-game
│   ├── HUD.java
│   ├── BarraEnergia.java
│   ├── CuadroDialogo.java
│   ├── IndicadorInteraccion.java
│   └── TextoFlotante.java
│
├── audio/                             ← Gestión centralizada de música y sonidos
│   └── GestorAudio.java
│
├── datos/                             ← Estructuras de datos persistentes
│   ├── Puntuacion.java
│   ├── GestorRecords.java
│   ├── EstadoPartida.java
│   └── GestorGuardado.java
│
├── config/                            ← Configuración y constantes
│   ├── ConfiguracionPersonajes.java
│   ├── ConfiguracionJuego.java
│   └── GestorPreferencias.java
│
└── utiles/                            ← Utilidades genéricas
    ├── GestorAssets.java
    ├── Animacion.java
    └── MathUtiles.java
```

#### Justificación de la estructura

- **`pantallas/`** y **`entidades/`** son los paquetes más grandes. Es lo normal: la mayoría del código vive ahí.
- **`ia/`** está separada porque crece sola — cada estado nuevo es una clase, agruparlos en `entidades/npc/` haría el paquete ilegible. Aplicación del **patrón State** del libro Design Patterns de la Gang of Four.
- **`config/`** separa los datos de configuración del código del juego. Cambiar el balance es ir a un solo sitio.
- **`utiles/`** contiene herramientas técnicas de propósito general, sin lógica del juego.
- **`datos/`** contiene estructuras de datos que cambian en tiempo de ejecución y persisten entre sesiones, claramente diferenciada de `config/` (parámetros estáticos) y `utiles/` (herramientas técnicas).

### Diseño por clases — Decisiones clave

#### `Personaje` parametrizado vs herencia para Luminis/Ignis/Aethen

🎯 **Scope del entregable**:

Los tres personajes seleccionables **comparten el 100% del comportamiento**: se mueven igual, atacan igual, reciben daño igual. Solo cambian sus valores numéricos (stats).

Por tanto, **no se modelan como subclases** (`Luminis extends Personaje`, etc.). Se modelan como **una única clase `Personaje` parametrizada** por una `ConfiguracionPersonaje`. Esto evita duplicación de código, facilita añadir nuevos personajes (basta con añadir una configuración) y permite rebalancear sin tocar lógica.

**Regla de oro aplicada**: herencia cuando cambia el comportamiento; parametrización cuando solo cambian los valores.

#### Subclases para NPCs

🎯 **Scope del entregable**:

Los NPCs (Acechante, Mirón, Devorador, Susurro) **sí se modelan como subclases** de `NPC` porque tienen comportamientos genuinamente distintos (patrullaje, detección con cono, ataque global, ataque a distancia). Cada subclase configura su `MaquinaEstados` con los estados que le corresponden y tiene métodos propios.

Las **variantes Élite** del Nivel 2 (Acechante Élite, etc.), en cambio, son la misma trampa que los personajes: solo cambian valores numéricos. Por tanto **no se modelan como sub-subclases**. Se crean parametrizando las clases base con valores diferentes.

#### Patrón Screen de libGDX

🎯 **Scope del entregable**:

`Screen` es una interfaz de libGDX que representa una "pantalla" del juego. La clase `Main extends Game` orquesta el cambio entre pantallas mediante `setScreen(new ...)`.

Cada `Screen` tiene su propio ciclo de vida:
- `show()`: se ejecuta una vez al activarse. Aquí se cargan recursos específicos.
- `render(float delta)`: se ejecuta 60 veces por segundo. Aquí van actualización lógica y dibujado.
- `hide()`, `dispose()`: liberación al cambiar de pantalla o cerrar.

Esta separación garantiza que cada pantalla tiene su propio estado y lógica encapsulados, evitando un monolito de "ifs por estado".

#### Patrón State para IA

🎯 **Scope del entregable**:

La IA de los NPCs se implementa mediante **máquinas de estados finitos (FSM)** con el patrón State del Gang of Four. Cada estado de comportamiento (PATRULLA, VIGILA, ALERTA, PERSIGUE, DORMIDO, DESPIERTO, HERIDO, FLOTA, APUNTA, DISPARA) es una clase propia que implementa la interfaz `EstadoIA`:

```
public interface EstadoIA {
    void entrar(NPC npc);
    void actualizar(NPC npc, float delta);
    void salir(NPC npc);
}
```

La clase `MaquinaEstados` mantiene el estado actual y delega su ejecución. Cuando un estado decide que ya no aplica, llama a `maquinaEstados.cambiarA(otroEstado)`.

**Ventajas**:
- Código de cada estado aislado en un archivo de ~30-50 líneas, en lugar de un `if/else` monolítico repartido por las clases de NPC.
- Estados reutilizables entre tipos de NPC (ej. `EstadoPatrulla` puede valer también para futuros enemigos).
- Aplicación canónica de un patrón clásico de diseño.

#### Hitboxes diferenciadas en NPCs

🎯 **Scope del entregable**:

Cada NPC tiene **dos hitboxes** distintas (objetos `Circle` o `Rectangle` de libGDX):

- `hitboxNucleo`: pequeño, en el centro. Si Lumen entra → Lumen recibe daño.
- `hitboxCuerpo`: mayor. Si el aura de Lumen entra → la sombra recibe daño.

Esto resuelve el conflicto entre el aura ofensiva y el daño por contacto descrito en la sección 5. Se implementa con métodos `getHitboxNucleo()` y `getHitboxCuerpo()` en la clase `NPC`.

#### Vector normalizado en movimiento 8 direcciones

🎯 **Scope del entregable**:

El movimiento 8 direcciones con teclas independientes generaría velocidad mayor en diagonal (vector de magnitud √2 ≈ 1.41) que en horizontal/vertical (magnitud 1). Se corrige normalizando el vector de movimiento antes de aplicarlo:

```
Vector2 direccion = new Vector2(deltaX, deltaY).nor();  // normalizado
posicion.add(direccion.scl(velocidad * delta));
```

Esto garantiza que Lumen se mueve siempre a la misma velocidad real, independientemente de la dirección.

### Librerías y dependencias

🎯 **Scope del entregable**:

#### Librerías que ya vienen con liftoff
- **libgdx-core**: API base de libGDX.
- **libgdx-platform (LWJGL3)**: backend de escritorio.
- **Scene2D** (incluido en core): para menús, HUD, botones y elementos UI.

#### Librerías que se añadirán cuando se necesiten

| Librería | Para qué | Cuándo |
|---|---|---|
| **gdx-tiled** | Leer y renderizar mapas `.tmx` de Tiled | Al empezar la implementación de los niveles |
| **gdx-freetype** (opcional) | Cargar fuentes `.ttf` con cualquier tamaño | Si se decide usar una fuente pixel art personalizada |

Cada librería se añade solo cuando se sabe para qué se usa. No se añaden dependencias "por si acaso".

#### Librerías evaluadas y descartadas

| Librería | Motivo del descarte |
|---|---|
| **Box2D** | Innecesario. Lumen no tiene gravedad ni físicas complejas. Las colisiones se resuelven con `Rectangle.overlaps()` y `Circle.overlaps()`, mucho más simples y suficientes. |
| **gdx-ai** | Innecesario. La FSM manual con el patrón State cubre la complejidad de IA del proyecto sin requerir un sistema externo. |
| **Artemis-ODB / Ashley (ECS)** | Innecesario. Los sistemas Entity-Component-System aportan cuando hay cientos de entidades dinámicas; aquí habrá ~20-30 entidades por sala, sobra con POO clásica. |
| **Kotlin / KTX** | El proyecto se desarrolla en Java puro, decisión tomada en Fase 0. |

### Resolución y renderizado

🎯 **Scope del entregable**:

- **Resolución lógica del juego**: 480 × 270 píxeles. Exactamente un cuarto de Full HD.
- **Tamaño de tile estándar**: 16 × 16 píxeles.
- **Escalado**: cada píxel del juego se renderiza como un cuadrado 4 × 4 en una pantalla Full HD. Filtro `Nearest Neighbor` (no `Linear`) para conservar la nitidez del pixel art.
- **Cámara**: `OrthographicCamera` de libGDX con viewport 480 × 270. Una `FitViewport` o `ExtendViewport` se usa para gestionar el escalado en distintas resoluciones de ventana.

### Mapas y niveles

🎯 **Scope del entregable**:

Los niveles se diseñan en **Tiled** (editor externo gratuito) y se exportan en formato `.tmx`. libGDX los carga mediante el módulo `gdx-tiled`.

**Estructura de cada mapa `.tmx`**:

- **Capa Tiles - Suelo**: el suelo dibujado de la sala.
- **Capa Tiles - Paredes**: las paredes (con colisión).
- **Capa Tiles - Decoración**: elementos decorativos sobre el suelo (no colisionan).
- **Capa Objetos - Colisiones**: rectángulos invisibles que definen colisiones (paredes complejas, obstáculos).
- **Capa Objetos - Entidades**: marcadores con propiedades (tipo, posición) para enemigos, recolectables, elementos interactuables.
- **Capa Objetos - Puertas**: rectángulos que activan transición a la siguiente sala.

El cargador (`CargadorMapas`) lee el `.tmx`, instancia los objetos correspondientes según el tipo marcado en cada propiedad de objeto, y devuelve una estructura `Sala` con todo listo.

> Este uso de mapas externos cubre parcialmente la filosofía de "generador de niveles externos": los niveles pueden modificarse sin tocar código.

### Persistencia de datos

🎯 **Scope del entregable**:

| Datos | Cómo se guardan | Dónde |
|---|---|---|
| Opciones (volumen, dificultad, último nombre) | `Preferences` de libGDX (clave-valor) | `~/.prefs/lumen.prefs` (Mac/Linux), AppData en Windows |
| Récords del Top 10 | `Preferences` o JSON | mismo lugar |
| Partida guardada (Guardar/Cargar) | Serialización con `Json` de libGDX | Archivo `partida_guardada.json` en la carpeta de preferencias |

**Estructura de `EstadoPartida`** (lo que se guarda al guardar partida):

- Personaje elegido (string identificador: "LUMINIS", "IGNIS" o "AETHEN").
- ID del nivel actual ("nivel1" o "nivel2") y de la sala ("N1-S3").
- Posición de Lumen en la sala (Vector2).
- Energía actual (float).
- Habilidades desbloqueadas (boolean por cada: rafaga, dash, aura).
- Puntos acumulados (int).
- Esencias en posesión (int).
- Esencias gastadas (int) — para evitar redesbloquear.
- Tiempo total transcurrido (float, segundos).
- Estado de elementos de salas visitadas (palancas activadas, antorchas encendidas, etc.) — un diccionario clave-valor por sala.

**Cuándo se guarda**:
- Manualmente al pulsar "Guardar" desde el menú de pausa.
- Automáticamente al transicionar entre salas (autosave silencioso).

**Cuándo se carga**:
- Al pulsar "Continuar" en el menú principal. Este botón solo aparece visible si existe el archivo de guardado (`GestorGuardado.existePartidaGuardada()`).

### Flujo del juego (quién llama a quién)

🎯 **Scope del entregable**:

#### Arranque

```
1. Sistema operativo → Lwjgl3Launcher.main()
2. Lwjgl3Launcher crea ventana → Main.create()
3. Main.create():
   ├─ Inicializa GestorAssets
   ├─ Inicializa GestorAudio
   ├─ Inicializa GestorPreferencias
   └─ setScreen(new PantallaMenu(this))
4. Loop de render a 60 FPS: PantallaMenu.render(delta)
```

#### Bucle de juego (PantallaJuego.render)

Cada frame, `PantallaJuego.render(delta)` ejecuta dos fases:

**Fase 1 — Actualización lógica**:
- Lectura de input → actualizar `Personaje` (mover, atacar, dashear).
- Actualizar `NPC`s (cada uno delega a su `MaquinaEstados`).
- Actualizar proyectiles activos.
- Actualizar `ElementoInteractuable`s (palancas, plataformas...).
- Actualizar `HUD` (lee datos de Personaje y Nivel).
- Detectar colisiones (Personaje vs NPCs, Aura vs Cuerpos, Ráfaga vs Cuerpos, Personaje vs Recolectables, Personaje vs ElementoInteractuable...).
- Aplicar consecuencias (restar energía, sumar puntos, recoger objetos, activar palancas...).
- Comprobar transición a otra sala.
- Comprobar Game Over.

**Fase 2 — Dibujado**:
- Limpiar pantalla.
- Dibujar `TiledMap` (suelo, paredes, decoración).
- Dibujar `Recolectable`s.
- Dibujar `ElementoInteractuable`s.
- Dibujar `NPC`s.
- Dibujar `Personaje` (Lumen).
- Dibujar proyectiles.
- Dibujar `HUD` (siempre encima).

#### Transición entre salas

```
1. PantallaJuego detecta que Lumen ha cruzado el rectángulo de Puerta.
2. Inicia fade negro (0.3s).
3. Llama a GestorNiveles.cargarSala("N1-S4").
4. GestorNiveles → CargadorMapas.cargar("nivel1-sala4.tmx").
5. CargadorMapas lee el .tmx, instancia NPCs, recolectables, elementos.
6. Devuelve un objeto Sala.
7. PantallaJuego coloca a Lumen en la posición de entrada de la nueva sala.
8. Aplica bonus de completar sala (energía + puntos).
9. Autosave silencioso.
10. Fade desaparece, jugador en la nueva sala.
```

### Cobertura completa del enunciado

🎯 **Scope del entregable** — Tabla resumen para evaluación:

#### Requisitos obligatorios

| Requisito | Sección donde se define |
|---|---|
| Personaje principal manejable con inicio, final y objetivo claro. Al menos 2 niveles diferenciados | Secciones 1, 3, 4, 6 |
| Información en pantalla (puntuación, energía, nivel actual), actualizada | Sección 7 |
| Menús para iniciar/configurar/terminar partida + instrucciones + jugar otra partida sin salir + al menos 2 opciones configurables | Sección 10 (Pantallas) — 3 opciones: música, efectos, dificultad |
| Al menos 3 NPCs diferentes interactuando | Sección 5 — Acechante, Mirón, Devorador (+Susurro y variantes) |
| Sonido y animaciones en todos los caracteres con movimiento | Secciones 2, 4, 5 |

#### Funcionalidades opcionales elegidas (5 + 1 = 6)

| Funcionalidad | Sección |
|---|---|
| GitHub bien gestionado (commits, issues, releases, wiki) | Sección 10 — Cobertura del enunciado |
| Top 10 de puntuaciones con nombre | Sección 8 — Récords de Campeones |
| IA en NPCs | Sección 5 + 10 — Patrón State con 4 tipos de IA |
| Menú de pausa in-game | Sección 10 — PantallaPausa |
| Selección entre 3 personajes con stats distintos | Sección 9 — Luminis, Ignis, Aethen |
| Guardar/Cargar partida | Sección 10 — EstadoPartida + GestorGuardado |

#### Funcionalidades opcionales descartadas (informativo)

| Descartada | Motivo |
|---|---|
| 2 niveles adicionales | El "Continuará..." narrativo invita a continuación futura sin comprometerse en el entregable |
| Multijugador local/red | Complejidad alta para el tiempo disponible |
| 3 nuevos tipos de NPCs | Ya se cubren 4 tipos + variantes Élite |
| Generador de niveles externo | Cubierto parcialmente al usar Tiled, sin generación procedural |
| Powerups | El sistema de habilidades por santuarios cubre el espíritu |
| Multiplataforma | El proyecto está preparado para extender, pero el entregable se limita a Desktop |
| Box2D | Innecesario para el tipo de juego (top-down sin físicas complejas) |

🔮 **Visión a futuro**: arquitectura preparada para extender a Android/iOS/Web sin refactorizar las pantallas y entidades (solo añadir nuevos módulos de launcher). Diseño compatible con un futuro **sistema de logros** sin refactorizar (gracias a la separación clara de `datos/`). Estructura modular que permitiría incorporar más personajes, niveles, tipos de NPCs y habilidades simplemente añadiendo clases sin tocar las existentes.

---

*Documento de diseño finalizado. Todas las secciones cerradas. Última actualización: secciones 1, 2, 3, 4, 5, 6, 7, 8, 9 y 10.*

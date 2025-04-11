# JuegoJavaFinalDefinitivo

**JuegoJavaFinalDefinitivo** es un proyecto de recreación del clásico videojuego *Super Mario World* desarrollado en Java. Este proyecto utiliza una arquitectura modular para manejar estados del juego, gráficos, audio y lógica de juego.

## Estructura del Proyecto

El proyecto está organizado en los siguientes directorios principales:

- **`src/`**: Contiene el código fuente del proyecto.
  - **`audio/`**: Manejo de audio, incluyendo música y efectos de sonido.
  - **`entidades/`**: Clases relacionadas con los personajes y enemigos del juego.
  - **`gamestates/`**: Estados del juego como menú, jugando, opciones, etc.
  - **`levels/`**: Gestión de niveles y datos relacionados.
  - **`main/`**: Punto de entrada principal del juego y clases base.
  - **`objects/`**: Objetos interactuables como bloques y potenciadores.
  - **`ui/`**: Interfaz de usuario, como menús y botones.
  - **`utilz/`**: Utilidades y constantes globales.

- **`test/`**: Directorio reservado para pruebas unitarias.

- **`nbproject/`**: Archivos de configuración del proyecto para NetBeans.

- **`build/`**: Archivos generados durante la compilación.

- **`MarioSprites/`**: Recursos gráficos como sprites de personajes, enemigos y tiles.

## Requisitos del Sistema

- **Java Development Kit (JDK)** versión 8 o superior.
- **NetBeans IDE** (opcional, pero recomendado para facilitar la ejecución y depuración).
- Sistema operativo compatible con Java.

## Cómo Ejecutar el Proyecto

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone <URL_DEL_REPOSITORIO>

2. Abre el proyecto en NetBeans o tu IDE favorito.

3. Compila el proyecto:
   - En NetBeans, selecciona `Build Project` en el menú.
   - Alternativamente, usa el siguiente comando en la terminal:
     ```bash
     ant compile
     ```

4. Ejecuta el proyecto:
   - En NetBeans, selecciona `Run Project`.
   - O usa el siguiente comando en la terminal:
     ```bash
     ant run
     ```

## Controles del Juego

- **Movimiento**: Usa las teclas de dirección para mover al personaje.
- **Salto**: Presiona la tecla `Espacio`.
- **Ataque**: Usa la tecla `Ctrl` para realizar un ataque.
- **Pausa**: Presiona `P` para pausar el juego.

## Características

- **Estados del Juego**: Incluye estados como Menú, Jugando, Opciones y Game Over.
- **Gestión de Niveles**: Carga dinámica de niveles con enemigos y objetos.
- **Audio**: Música de fondo y efectos de sonido personalizados.
- **Interfaz de Usuario**: Menús interactivos y opciones de configuración.
- **Gráficos**: Sprites en 2D escalados para adaptarse a diferentes resoluciones.

## Posibles Mejoras Futuras

- Implementación de un sistema de guardado y carga de partidas.
- Añadir más niveles y enemigos.
- Mejorar la inteligencia artificial de los enemigos.
- Soporte para multijugador local.
- Optimización del rendimiento para dispositivos con recursos limitados.

## Créditos

- **Desarrollador**: Robert
- **Recursos Gráficos**: Sprites personalizados y adaptados de *Super Mario World*.
- **Sonido**: Efectos de sonido y música adaptados para el proyecto.

## Licencia

Este proyecto es solo para fines educativos y no tiene fines comerciales. Todos los derechos de los recursos originales pertenecen a sus respectivos propietarios.

---
¡Diviértete jugando y explorando el código!

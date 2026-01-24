# MediaTracker

Segundo proyecto personal y autodidacta.

Aplicación de escritorio desarrollada en **Java + JavaFX** para el seguimiento de películas y series.  
Permite gestionar contenidos audiovisuales y registrar actividades asociadas al consumo de los mismos.

---

## Objetivo del proyecto

- Profundizar conceptos aprendidos en proyectos anteriores
- Extender la arquitectura MVC
- Gestionar recursos locales y externos (imágenes)
- Mejorar la separación entre lógica visual y lógica de negocio

---

## Arquitectura

Arquitectura **MVC extendida**:

### Model
- `AudioVisual` (clase base)
- `Pelicula`
- `Serie`
- `Temporada`
- `Capitulo`
- `Actividad`

### Controller
- Manejo de eventos
- Navegación principal

### Service
- `IService` para la lógica de negocio
- Servicio dedicado para:
  - imágenes locales
  - imágenes por URL
  - fallback automático

### Repository
- Acceso a datos
- Persistencia local

---

## Persistencia

- Persistencia local mediante **JSON**
- Serialización y deserialización manual
- Dos archivos independientes:
  - uno para datos audiovisuales
  - otro para actividades
- Cada archivo funciona como **fuente única de verdad**
- Guardado automático ante cualquier modificación

---

## Conceptos aplicados

- Programación Orientada a Objetos
- Separación de responsabilidades
- Lógica de negocio desacoplada de la UI
- Validaciones centralizadas
- Delegación de trabajo entre capas
- Manejo de errores y feedback al usuario (IREP)
- Implementación de interfaces
- Persistencia local
- Refactorización incremental

---

## Conceptos aprendidos

- Manejo de imágenes locales y externas (URLs)
- Fallback automático de recursos
- Gestión de archivos
- Profundización de arquitectura MVC
- Mejora progresiva del diseño de software

---

## Cómo ejecutar el proyecto

Este proyecto está pensado para ejecutarse en un entorno de desarrollo (IDE).

### Requisitos
- Java JDK 17 o superior (probado con JDK 25.0.1)
- Maven
- IDE compatible con JavaFX (IntelliJ IDEA recomendado)

### Dependencias
- JavaFX 21.0.6 (Maven)
- Gson 2.11.0 (Maven)

### Pasos
1. Clonar el repositorio
   ```bash
   git clone https://github.com/SdrNahui/MediaTracker.git
   ```

2. Abrir el proyecto en el IDE

3. Configurar el SDK si es necesario (Language level: SDK default)

4. Ejecutar la clase `MediaTrackerApplication`

Los datos se cargan automáticamente desde archivos JSON locales.

---
## Estado del proyecto

- Funcional
- Persistente
- Estable
- Versión: **v1.1**

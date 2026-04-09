# Catalogo Express

Aplicacion Android nativa en Kotlin que consume un catalogo remoto, calcula un score por item, ordena la lista por relevancia y permite navegar de listado a detalle.

## Caracteristicas

- Arquitectura por capas con `ViewModel`, `Repository` y casos de uso.
- Navegacion con `Fragments`.
- Pantalla de listado implementada con Jetpack Compose.
- Pantalla de detalle implementada con vistas nativas.
- Consumo de API con Retrofit + OkHttp.
- Persistencia local con Room para cache.
- Manejo de estados `loading`, `success` y `error`.
- Orden por `score` descendente.

## API usada

- Base remota: `https://fakestoreapiserver.reactbd.org/api/products`

## Compilacion

Desde la raiz del proyecto:

```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

En Windows PowerShell tambien puedes usar:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat assembleRelease
```

## Versiones usadas

- Android Gradle Plugin: `9.0.1`
- Gradle: `9.2.1`
- Kotlin: `2.0.21`
- compileSdk: `35`
- targetSdk: `35`
- minSdk: `24`

## Librerias principales

- Retrofit
- OkHttp Logging Interceptor
- Room
- Coil
- Jetpack Compose
- Navigation Fragment
- Lifecycle ViewModel

## Estructura

- `data/remote`: cliente HTTP, datasource y DTOs.
- `data/local`: Room cache.
- `data/repository`: repositorio y mappers.
- `domain/model`: modelo de producto.
- `domain/usecase`: casos de uso y score.
- `ui/list`: listado en Compose.
- `ui/detail`: detalle nativo.
- `ui/common`: formateadores, tema y factory.

## Decisiones tecnicas

- Se usa `BuildConfig` para diferenciar `debug` y `release`.
- `debug` habilita logging HTTP y usa una base URL raiz con path configurable.
- `release` usa una base URL mas cerrada y sin logging de red.
- La cache local permite fallback cuando la red falla.
- El score se calcula fuera de UI para mantener una capa de datos clara.

## Documentacion adicional

Revisar `DECISIONES.md` para:

- manejo de token
- normalizacion del score
- resiliencia y errores
- diferencias entre ambientes
- estrategia de entrega a QA

# Catalogo Express

Catalogo Express es una aplicación Android nativa desarrollada en Kotlin. El objetivo fue construir un catálogo simple pero mantenible, con una pantalla de listado y una de detalle, consumiendo datos remotos, calculando un score por producto y evitando acoplar la UI directamente a la respuesta del backend.

## Alcance

- Listado de productos con imagen, título, precio, estado y score.
- Detalle del producto seleccionado.
- Ordenamiento descendente por score.
- Consumo de API remota con capa de datos separada de la UI.
- Persistencia local para fallback básico ante fallos de red.
- Navegación nativa con `Fragments`.
- Uso de Jetpack Compose en la pantalla principal.

## Stack técnico

- Kotlin
- Android SDK
- Jetpack Compose
- Fragments + Navigation
- ViewModel
- Retrofit + OkHttp
- Room
- Coil

## Fuente de datos

API utilizada:

- `https://fakestoreapiserver.reactbd.org/api/products`

## Compilación

Desde la raíz del proyecto:

```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

En PowerShell:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat assembleRelease
```

Para instalar la build debug en un dispositivo conectado:

```powershell
.\gradlew.bat installDebug
```

## Versiones utilizadas

- Android Gradle Plugin: `9.0.1`
- Gradle: `9.2.1`
- Kotlin: `2.0.21`
- compileSdk: `36`
- targetSdk: `36`
- minSdk: `24`

## Validaciones ejecutadas

- `.\gradlew.bat assembleDebug`
- `.\gradlew.bat assembleRelease`
- `.\gradlew.bat testDebugUnitTest`

## Estructura general

- `data/remote`: servicio HTTP, DTOs y acceso remoto.
- `data/local`: base local con Room.
- `data/repository`: integración entre remoto, cache y normalización.
- `domain/model`: modelo de producto.
- `domain/usecase`: lógica puntual de consulta y score.
- `ui/list`: listado en Compose.
- `ui/detail`: detalle nativo.
- `ui/common`: utilidades compartidas de presentación.

## Decisiones técnicas

- La app se construyó con una arquitectura simple por capas para mantener separado acceso a datos, lógica y presentación.
- El score se calcula fuera de la UI, durante la transformación del dato, para no repetir lógica en pantalla.
- Se diferenciaron variantes `debug` y `release` usando `BuildConfig`.
- En `debug` se habilitó logging HTTP; en `release`, no.
- Room se utiliza como cache local para mejorar resiliencia frente a fallos de red.

## Documentación complementaria

En `DECISIONES.md` se documentan:

- arquitectura adoptada
- tradeoffs tomados por tiempo
- mejoras futuras
- criterios de backend y nube

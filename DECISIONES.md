# DECISIONES

## 1. Arquitectura

Se implemento una arquitectura simple y mantenible:

- `ViewModel` para manejar estado de UI.
- `Repository` como unico punto de acceso a datos.
- `RemoteDataSource` para red.
- `Room` para persistencia local.
- Casos de uso para desacoplar la logica de consulta.

La UI no consume datos hardcodeados. Todo llega desde la capa de datos y pasa por normalizacion antes de mostrarse.

## 2. Normalizacion del score

Formula requerida:

```text
score = (rating * ln(stock + 1)) / max(price, 1)
```

Reglas aplicadas:

- `id`: si falla el parseo, se usa `0`.
- `title`: si llega vacio o nulo, se usa `Sin titulo`.
- `price`: se parsea a `Double`; si falla, se usa `0.0`.
- `rating`: se parsea a `Double`; si falla, se usa `0.0` y luego se limita al rango `0.0..5.0`.
- `stock`: se parsea a `Int`; si falla, se usa `0`.
- `image`: si no existe, se deja vacia y la UI tolera ese caso.
- `status`: se deriva de `stock`.

Estados:

- `stock <= 0`: `Sin stock`
- `stock in 1..10`: `Pocas unidades`
- `stock > 10`: `Disponible`

La lista se ordena por `score` descendente antes de llegar a UI.

## 3. Token y autenticacion

No se implemento autenticacion porque no era requerida funcionalmente, pero si se define la estrategia:

- Preferiria JWT de corta vida.
- El token de acceso no iria en SharedPreferences plano.
- Para almacenamiento seguro usaria `EncryptedSharedPreferences` o `DataStore` protegido con Android Keystore segun el contexto.
- Un `OkHttp Interceptor` seria el lugar adecuado para adjuntar el token al cliente HTTP.
- La renovacion del token se resolveria con un autenticador o flujo dedicado de refresh token.

## 4. Red, timeout y resiliencia

Se implementaron timeouts en OkHttp:

- `connectTimeout = 15s`
- `readTimeout = 15s`
- `writeTimeout = 15s`
- `callTimeout = 20s`

Errores tipificados:

- timeout
- offline o fallo de red
- error backend HTTP
- not found
- unknown

Estrategia:

- Si la llamada remota funciona, se actualiza Room.
- Si falla la red y hay cache, se devuelve cache local.
- Si falla la red y no hay cache, la UI entra en estado `error`.
- La UI expone reintento manual.

## 5. Ambientes

Se diferenciaron dos ambientes:

- `debug`
- `release`

Diferencias:

- `debug` usa logging HTTP con `HttpLoggingInterceptor`.
- `release` desactiva el logging.
- Ambos usan `BuildConfig` para resolver `BASE_URL` y `PRODUCTS_PATH`.

Para demostrar configuracion por ambiente:

- `debug` usa base URL raiz y path `api/products`.
- `release` usa base URL con `/api/` y path `products`.

## 6. Release y QA

Estrategia de entrega a QA:

1. Generar `assembleRelease`.
2. Validar smoke test basico en dispositivo fisico o emulador.
3. Compartir APK o AAB segun el flujo del equipo.
4. Adjuntar changelog corto y riesgos conocidos.

Diferencias practicas entre builds:

- `debug`: mas util para desarrollo, con logging y sufijo de version.
- `release`: pensada para validar entrega, sin logging de red y con configuracion estable.

## 7. Librerias externas usadas

- Retrofit
- OkHttp
- Coil
- Room

Todas tienen uso real en la implementacion:

- Retrofit/OkHttp para consumir la API.
- Coil para cargar imagenes.
- Room para cache local.

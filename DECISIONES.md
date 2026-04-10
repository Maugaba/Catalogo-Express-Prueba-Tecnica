# DECISIONES

## Arquitectura usada

La aplicación se resolvió con una arquitectura por capas simple y clara: `ui`, `data` y `domain`. En presentación usé `Fragments`, `ViewModel` y estados `loading / success / error`. En datos, centralicé el acceso en un `Repository`, apoyado por una fuente remota con Retrofit y una cache local con Room. La lógica de cálculo del `score` y la normalización del dato no quedó en la UI, sino antes de exponer el modelo a pantalla.

Para el cálculo del score apliqué la fórmula pedida:

```text
score = (rating * ln(stock + 1)) / max(price, 1)
```

Antes de calcularlo normalicé los valores para tolerar vacíos, nulos o parseos inválidos. Si `price`, `rating` o `stock` no son válidos, se reemplazan por valores seguros. El estado del producto también se deriva desde `stock`, para no depender de un campo externo adicional.

## Tradeoffs por tiempo

Por tiempo prioricé una base sólida y compilable antes que sumar más complejidad. Dejé Compose solo en la pantalla de listado y mantuve el detalle con vistas nativas, porque así cumplía el requisito técnico sin forzar una migración completa. También opté por una inyección de dependencias manual con un `AppContainer`, suficiente para este alcance y más rápida de dejar estable que incorporar Hilt.

En persistencia elegí Room como cache simple de lectura, sin sincronización avanzada ni estrategia de invalidación elaborada. El manejo offline queda cubierto en el caso más útil para la prueba: si falla la red, se intenta responder con datos locales y, si no existen, se informa el error con opción de reintento.

## Mejoras futuras

Si este proyecto continuara, el siguiente paso natural sería unificar toda la UI en Compose, agregar paginación real, filtros y búsqueda. También convendría incorporar una estrategia de refresco más explícita para la cache, manejo de estados de red más granular y tests de integración para repository y navegación.

En una segunda iteración también sumaría inyección con Hilt, placeholders/estados vacíos más trabajados, observabilidad básica y una definición más estricta del contrato backend para evitar normalizaciones defensivas excesivas en cliente.

## Decisiones de nube y backend

Para esta entrega usé una API pública como fuente de datos y diferencié `debug` y `release` por ambiente usando `BuildConfig`, con logging HTTP habilitado solo en `debug`. En un escenario real, mantendría la `baseUrl` por ambiente y separaría al menos desarrollo, QA y producción.

Si el backend requiriera autenticación, usaría JWT de corta vida y almacenaría el token de forma segura, idealmente apoyado en Android Keystore. La inyección del token iría en un interceptor de OkHttp y el refresh se manejaría fuera de la UI. Del lado de resiliencia, dejaría definidos timeouts, códigos de error esperados y contratos de respuesta consistentes para reducir lógica defensiva en la app.

Para entrega a QA, generaría una build `release` firmada para distribución interna y mantendría `debug` como variante de desarrollo. Esa separación permite validar comportamiento cercano a producción sin perder trazabilidad durante implementación y pruebas.

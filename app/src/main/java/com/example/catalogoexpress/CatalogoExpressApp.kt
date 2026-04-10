package com.example.catalogoexpress

import android.app.Application

class CatalogoExpressApp : Application() {
    val appContainer: AppContainer by lazy { AppContainer(this) }
}

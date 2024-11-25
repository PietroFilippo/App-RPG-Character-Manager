package com.example.rpgcharactermanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Indica que esta classe é o ponto de entrada do Hilt na aplicação
@HiltAndroidApp
class PersonagemApplication : Application() {

    // Sobrescreve o método onCreate() do Application
    override fun onCreate() {
        super.onCreate()
        // Inicialização da aplicação
    }
}
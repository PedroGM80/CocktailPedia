package dev.pgm.cocktailpedia

import android.app.Application

class CocktailPediaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
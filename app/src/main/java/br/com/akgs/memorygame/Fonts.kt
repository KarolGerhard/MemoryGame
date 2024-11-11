package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Typeface
import android.graphics.Typeface.createFromAsset
import android.util.Log

object Fonts {

    lateinit var pokemonFontSolid: Typeface
    lateinit var pokemonFontHollow: Typeface

    fun initializeFont(context: Context) {
        try {
            pokemonFontSolid = createFromAsset(context.assets, "Pokemon-Solid.ttf")
            pokemonFontHollow = createFromAsset(context.assets, "Pokemon-Hollow.ttf")
        }
        catch (e: Exception) {
            Log.d("App", "Algo ocorreu de errado ao carregar a fonte")
        }
    }
}
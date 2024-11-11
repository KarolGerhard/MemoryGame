package br.com.akgs.memorygame

import android.graphics.Bitmap

data class Card(
    var image: Bitmap,
    var defaultImage: Bitmap,
    var currentImage: Bitmap,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false
)
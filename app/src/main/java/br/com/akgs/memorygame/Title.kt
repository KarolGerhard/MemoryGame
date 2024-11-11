package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

class Title(private var x: Float, private var y: Float): GameObject {

    private val paint = Paint()
    private var animTime = 0f
    private var changeTime = 100f

    init {
        paint.color = Color.WHITE
        paint.typeface = Fonts.pokemonFontHollow
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 65f
        paint.isFakeBoldText = true
//        title = loadBitmap("title-cena-dois.png")

    }

    override fun update(et: Float) {
        animTime += et
        if (animTime >= changeTime) {
            animTime = 0f
            paint.color = Color.rgb(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
        }
    }

    override fun render(canvas: Canvas) {
        canvas.drawText("Combine os pares", x, y, paint)
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {}

//    fun loadBitmap(fileName: String): Bitmap {
//        return BitmapFactory.decodeStream(context.assets.open(fileName))
//    }
}
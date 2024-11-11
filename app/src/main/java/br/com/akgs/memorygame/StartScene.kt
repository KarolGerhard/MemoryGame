package br.com.akgs.memorygame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import kotlin.random.Random

class StartScene(private val screen: MainActivity.Screen): Scene {

    private val paint = Paint()
    private var controlTime = 0f
    private var meioEeveeBitmap: Bitmap? = null
    private var topoBitmap: Bitmap? = null
    private var bottomBitmap: Bitmap? = null


    init {
        paint.textSize = 90f
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Fonts.pokemonFontHollow
        paint.color = Color.YELLOW

        meioEeveeBitmap = loadBitmap("centro-eevee.png")
        topoBitmap = loadBitmap("topo1.png")
        bottomBitmap = loadBitmap("bottom.png")
    }

    override fun update(et: Float) {
        controlTime += et
        if (controlTime > 300) {
            controlTime = 0f
            paint.color = Color.rgb(Random.nextInt(255), Random.nextInt(253), Random.nextInt(85))
        }
    }

    override fun render(canvas: Canvas) {
        val topo = topoBitmap ?: return
        canvas.drawBitmap(topo, (screen.width - topo.width)/2f, 150f, paint)

        val centro = meioEeveeBitmap ?: return
        canvas.drawBitmap(centro, (screen.width - centro.width)/2f, screen.height/3f, null)

        val text = "Toque para jogar"
        canvas.drawText(text, screen.width/2f, screen.height - 650f, paint)

        val bottom = bottomBitmap ?: return
        canvas.drawBitmap(bottom, (screen.width - bottom.width)/2f, screen.height - 400f, null)

    }

    override fun onTouch(e: MotionEvent): Boolean {
        return when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                screen.scene = GameScene(screen)
                true
            }
            else -> false
        }
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    private fun loadBitmap(file: String): Bitmap? {
        try {
            val inputStream = screen.context.assets.open(file)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            return bitmap
        }
        catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao carrgar a imagem")
        }

        return null
    }
}
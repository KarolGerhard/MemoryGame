package br.com.akgs.memorygame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import kotlin.random.Random

class FinishSuccessScene(private val screen: MainActivity.Screen) : Scene {

    private val paint = Paint()
    private var controlTime = 0f
    private var eeveeBitmap: Bitmap? = null
    private var messageBitmap: Bitmap? = null
    private var music: Music? = null


    init {
        paint.textSize = 90f
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Fonts.pokemonFontHollow
        paint.color = Color.YELLOW

        eeveeBitmap = loadBitmap("eevee-happy.jpg")
        messageBitmap = loadBitmap("parabens.png")

        try {
            val descriptor = screen.context.assets.openFd("music-completed.mp3")
            music = Music(descriptor)
        } catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao carregar o áudio")
        }
        music?.setLooping(false)
        music?.setVolume(5f)
        music?.play()
    }

    override fun update(et: Float) {
        controlTime += et
        if (controlTime > 300) {
            controlTime = 0f
            paint.color = Color.rgb(Random.nextInt(255), Random.nextInt(253), Random.nextInt(85))
        }
    }

    override fun render(canvas: Canvas) {
        val eevee = eeveeBitmap ?: return
        canvas.drawBitmap(eevee, (screen.width - eevee.width) / 2f, 150f, paint)

        val message = messageBitmap ?: return
        canvas.drawBitmap(
            message,
            (screen.width - message.width) / 2f,
            screen.height / 2f + 300f,
            paint
        )

    }

    override fun onTouch(e: MotionEvent): Boolean {
        return when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                screen.scene = StartScene(screen)
                true
            }

            else -> false
        }
    }

    override fun onResume() {

    }

    override fun onPause() {
        music?.pause()
        music?.dispose()
    }

    private fun loadBitmap(file: String): Bitmap? {
        try {
            val inputStream = screen.context.assets.open(file)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            return bitmap
        } catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao carrgar a imagem")
        }

        return null
    }
}
package br.com.akgs.memorygame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import kotlin.random.Random

class GameOverScene(private val screen: MainActivity.Screen) : Scene {

    private val paint = Paint()
    private var controlTime = 0f
    private var eeveeSad: Bitmap? = null
    private var gameOver: Bitmap? = null
    private var music: Music? = null


    init {
        paint.textSize = 72f
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Fonts.pokemonFontSolid
        paint.color = Color.YELLOW

        eeveeSad = loadBitmap("sad-eevee.png")
        gameOver = loadBitmap("game-over.png")

        try {
            val descriptor = screen.context.assets.openFd("music-gameover.mp3")
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

        val sad = eeveeSad ?: return
        canvas.drawBitmap(sad, (screen.width - sad.width) / 2f, 300f, paint)

        val message = gameOver ?: return
        canvas.drawBitmap(message, (screen.width - message.width)/2f, screen.height/3f + 300f, null)


        val textRepeat = "Tentar Novamente"
        canvas.drawText(textRepeat, screen.width / 2f, screen.height / 2 + 250f, paint)

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

    override fun onResume() {

    }

    override fun onPause() {
        music?.pause()
        music?.dispose()
    }


}
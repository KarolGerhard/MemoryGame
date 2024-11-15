package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var screen: Screen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Fonts.initializeFont(this)
        screen = Screen(this)
        screen?.setOnTouchListener(screen)
        setContentView(screen!!)
    }

    class Screen(private val context: Context): View(context), OnTouchListener {

        private var startTime = System.nanoTime()
        var scene: Scene = StartScene(this)

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val elapsedTime = (System.nanoTime() - startTime) / 1000000f
            startTime = System.nanoTime()

            canvas.drawColor(Color.WHITE)
            this.update(elapsedTime)
            this.render(canvas)

            invalidate()
        }

        private fun update(et: Float) {
            scene.update(et)
        }

        private fun render(canvas: Canvas) {
            scene.render(canvas)
        }

        override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
            val e = motionEvent ?: return false

            return scene.onTouch(e)
        }

        fun setEventResume() {
            scene.onResume()
        }

        fun setEventPause() {
            scene.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        screen?.setEventResume()
    }

    override fun onPause() {
        super.onPause()
        screen?.setEventPause()
    }
}
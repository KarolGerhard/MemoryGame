package br.com.akgs.memorygame

import android.graphics.Canvas
import android.view.MotionEvent

interface Scene {

    fun update(et: Float)
    fun render(canvas: Canvas)
    fun onTouch(e: MotionEvent): Boolean
    fun onResume()
    fun onPause()

}
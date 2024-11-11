package br.com.akgs.memorygame

import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent

class GameScene(screen: MainActivity.Screen) : Scene {

    private val cards: GameObject = Cards(screen.context, 400f, 300f)

    //    private val title: GameObject = Title(screen.context, 250f, 200f)
    private val title: GameObject = Title(screen.width / 2f, 250f)
    private var goArray: MutableList<GameObject> = mutableListOf()
    private var music: Music? = null

    init {
        try {
            val descriptor = screen.context.assets.openFd("music.mp3")
            music = Music(descriptor)
        } catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao carregar o áudio")
        }
        music?.setLooping(true)
        music?.setVolume(5f)
        music?.play()
        goArray.add(cards)
        goArray.add(title)
    }

    override fun update(et: Float) {
        for (go in goArray) {
            go.update(et)
        }
    }

    override fun render(canvas: Canvas) {
        for (go in goArray) {
            go.render(canvas)
        }
    }

    override fun onTouch(e: MotionEvent): Boolean {
        for (go in goArray) {
            go.handleEvent(e.action, e.x, e.y)
        }

        return true
    }

    override fun onResume() {

    }

    override fun onPause() {
        music?.pause()
        music?.dispose()
    }
}

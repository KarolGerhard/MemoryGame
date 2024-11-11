package br.com.akgs.memorygame

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log

class Music(descriptor: AssetFileDescriptor) {

    private val mp = MediaPlayer()
    private var isPrepared = false

    init {
        try {
            mp.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            mp.prepare()
            isPrepared = true
        }
        catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao carregar o áudio")
        }
    }

    fun play() {
        if (mp.isPlaying) return
        try {
            if (!isPrepared) mp.prepare()
            mp.start()
        }
        catch (e: Exception) {
            Log.d("App", e.message ?: "Algo ocorreu de errado ao iniciar o áudio")
        }
    }

    fun pause() {
        if (mp.isPlaying) mp.pause()
    }

    fun stop() {
        mp.stop()
        isPrepared = false
    }

    fun dispose() {
        if (mp.isPlaying) mp.stop()
        mp.release()
    }

    fun setLooping(isLooping: Boolean) {
        mp.isLooping = isLooping
    }

    fun setVolume(volume: Float) {
        mp.setVolume(volume, volume)
    }
}
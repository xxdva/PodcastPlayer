package com.example.myapplication.player

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PlayerController(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackId: String? = null

    var isPlaying by mutableStateOf(false)
        private set

    var positionMs by mutableIntStateOf(0)
        private set

    var durationMs by mutableIntStateOf(0)
        private set

    val progressFraction: Float
        get() = if (durationMs > 0) positionMs.toFloat() / durationMs else 0f

    fun play(trackId: String, @RawRes resId: Int) {
        if (trackId == currentTrackId && mediaPlayer != null) {
            mediaPlayer?.start()
            isPlaying = true
            return
        }

        mediaPlayer?.release()
        currentTrackId = trackId
        positionMs = 0
        mediaPlayer = MediaPlayer.create(context, resId)?.apply {
            isLooping = true
            setOnCompletionListener { this@PlayerController.isPlaying = false }
            durationMs = duration
            start()
        }
        isPlaying = true
    }

    fun togglePlayPause() {
        val player = mediaPlayer ?: return
        if (player.isPlaying) {
            player.pause()
            isPlaying = false
        } else {
            player.start()
            isPlaying = true
        }
    }

    fun seekTo(fraction: Float) {
        val player = mediaPlayer ?: return
        val target = (fraction.coerceIn(0f, 1f) * durationMs).toInt()
        player.seekTo(target)
        positionMs = target
    }

    fun syncPosition() {
        val player = mediaPlayer ?: return
        if (player.isPlaying) {
            positionMs = player.currentPosition
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentTrackId = null
        isPlaying = false
        positionMs = 0
        durationMs = 0
    }
}

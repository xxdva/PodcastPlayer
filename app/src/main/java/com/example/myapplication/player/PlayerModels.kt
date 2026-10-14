package com.example.myapplication.player

import androidx.compose.ui.graphics.Color

enum class AudioKind {
    PODCAST,
    AUDIOBOOK
}

data class AudioItem(
    val id: String,
    val title: String,
    val creator: String,
    val kind: AudioKind,
    val coverColor: Color,
    val totalMinutes: Int,
    val progress: Float
)

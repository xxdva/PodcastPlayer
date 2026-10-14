package com.example.myapplication.player

import androidx.compose.ui.graphics.Color

object MockLibrary {
    val items: List<AudioItem> = listOf(
        AudioItem(
            id = "1",
            title = "Невидимый фронт, эпизод 42",
            creator = "Радио Кортекс",
            kind = AudioKind.PODCAST,
            coverColor = Color(0xFF6750A4),
            totalMinutes = 48,
            progress = 0.62f
        ),
        AudioItem(
            id = "2",
            title = "Дюна",
            creator = "Фрэнк Герберт",
            kind = AudioKind.AUDIOBOOK,
            coverColor = Color(0xFFB4690E),
            totalMinutes = 1260,
            progress = 0.18f
        ),
        AudioItem(
            id = "3",
            title = "Технооптимист, эпизод 17",
            creator = "Digital Daily",
            kind = AudioKind.PODCAST,
            coverColor = Color(0xFF386A20),
            totalMinutes = 35,
            progress = 0f
        ),
        AudioItem(
            id = "4",
            title = "Мартин Иден",
            creator = "Джек Лондон",
            kind = AudioKind.AUDIOBOOK,
            coverColor = Color(0xFF8E4EC6),
            totalMinutes = 540,
            progress = 0.91f
        ),
        AudioItem(
            id = "5",
            title = "Утренний разбор, эпизод 101",
            creator = "Новости Плюс",
            kind = AudioKind.PODCAST,
            coverColor = Color(0xFF1D6B63),
            totalMinutes = 22,
            progress = 0.35f
        ),
        AudioItem(
            id = "6",
            title = "Метро 2033",
            creator = "Дмитрий Глуховский",
            kind = AudioKind.AUDIOBOOK,
            coverColor = Color(0xFF9E4A5A),
            totalMinutes = 780,
            progress = 0.5f
        )
    )
}

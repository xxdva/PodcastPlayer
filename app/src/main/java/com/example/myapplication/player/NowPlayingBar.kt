package com.example.myapplication.player

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun NowPlayingBar(
    item: AudioItem,
    isPlaying: Boolean,
    progressFraction: Float,
    onTogglePlaying: () -> Unit,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.creator,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                ExpressivePlayPauseButton(isPlaying = isPlaying, onClick = onTogglePlaying)
            }
            Spacer(modifier = Modifier.height(4.dp))

            var dragValue by remember { mutableStateOf<Float?>(null) }
            val sliderValue = dragValue ?: progressFraction.coerceIn(0f, 1f)
            Slider(
                value = sliderValue,
                onValueChange = { dragValue = it },
                onValueChangeFinished = {
                    dragValue?.let(onSeek)
                    dragValue = null
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ExpressivePlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    val cornerRadius by animateDpAsState(
        targetValue = if (isPlaying) 18.dp else 28.dp,
        animationSpec = tween(durationMillis = 250),
        label = "fabCornerRadius"
    )
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(cornerRadius),
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            contentDescription = if (isPlaying) "Пауза" else "Воспроизвести"
        )
    }
}

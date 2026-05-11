package com.example.costtracker.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SuccessOverlay(onDismiss: () -> Unit) {
    var visible by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )
    val exprId = remember { randomExpression() }

    LaunchedEffect(Unit) {
        delay(1200)
        visible = false
        delay(350)
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99FFF8F3))
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = exprId),
            contentDescription = "投喂成功",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(180.dp).scale(scale)
        )
    }
}

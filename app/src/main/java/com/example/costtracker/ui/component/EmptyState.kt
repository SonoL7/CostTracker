package com.example.costtracker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    val petIcon = remember { randomTitleIcon() }

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = petIcon), contentDescription = "萌宠",
            contentScale = ContentScale.Fit, modifier = Modifier.size(130.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Color(0xCCFFFFFF)).padding(horizontal = 24.dp, vertical = 14.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("喵～主人还没有记录哦", style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
                Spacer(Modifier.height(4.dp))
                Text("点击 + 开始投喂吧！", fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            }
        }

        Spacer(Modifier.height(8.dp))
        Text("🐾  🐾  🐾", fontSize = 16.sp)
    }
}

package com.example.costtracker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.costtracker.domain.model.ItemDisplay
import java.time.format.DateTimeFormatter

private val categoryColors = listOf(
    Color(0xFFFFF0F5), Color(0xFFF0F8FF), Color(0xFFFFF8E1),
    Color(0xFFF3E5F5), Color(0xFFE8F5E9), Color(0xFFFFF3E0),
    Color(0xFFFCE4EC), Color(0xFFE3F2FD), Color(0xFFF9FBE7),
)
private val catColorKeys = listOf(
    "餐饮","交通","购物","娱乐","零食","饮品","数码",
    "学习","储蓄","旅游","聚会","计划","奖金","采购","零花钱","目标","其他"
)
private fun colorFor(cat: String): Color {
    val idx = catColorKeys.indexOfFirst { cat.contains(it, true) }
    return categoryColors[idx.coerceIn(0, categoryColors.lastIndex)]
}

// Font sizes scale by column count to prevent text wrapping
private data class CardSizes(val price: TextUnit, val icon: Int, val name: TextUnit, val small: TextUnit)
private fun sizesFor(cols: Int) = when (cols) {
    2 -> CardSizes(26.sp, 48, 13.sp, 10.sp)
    3 -> CardSizes(22.sp, 42, 12.sp, 9.sp)
    4 -> CardSizes(19.sp, 36, 11.sp, 9.sp)
    else -> CardSizes(16.sp, 32, 10.sp, 8.sp)
}

@Composable
fun ItemCard(
    item: ItemDisplay,
    onClick: () -> Unit,
    cols: Int = 2,
    modifier: Modifier = Modifier
) {
    val sizes = remember(cols) { sizesFor(cols) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MM/dd") }
    val iconId = remember(item.id, item.category) { getItemCustomIcon(item.id) ?: getCategoryIcon(item.category) }
    val bg = remember(item.category) { colorFor(item.category) }
    val paddingDp = if (cols <= 2) 14 else if (cols <= 3) 10 else 8

    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(if (cols <= 2) 22.dp else 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (cols <= 2) 3.dp else 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(colors = listOf(bg.copy(alpha = 0.5f), Color.White)))
                .padding(paddingDp.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (iconId != null) {
                Image(painter = painterResource(id = iconId), contentDescription = item.category,
                    contentScale = ContentScale.Fit, modifier = Modifier.size(sizes.icon.dp))
            }
            Spacer(modifier = Modifier.height(if (cols <= 3) 4.dp else 2.dp))

            Text(item.name, fontSize = sizes.name, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface, maxLines = 1,
                overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            Text("${item.category} · ${item.purchaseDate.format(dateFormatter)}",
                fontSize = sizes.small, color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(if (cols <= 3) 6.dp else 2.dp))

            Text("¥%.2f".format(item.dailyAvgCost), fontSize = sizes.price, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary, maxLines = 1,
                overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            Text("/ 天", fontSize = sizes.small, fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f), maxLines = 1, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(if (cols <= 3) 5.dp else 2.dp))
            Text("总价 ¥%.0f · ${item.daysPassed}天".format(item.price),
                fontSize = sizes.small, color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, textAlign = TextAlign.Center)
        }
    }
}

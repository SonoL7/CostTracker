package com.example.costtracker.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.costtracker.R

private val defaultCategories = listOf(
    "餐饮", "交通", "购物", "娱乐", "零食", "饮品", "数码",
    "学习", "储蓄", "旅游", "聚会", "计划", "奖金", "采购", "零花钱", "目标", "其他",
    "休闲", "摄影", "歌舞", "烹饪", "艺术"
)

private val categoryColors = listOf(
    Color(0xFFFFF0F5), Color(0xFFF0F8FF), Color(0xFFFFF8E1), Color(0xFFF3E5F5),
    Color(0xFFE8F5E9), Color(0xFFFFF3E0), Color(0xFFFCE4EC), Color(0xFFE3F2FD),
    Color(0xFFF9FBE7), Color(0xFFF1F8E9), Color(0xFFFFEBEE), Color(0xFFF5F5F5),
    Color(0xFFE8EAF6), Color(0xFFFFF9C4), Color(0xFFF3E5AB), Color(0xFFE1F5FE),
    Color(0xFFFCE4D6), Color(0xFFD6EAF8), Color(0xFFFDEBD0), Color(0xFFE8DAEF),
    Color(0xFFD5F5E3), Color(0xFFFADBD8),
)
private fun colorFor(idx: Int): Color = categoryColors[idx % categoryColors.size]

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun CategoryGrid(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    allCategories: List<String>,
    onAddCategory: (String) -> Unit,
    onDeleteCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<String?>(null) }
    var newCategoryName by remember { mutableStateOf("") }
    var selectedIconIndex by remember { mutableStateOf(0) }
    // Only show categories with proper cat_* icons
    val displayCategories = (defaultCategories + allCategories)
        .distinct()
        .filter { getCategoryIcon(it) != null }

    Column(modifier = modifier.fillMaxWidth()) {
        Text("🏷️ 选择分类（长按可删除）", style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp))

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cols = ((maxWidth / 80.dp).toInt()).coerceIn(3, 5)
            val cellW = maxWidth / cols

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                displayCategories.forEachIndexed { index, cat ->
                    val isSelected = selectedCategory == cat
                    val bgColor = colorFor(index)
                    val iconId = getCategoryIcon(cat)
                    val canDelete = cat !in defaultCategories

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(cellW - 4.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else bgColor)
                            .then(if (isSelected) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)) else Modifier)
                            .combinedClickable(
                                onClick = { onCategorySelected(cat) },
                                onLongClick = { if (canDelete) showDeleteDialog = cat }
                            )
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                    ) {
                        Image(painter = painterResource(id = iconId!!), contentDescription = cat,
                            contentScale = ContentScale.Fit, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(cat, style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            maxLines = 1, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center,
                            fontSize = 9.sp)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(cellW - 4.dp)
                        .clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { showAddDialog = true }.padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Filled.Add, "新增", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("新增", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp)
                }
            }
        }
    }

    if (showAddDialog) {
        val iconPool = (0..25).map { i ->
            when (i) {
                0 -> R.drawable.icon_sel_00; 1 -> R.drawable.icon_sel_01; 2 -> R.drawable.icon_sel_02
                3 -> R.drawable.icon_sel_03; 4 -> R.drawable.icon_sel_04; 5 -> R.drawable.icon_sel_05
                6 -> R.drawable.icon_sel_06; 7 -> R.drawable.icon_sel_07; 8 -> R.drawable.icon_sel_08
                9 -> R.drawable.icon_sel_09; 10 -> R.drawable.icon_sel_10; 11 -> R.drawable.icon_sel_11
                12 -> R.drawable.icon_sel_12; 13 -> R.drawable.icon_sel_13; 14 -> R.drawable.icon_sel_14
                15 -> R.drawable.icon_sel_15; 16 -> R.drawable.icon_sel_16; 17 -> R.drawable.icon_sel_17
                18 -> R.drawable.icon_sel_18; 19 -> R.drawable.icon_sel_19; 20 -> R.drawable.icon_sel_20
                21 -> R.drawable.icon_sel_21; 22 -> R.drawable.icon_sel_22; 23 -> R.drawable.icon_sel_23
                24 -> R.drawable.icon_sel_24; 25 -> R.drawable.icon_sel_25
                else -> R.drawable.icon_sel_00
            }
        }
        AlertDialog(
            onDismissRequest = { showAddDialog = false }, containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp), title = { Text("✨ 新增分类") },
            text = {
                Column {
                    OutlinedTextField(value = newCategoryName, onValueChange = { newCategoryName = it },
                        label = { Text("分类名称") }, singleLine = true, shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("选择图标：", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        iconPool.forEachIndexed { idx, resId ->
                            val sel = idx == selectedIconIndex
                            Image(painter = painterResource(id = resId), contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(34.dp).clip(RoundedCornerShape(8.dp))
                                    .background(if (sel) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent)
                                    .clickable { selectedIconIndex = idx }.padding(2.dp))
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = {
                val t = newCategoryName.trim()
                if (t.isNotEmpty()) {
                    onAddCategory(t)
                    assignCategoryIcon(t, iconPool[selectedIconIndex])
                    onCategorySelected(t)
                    newCategoryName = ""; selectedIconIndex = 0; showAddDialog = false
                }
            }) { Text("添加", color = MaterialTheme.colorScheme.primary) } },
            dismissButton = { TextButton(onClick = { showAddDialog = false }) { Text("取消") } }
        )
    }

    if (showDeleteDialog != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null }, containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp), title = { Text("删除分类") },
            text = { Text("确定要删除「${showDeleteDialog ?: ""}」分类吗？") },
            confirmButton = { TextButton(onClick = { showDeleteDialog?.let { onDeleteCategory(it) }; showDeleteDialog = null }) { Text("删除", color = MaterialTheme.colorScheme.error) } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = null }) { Text("取消") } }
        )
    }
}

package com.example.costtracker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.costtracker.R
import kotlin.random.Random

// Title bar: Q版小窝素材 1-18
private val titleIcons = (1..18).map { i ->
    when (i) {
        1 -> R.drawable.title_1; 2 -> R.drawable.title_2; 3 -> R.drawable.title_3
        4 -> R.drawable.title_4; 5 -> R.drawable.title_5; 6 -> R.drawable.title_6
        7 -> R.drawable.title_7; 8 -> R.drawable.title_8; 9 -> R.drawable.title_9
        10 -> R.drawable.title_10; 11 -> R.drawable.title_11; 12 -> R.drawable.title_12
        13 -> R.drawable.title_13; 14 -> R.drawable.title_14; 15 -> R.drawable.title_15
        16 -> R.drawable.title_16; 17 -> R.drawable.title_17; 18 -> R.drawable.title_18
        else -> R.drawable.title_1
    }
}

// Top bar icons 1-10
private val barIcons = (1..10).map { i ->
    when (i) {
        1 -> R.drawable.bar_1; 2 -> R.drawable.bar_2; 3 -> R.drawable.bar_3
        4 -> R.drawable.bar_4; 5 -> R.drawable.bar_5; 6 -> R.drawable.bar_6
        7 -> R.drawable.bar_7; 8 -> R.drawable.bar_8; 9 -> R.drawable.bar_9
        10 -> R.drawable.bar_10
        else -> R.drawable.bar_1
    }
}

// Category icon map (22 categories)
val categoryIconMap = mapOf(
    "交通" to R.drawable.cat_traffic, "休闲" to R.drawable.cat_leisure,
    "储蓄" to R.drawable.cat_savings, "其他" to R.drawable.cat_other,
    "奖金" to R.drawable.cat_bonus, "娱乐" to R.drawable.cat_entertainment,
    "学习" to R.drawable.cat_study, "摄影" to R.drawable.cat_photo,
    "数码" to R.drawable.cat_digital, "旅游" to R.drawable.cat_travel,
    "歌舞" to R.drawable.cat_music, "烹饪" to R.drawable.cat_cooking,
    "目标" to R.drawable.cat_goal, "聚会" to R.drawable.cat_party,
    "艺术" to R.drawable.cat_art, "计划" to R.drawable.cat_plan,
    "购物" to R.drawable.cat_shopping, "采购" to R.drawable.cat_purchase,
    "零花钱" to R.drawable.cat_allowance, "零食" to R.drawable.cat_snacks,
    "餐饮" to R.drawable.cat_food, "饮品" to R.drawable.cat_drinks
)

// New category selection icons (26 from 新增分类备选素材)
private val selectionIcons = (0..25).map { i ->
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

// Expression images (1-23)
val expressionIcons = (1..23).map { i ->
    when (i) {
        1 -> R.drawable.expr_1; 2 -> R.drawable.expr_2; 3 -> R.drawable.expr_3
        4 -> R.drawable.expr_4; 5 -> R.drawable.expr_5; 6 -> R.drawable.expr_6
        7 -> R.drawable.expr_7; 8 -> R.drawable.expr_8; 9 -> R.drawable.expr_9
        10 -> R.drawable.expr_10; 11 -> R.drawable.expr_11; 12 -> R.drawable.expr_12
        13 -> R.drawable.expr_13; 14 -> R.drawable.expr_14; 15 -> R.drawable.expr_15
        16 -> R.drawable.expr_16; 17 -> R.drawable.expr_17; 18 -> R.drawable.expr_18
        19 -> R.drawable.expr_19; 20 -> R.drawable.expr_20; 21 -> R.drawable.expr_21
        22 -> R.drawable.expr_22; 23 -> R.drawable.expr_23
        else -> R.drawable.expr_1
    }
}

// User-assigned icons for custom categories
private val userCategoryIcons = mutableMapOf<String, Int>()

fun getCategoryIcon(name: String): Int? = userCategoryIcons[name] ?: categoryIconMap[name]
fun assignCategoryIcon(name: String, iconResId: Int) { userCategoryIcons[name] = iconResId }
fun removeCategoryIcon(name: String) { userCategoryIcons.remove(name) }

// Per-item custom icon overrides (lost on app restart)
private val itemCustomIcons = mutableMapOf<Long, Int>()
fun getItemCustomIcon(itemId: Long): Int? = itemCustomIcons[itemId]
fun setItemCustomIcon(itemId: Long, iconResId: Int) { itemCustomIcons[itemId] = iconResId }
fun removeItemCustomIcon(itemId: Long) { itemCustomIcons.remove(itemId) }

// All available icons for custom selection (cat_* + icon_sel_*)
val allSelectableIcons: List<Int> by lazy {
    categoryIconMap.values.toList().distinct() +
    (0..25).map { i ->
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
    }.distinct()
}

fun randomTitleIcon(): Int = titleIcons[Random.nextInt(titleIcons.size)]
fun randomBarIcon(): Int = barIcons[Random.nextInt(barIcons.size)]
fun randomExpression(): Int = expressionIcons[Random.nextInt(expressionIcons.size)]
fun randomSelectionIcon(): Int = selectionIcons[Random.nextInt(selectionIcons.size)]

@Composable
fun PetMascot(size: Dp = 100.dp, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = randomTitleIcon()), contentDescription = "萌宠",
            contentScale = ContentScale.Fit, modifier = Modifier.size(size))
    }
}

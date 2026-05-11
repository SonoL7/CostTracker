package com.example.costtracker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.costtracker.R
import java.time.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemForm(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    category: String,
    onCategoryChange: (String) -> Unit,
    categoryError: String?,
    allCategories: List<String>,
    onAddCategory: (String) -> Unit,
    purchaseDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    priceText: String,
    onPriceChange: (String) -> Unit,
    priceError: String?,
    onDeleteCategory: (String) -> Unit = {},
    customIconId: Int? = null,
    onCustomIconChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showIconPicker by remember { mutableStateOf(false) }
    val fieldShape = RoundedCornerShape(16.dp)
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            value = name, onValueChange = onNameChange,
            label = { Text("📝 物品名称") }, singleLine = true,
            isError = nameError != null, supportingText = nameError?.let { { Text(it) } },
            shape = fieldShape, colors = fieldColors, modifier = Modifier.fillMaxWidth()
        )

        // Custom icon button
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                onClick = { showIconPicker = true },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(36.dp)
            ) {
                if (customIconId != null) {
                    Image(painter = painterResource(id = customIconId), contentDescription = null,
                        contentScale = ContentScale.Fit, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text("🎨 自定义图标", fontSize = 12.sp)
            }
            if (customIconId != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Text("点击可更换", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CategoryGrid(
            selectedCategory = category,
            onCategorySelected = onCategoryChange,
            allCategories = allCategories,
            onAddCategory = onAddCategory,
            onDeleteCategory = onDeleteCategory
        )

        if (categoryError != null) {
            Text(categoryError, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(16.dp))
        DatePickerButton(selectedDate = purchaseDate, onDateSelected = onDateSelected, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = priceText,
            onValueChange = { if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d{0,2}$"))) onPriceChange(it) },
            label = { Text("💰 价格 (元)") }, singleLine = true,
            isError = priceError != null, supportingText = priceError?.let { { Text(it) } },
            shape = fieldShape, colors = fieldColors, prefix = { Text("¥") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth()
        )
    }

    if (showIconPicker) {
        AlertDialog(
            onDismissRequest = { showIconPicker = false }, containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp), title = { Text("🎨 选择自定义图标") },
            text = {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Add "clear" option
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp))
                            .background(if (customIconId == null) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent)
                            .clickable { onCustomIconChange(-1); showIconPicker = false }
                    ) { Text("✕", fontSize = 18.sp, modifier = Modifier.padding(4.dp)) }

                    allSelectableIcons.forEach { resId ->
                        val sel = customIconId == resId
                        Image(painter = painterResource(id = resId), contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp))
                                .background(if (sel) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent)
                                .clickable { onCustomIconChange(resId); showIconPicker = false }
                                .padding(2.dp))
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showIconPicker = false }) { Text("关闭") } }
        )
    }
}

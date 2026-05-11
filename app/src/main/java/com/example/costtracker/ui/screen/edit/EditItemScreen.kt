package com.example.costtracker.ui.screen.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.costtracker.R
import com.example.costtracker.ui.component.ItemForm
import com.example.costtracker.ui.component.randomBarIcon
import com.example.costtracker.ui.component.randomTitleIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditItemViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val titleIcon = remember { randomTitleIcon() }
    val petIcon = remember { randomBarIcon() }
    LaunchedEffect(uiState.isSaved, uiState.isDeleted) { if (uiState.isSaved || uiState.isDeleted) onNavigateBack() }

    if (uiState.isLoading) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) }; return }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.bg_main), contentDescription = null,
            contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize().background(Color(0xDDFFF8F3)))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = titleIcon), contentDescription = null,
                                contentScale = ContentScale.Fit, modifier = Modifier.size(32.dp))
                            Text(" 编辑记录", style = MaterialTheme.typography.titleLarge)
                        }
                    },
                    navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = MaterialTheme.colorScheme.onBackground) } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = MaterialTheme.colorScheme.onBackground)
                )
            }
        ) { innerPadding ->
            Column(Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp).verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color(0xDDFFFFFF)).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = petIcon), contentDescription = null,
                        contentScale = ContentScale.Fit, modifier = Modifier.size(52.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("修改一下再保存吧～", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(Modifier.height(16.dp))
                ItemForm(name = uiState.name, onNameChange = viewModel::onNameChange, nameError = uiState.nameError,
                    category = uiState.category, onCategoryChange = viewModel::onCategoryChange, categoryError = uiState.categoryError,
                    allCategories = uiState.allCategories, onAddCategory = viewModel::addCategory, onDeleteCategory = viewModel::deleteCategory,
                    purchaseDate = uiState.purchaseDate, onDateSelected = viewModel::onDateSelected,
                    priceText = uiState.priceText, onPriceChange = viewModel::onPriceChange, priceError = uiState.priceError,
                    customIconId = uiState.customIconId, onCustomIconChange = viewModel::onCustomIconChange)
                Spacer(Modifier.height(24.dp))
                Button(onClick = viewModel::save, enabled = !uiState.isSaving && !uiState.isDeleting, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text(if (uiState.isSaving) "保存中..." else "💾 保存", color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = { showDeleteDialog = true }, enabled = !uiState.isSaving && !uiState.isDeleting, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                    Text(if (uiState.isDeleting) "删除中..." else "🗑️ 删除此记录")
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
    if (showDeleteDialog) AlertDialog(
        onDismissRequest = { showDeleteDialog = false }, containerColor = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp),
        title = { Row(verticalAlignment = Alignment.CenterVertically) { Image(painter = painterResource(id = R.drawable.expr_6), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.size(44.dp)); Spacer(Modifier.width(8.dp)); Text("确认删除") } },
        text = { Text("删除后无法恢复，确定要删除吗？", color = MaterialTheme.colorScheme.onSurfaceVariant) },
        confirmButton = { TextButton(onClick = { showDeleteDialog = false; viewModel.delete() }) { Text("删除", color = MaterialTheme.colorScheme.error) } },
        dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("取消") } }
    )
}

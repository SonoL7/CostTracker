package com.example.costtracker.ui.screen.add

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.costtracker.R
import com.example.costtracker.ui.component.ItemForm
import com.example.costtracker.ui.component.SuccessOverlay
import com.example.costtracker.ui.component.randomBarIcon
import com.example.costtracker.ui.component.randomTitleIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val titleIcon = remember { randomTitleIcon() }
    val welcomeIcon = remember { randomBarIcon() }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved && !showSuccess) {
            showSuccess = true
        }
    }

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
                            Text(" 投喂小窝", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = MaterialTheme.colorScheme.onBackground)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = MaterialTheme.colorScheme.onBackground)
                )
            }
        ) { innerPadding ->
            Column(Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp).verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color(0xDDFFFFFF)).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = welcomeIcon), contentDescription = null,
                        contentScale = ContentScale.Fit, modifier = Modifier.size(60.dp))
                    Spacer(Modifier.width(12.dp))
                    Column { Text("今天买了什么好东西呀？", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface); Text("投喂给小窝，帮你算成本～", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                Spacer(Modifier.height(16.dp))
                ItemForm(name = uiState.name, onNameChange = viewModel::onNameChange, nameError = uiState.nameError,
                    category = uiState.category, onCategoryChange = viewModel::onCategoryChange, categoryError = uiState.categoryError,
                    allCategories = uiState.allCategories, onAddCategory = viewModel::addCategory, onDeleteCategory = viewModel::deleteCategory,
                    purchaseDate = uiState.purchaseDate, onDateSelected = viewModel::onDateSelected,
                    priceText = uiState.priceText, onPriceChange = viewModel::onPriceChange, priceError = uiState.priceError,
                    customIconId = uiState.customIconId, onCustomIconChange = viewModel::onCustomIconChange)
                Spacer(Modifier.height(24.dp))
                Button(onClick = viewModel::save, enabled = !uiState.isSaving,
                    modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text(if (uiState.isSaving) "保存中..." else "🐾 投喂到小窝", color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(Modifier.height(20.dp))
            }
        }

        if (showSuccess) {
            SuccessOverlay(onDismiss = { showSuccess = false; onNavigateBack() })
        }
    }
}

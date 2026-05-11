package com.example.costtracker.ui.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.costtracker.R
import com.example.costtracker.ui.component.EmptyState
import com.example.costtracker.ui.component.ItemCard
import com.example.costtracker.ui.component.randomBarIcon
import com.example.costtracker.ui.component.randomExpression
import com.example.costtracker.ui.component.randomTitleIcon

private fun columnCount(itemCount: Int): Int = when {
    itemCount <= 5 -> 2
    itemCount <= 10 -> 3
    itemCount <= 15 -> 4
    else -> 5
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val titleBarIcon = remember { randomTitleIcon() }
    val headerIcon = remember { randomBarIcon() }
    val aboutExpr = remember { randomExpression() }
    val cols = columnCount(uiState.items.size)
    var showAbout by remember { mutableStateOf(false) }

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
                            Image(painter = painterResource(id = titleBarIcon), contentDescription = "关于",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(32.dp).clickable { showAbout = true })
                            Spacer(Modifier.width(8.dp))
                            Text("物资小窝", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, titleContentColor = MaterialTheme.colorScheme.onBackground)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddClick, containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary, shape = CircleShape
                ) { Icon(Icons.Filled.Add, "投喂") }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when {
                    uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                    uiState.errorMessage != null -> Text(uiState.errorMessage!!, Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.error)
                    uiState.items.isEmpty() -> EmptyState()
                    else -> LazyVerticalGrid(
                        columns = GridCells.Fixed(cols),
                        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 88.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item(span = { GridItemSpan(cols) }) {
                            Row(
                                Modifier.fillMaxWidth().padding(4.dp).clip(RoundedCornerShape(24.dp))
                                    .background(Color(0xCCFFFFFF)).padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = headerIcon), contentDescription = null,
                                    contentScale = ContentScale.Fit, modifier = Modifier.size(56.dp))
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text("共 ${uiState.items.size} 件好物", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text("喵～主人真会省钱！", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                        items(items = uiState.items, key = { it.id }) { item ->
                            ItemCard(item = item, onClick = { onItemClick(item.id) }, cols = cols)
                        }
                    }
                }
            }
        }

        // About overlay
        if (showAbout) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCCFFF8F3))
                    .clickable { showAbout = false },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = aboutExpr),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "by San.（2026.05）",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

package com.example.costtracker.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.costtracker.domain.model.ItemDisplay
import com.example.costtracker.domain.usecase.ObserveItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemListUiState(
    val isLoading: Boolean = true,
    val items: List<ItemDisplay> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val observeItemsUseCase: ObserveItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListUiState())
    val uiState: StateFlow<ItemListUiState> = _uiState

    init {
        viewModelScope.launch {
            observeItemsUseCase()
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch { e ->
                    _uiState.value = ItemListUiState(
                        isLoading = false,
                        errorMessage = e.message ?: "加载失败"
                    )
                }
                .collect { items ->
                    _uiState.value = ItemListUiState(
                        isLoading = false,
                        items = items
                    )
                }
        }
    }
}

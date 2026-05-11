package com.example.costtracker.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.costtracker.domain.repository.ItemRepository
import com.example.costtracker.domain.usecase.AddItemUseCase
import com.example.costtracker.ui.component.setItemCustomIcon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

data class AddItemUiState(
    val name: String = "",
    val nameError: String? = null,
    val category: String = "",
    val categoryError: String? = null,
    val purchaseDate: LocalDate = LocalDate.now(),
    val priceText: String = "",
    val priceError: String? = null,
    val allCategories: List<String> = emptyList(),
    val customIconId: Int? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddItemUiState())
    val uiState: StateFlow<AddItemUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeCategories().collect { categories ->
                _uiState.update { it.copy(allCategories = categories) }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onCategoryChange(category: String) {
        _uiState.update { it.copy(category = category, categoryError = null) }
    }

    fun onPriceChange(price: String) {
        _uiState.update { it.copy(priceText = price, priceError = null) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(purchaseDate = date) }
    }

    fun onCustomIconChange(id: Int) {
        _uiState.update { it.copy(customIconId = if (id == -1) null else id) }
    }

    fun addCategory(name: String) {
        viewModelScope.launch { repository.addCategory(name) }
    }

    fun deleteCategory(name: String) {
        viewModelScope.launch { repository.deleteCategory(name) }
    }

    fun save() {
        val state = _uiState.value
        val name = state.name.trim()
        val category = state.category.trim()
        val priceText = state.priceText.trim()

        var hasError = false

        if (name.isEmpty()) {
            _uiState.update { it.copy(nameError = "名称不能为空") }
            hasError = true
        }

        if (category.isEmpty()) {
            _uiState.update { it.copy(categoryError = "分类不能为空") }
            hasError = true
        }

        val price = priceText.toDoubleOrNull()
        if (price == null || price <= 0) {
            _uiState.update { it.copy(priceError = "请输入有效的正数价格") }
            hasError = true
        }

        if (hasError) return

        _uiState.update { it.copy(isSaving = true) }

        viewModelScope.launch {
            val epochMillis = state.purchaseDate
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
            val newId = addItemUseCase(name, category, epochMillis, price!!)
            state.customIconId?.let { setItemCustomIcon(newId, it) }
            repository.addCategory(category)
            _uiState.update { it.copy(isSaving = false, isSaved = true) }
        }
    }
}

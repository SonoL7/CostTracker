package com.example.costtracker.domain.usecase

import com.example.costtracker.domain.model.ItemDisplay
import com.example.costtracker.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<List<ItemDisplay>> = repository.observeItems()
}

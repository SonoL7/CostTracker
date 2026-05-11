package com.example.costtracker.domain.usecase

import com.example.costtracker.domain.model.ItemDisplay
import com.example.costtracker.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(id: Long): ItemDisplay? = repository.getItem(id)
}

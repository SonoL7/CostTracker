package com.example.costtracker.domain.usecase

import com.example.costtracker.domain.repository.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteItem(id)
    }
}

package com.example.costtracker.domain.usecase

import com.example.costtracker.domain.repository.ItemRepository
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        category: String,
        purchaseDateEpoch: Long,
        price: Double
    ) = repository.updateItem(id, name, category, purchaseDateEpoch, price)
}

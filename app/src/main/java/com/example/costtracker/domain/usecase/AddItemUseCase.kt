package com.example.costtracker.domain.usecase

import com.example.costtracker.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(
        name: String,
        category: String,
        purchaseDateEpoch: Long,
        price: Double
    ): Long = repository.addItem(name, category, purchaseDateEpoch, price)
}

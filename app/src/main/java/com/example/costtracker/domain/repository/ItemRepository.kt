package com.example.costtracker.domain.repository

import com.example.costtracker.domain.model.ItemDisplay
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun observeItems(): Flow<List<ItemDisplay>>
    suspend fun getItem(id: Long): ItemDisplay?
    suspend fun addItem(name: String, category: String, purchaseDateEpoch: Long, price: Double): Long
    suspend fun updateItem(id: Long, name: String, category: String, purchaseDateEpoch: Long, price: Double)
    suspend fun deleteItem(id: Long)
    fun observeCategories(): Flow<List<String>>
    suspend fun addCategory(name: String)
    suspend fun deleteCategory(name: String)
}

package com.example.costtracker.data.repository

import com.example.costtracker.data.local.dao.CategoryDao
import com.example.costtracker.data.local.dao.ItemDao
import com.example.costtracker.data.local.entity.CategoryEntity
import com.example.costtracker.data.local.entity.ItemEntity
import com.example.costtracker.domain.model.ItemDisplay
import com.example.costtracker.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToLong

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val categoryDao: CategoryDao
) : ItemRepository {

    override fun observeItems(): Flow<List<ItemDisplay>> =
        itemDao.observeAll().map { entities ->
            entities.map { it.toDisplay() }
        }

    override suspend fun getItem(id: Long): ItemDisplay? =
        itemDao.getById(id)?.toDisplay()

    override suspend fun addItem(name: String, category: String, purchaseDateEpoch: Long, price: Double): Long {
        val entity = ItemEntity(
            name = name,
            category = category,
            purchaseDate = purchaseDateEpoch,
            price = price
        )
        return itemDao.insert(entity)
    }

    override suspend fun updateItem(id: Long, name: String, category: String, purchaseDateEpoch: Long, price: Double) {
        val entity = ItemEntity(
            id = id,
            name = name,
            category = category,
            purchaseDate = purchaseDateEpoch,
            price = price,
            updatedAt = System.currentTimeMillis()
        )
        itemDao.update(entity)
    }

    override suspend fun deleteItem(id: Long) = itemDao.deleteById(id)

    override fun observeCategories(): Flow<List<String>> =
        categoryDao.observeAll().map { entities ->
            entities.map { it.name }
        }

    override suspend fun addCategory(name: String) {
        val trimmed = name.trim()
        if (trimmed.isNotEmpty() && categoryDao.exists(trimmed) == 0) {
            categoryDao.insert(CategoryEntity(name = trimmed))
        }
    }

    override suspend fun deleteCategory(name: String) {
        categoryDao.deleteByName(name)
    }

    private fun ItemEntity.toDisplay(): ItemDisplay {
        val date = Instant.ofEpochMilli(purchaseDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val today = LocalDate.now()
        val days = maxOf(1L, ChronoUnit.DAYS.between(date, today))
        val avg = (price / days * 100).roundToLong() / 100.0
        return ItemDisplay(
            id = id,
            name = name,
            category = category,
            purchaseDate = date,
            price = price,
            daysPassed = days,
            dailyAvgCost = avg
        )
    }
}

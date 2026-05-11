package com.example.costtracker.domain.model

import java.time.LocalDate

data class ItemDisplay(
    val id: Long,
    val name: String,
    val category: String,
    val purchaseDate: LocalDate,
    val price: Double,
    val daysPassed: Long,
    val dailyAvgCost: Double
)

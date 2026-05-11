package com.example.costtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.costtracker.data.local.dao.CategoryDao
import com.example.costtracker.data.local.dao.ItemDao
import com.example.costtracker.data.local.entity.CategoryEntity
import com.example.costtracker.data.local.entity.ItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [ItemEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CostDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao

    class Callback @Inject constructor(
        private val databaseProvider: Provider<CostDatabase>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val categoryDao = databaseProvider.get().categoryDao()
                val defaults = listOf("食品", "交通", "数码", "服饰", "家居", "娱乐", "其他")
                defaults.forEachIndexed { index, name ->
                    categoryDao.insert(CategoryEntity(name = name, displayOrder = index))
                }
            }
        }
    }
}

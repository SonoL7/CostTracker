package com.example.costtracker.di

import com.example.costtracker.data.local.database.CostDatabase
import com.example.costtracker.data.local.dao.CategoryDao
import com.example.costtracker.data.local.dao.ItemDao
import com.example.costtracker.data.repository.ItemRepositoryImpl
import com.example.costtracker.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: android.app.Application,
        callback: CostDatabase.Callback
    ): CostDatabase {
        return androidx.room.Room.databaseBuilder(
            app,
            CostDatabase::class.java,
            "cost_tracker.db"
        )
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideItemDao(db: CostDatabase): ItemDao = db.itemDao()

    @Provides
    fun provideCategoryDao(db: CostDatabase): CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideItemRepository(impl: ItemRepositoryImpl): ItemRepository = impl
}

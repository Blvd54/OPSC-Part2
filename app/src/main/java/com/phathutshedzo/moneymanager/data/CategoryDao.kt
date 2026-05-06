package com.phathutshedzo.moneymanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories ORDER BY name")
    suspend fun getAllCategoriesList(): List<Category>

    @Insert
    suspend fun insert(category: Category)
}
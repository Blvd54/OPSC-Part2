package com.phathutshedzo.moneymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val date: Date,
    val description: String,
    val category: String,           // Changed to String instead of ID
    val photoPath: String? = null
)
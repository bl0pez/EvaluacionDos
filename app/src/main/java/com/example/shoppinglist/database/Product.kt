package com.example.shoppinglist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    var isCompleted: Boolean = false
)
package com.example.shoppinglist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY isCompleted DESC")
    fun getAll(): List<Product>

    @Update
    fun update(vararg product: Product)

    @Insert
    fun create(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)
}
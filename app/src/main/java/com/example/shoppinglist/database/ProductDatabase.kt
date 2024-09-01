package com.example.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INTANCE: ProductDatabase? = null

        fun getDatabase(context:Context): ProductDatabase {
            return INTANCE ?: synchronized(this) {
                INTANCE ?: buildDatabase(context).also { INTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ProductDatabase::class.java, "pruduct_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
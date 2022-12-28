package com.example.zaanzainmerchant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zaanzainmerchant.database.productlist.ProductListDao
import com.example.zaanzainmerchant.database.productlist.RoomProductList

@Database(entities = [RoomProductList::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productListDao(): ProductListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
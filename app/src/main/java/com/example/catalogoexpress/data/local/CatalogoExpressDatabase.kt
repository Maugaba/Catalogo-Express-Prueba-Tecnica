package com.example.catalogoexpress.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class CatalogoExpressDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

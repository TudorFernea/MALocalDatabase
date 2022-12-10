package com.example.localdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class StockDatabase: RoomDatabase() {

    abstract fun stockDAO(): StockDAO

    companion object{
        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getDatabase(context: Context): StockDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stock_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
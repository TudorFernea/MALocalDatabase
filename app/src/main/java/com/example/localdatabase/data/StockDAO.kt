package com.example.localdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StockDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStock(stock: Stock)

    @Query("SELECT * FROM stock_table ORDER BY id ASC")
    fun getStocks(): LiveData<List<Stock>>

    @Query("DELETE FROM stock_table WHERE id = :id")
    suspend fun deleteStock(id: Int)

    @Query("SELECT * FROM stock_table WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Stock

    @Update
    suspend fun updateStock(stock: Stock)

}
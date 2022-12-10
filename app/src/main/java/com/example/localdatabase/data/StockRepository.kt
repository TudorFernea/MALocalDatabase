package com.example.localdatabase.data

import androidx.lifecycle.LiveData

class StockRepository(private val stockDAO: StockDAO) {

    val getStocks: LiveData<List<Stock>> = stockDAO.getStocks()

    suspend fun addStock(stock: Stock){
        stockDAO.addStock(stock)
    }

    suspend fun deleteStock(id: Int){
        stockDAO.deleteStock(id)
    }

    suspend fun getById(id: Int): Stock{
        return stockDAO.getById(id)
    }

    suspend fun updateStock(stock: Stock){
        stockDAO.updateStock(stock)
    }

}
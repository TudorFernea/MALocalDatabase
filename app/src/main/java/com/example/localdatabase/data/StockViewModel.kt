package com.example.localdatabase.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockViewModel(application: Application): AndroidViewModel(application) {

    val getStocks: LiveData<List<Stock>>
    private val repository: StockRepository

    init{
        val stockDAO: StockDAO = StockDatabase.getDatabase(application).stockDAO()
        repository = StockRepository(stockDAO)
        getStocks = repository.getStocks
    }

    fun addStock(stock: Stock){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.addStock(stock)
        }
    }

    fun deleteStock(id: Int){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.deleteStock(id)
        }
    }
    fun getById(id: Int): LiveData<Stock>{
        val result = MutableLiveData<Stock>()
        viewModelScope.launch ( Dispatchers.IO ){
            val returned = repository.getById(id)
            result.postValue(returned)
        }
        return result
    }

    fun updateStock(stock: Stock){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.updateStock(stock)
        }
    }

}
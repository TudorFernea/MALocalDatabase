package com.example.localdatabase

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.localdatabase.R
import com.example.localdatabase.data.Stock
import com.example.localdatabase.data.StockViewModel
import com.example.localdatabase.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_add.*
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_item_detail.detail_toolbar

class ItemAddActivity : AppCompatActivity() {

    private lateinit var mStockViewModel: StockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_add)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mStockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        btnAdd.setOnClickListener{

            val symbol = edSymbol.text.toString()
            val desc = edDesc.text.toString()
            val buy_price = edBuy.text.toString().toDoubleOrNull()
            val sell_price = edSell.text.toString().toDoubleOrNull()

            if(validate(symbol, desc, buy_price, sell_price)) {

                val stock: Stock = Stock(
                    0,
                    symbol,
                    desc,
                    buy_price,
                    sell_price
                )
                mStockViewModel.addStock(stock)
                Toast.makeText(this, "Added stock", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Invalid fields", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    }

    private fun validate(symbol: String, desc: String, buy_price: Double?, sell_price: Double?): Boolean{
        return !(TextUtils.isEmpty(symbol) && buy_price == null && sell_price == null)
    }

}
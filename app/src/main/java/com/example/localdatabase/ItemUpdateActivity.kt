package com.example.localdatabase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.ViewModelProvider
import com.example.localdatabase.R
import com.example.localdatabase.data.Stock
import com.example.localdatabase.data.StockViewModel
import com.example.localdatabase.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_add.*
import kotlinx.android.synthetic.main.activity_item_delete.*
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_item_detail.detail_toolbar
import kotlinx.android.synthetic.main.activity_item_update.*

class ItemUpdateActivity: AppCompatActivity()  {

    private lateinit var mStockViewModel: StockViewModel
    private var stock: Stock? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_update)
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mStockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        val id = intent.getIntExtra(ARG_ITEM_ID, 0)

        mStockViewModel.getById(id).observe(this, { st ->
            stock = st;

            tvUpdate.text = "Update " + stock?.symbol;

            edUpdSymbol.setText(stock?.symbol)
            edUpdDesc.setText(stock?.desc);
            edUpdBuy.setText(stock?.buy_price.toString());
            edUpdSell.setText(stock?.sell_price.toString());
        })

        btnUpdate.setOnClickListener{

            val symbol = edUpdSymbol.text.toString()
            val desc = edUpdDesc.text.toString()
            val buy_price = edUpdBuy.text.toString().toDoubleOrNull()
            val sell_price = edUpdSell.text.toString().toDoubleOrNull()

            if(validate(symbol, desc, buy_price, sell_price)) {

                val updatedStock: Stock = Stock(
                    id,
                    symbol,
                    desc,
                    buy_price,
                    sell_price
                )
                mStockViewModel.updateStock(updatedStock)
                Toast.makeText(this, "Updated stock", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Invalid fields", Toast.LENGTH_LONG).show()
                finish()
            }

        };

    }



    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra(ARG_ITEM_ID, 0)

        mStockViewModel.getById(id).observe(this, { st ->
            stock = st;

            tvUpdate.text = "Update " + st.symbol;

            edUpdSymbol.setText(stock?.symbol)
            edUpdDesc.setText(stock?.desc);
            edUpdBuy.setText(stock?.buy_price.toString());
            edUpdSell.setText(stock?.sell_price.toString());
        })
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                NavUtils.navigateUpTo(this, Intent(this, ItemDetailActivity::class.java).apply{
                    putExtra(ItemUpdateActivity.ARG_ITEM_ID,
                        intent.getIntExtra(ItemUpdateActivity.ARG_ITEM_ID, 0))
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun validate(symbol: String, desc: String, buy_price: Double?, sell_price: Double?): Boolean{
        return !(TextUtils.isEmpty(symbol) && buy_price == null && sell_price == null)
    }

    companion object {
        const val ARG_ITEM_ID = "id"
    }
}
package com.example.localdatabase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localdatabase.R
import com.example.localdatabase.data.Stock
import com.example.localdatabase.data.StockDatabase
import com.example.localdatabase.data.StockViewModel
import com.example.localdatabase.dummy.DummyContent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*


class ItemListActivity : AppCompatActivity() {


  private lateinit var mStockViewModel: StockViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_item_list)

    setSupportActionBar(toolbar)
    toolbar.title = title

    fab.setOnClickListener {
      val intent = Intent(this, ItemAddActivity::class.java)
      startActivity(intent)
    }

    setupRecyclerView(item_list)

    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val adapter: SimpleItemRecyclerViewAdapter = item_list.adapter as SimpleItemRecyclerViewAdapter

        val deletedStock: Stock = adapter.stockList[viewHolder.adapterPosition]

        val intent = Intent(viewHolder.itemView.context, ItemRemoveActivity::class.java).apply{
          putExtra(ItemRemoveActivity.ARG_ITEM_ID, deletedStock.id)
        }

        startActivity(intent)
        item_list.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
      }

    }).attachToRecyclerView(item_list)

  }

  override fun onResume() {
    super.onResume()
    item_list.adapter?.notifyDataSetChanged()
  }

  private fun setupRecyclerView(recyclerView: RecyclerView) {
    val adapter = SimpleItemRecyclerViewAdapter()
    recyclerView.adapter = adapter
    mStockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)
    mStockViewModel.getStocks.observe(this, Observer { stocks ->
     adapter.setList(stocks)
    })
  }

  inner class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
  {

    var stockList: List<Stock> = emptyList()

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
      val item = v.tag as Stock
      val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
      }
      v.context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_list_content, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = stockList[position]
      holder.symbView.text = item.symbol
      holder.sellView.text = "S:"+ item.sell_price.toString()
      holder.buyView.text = "B: "+ item.buy_price.toString()

      with(holder.itemView) {
        tag = item
        setOnClickListener(onClickListener)
      }
    }

    override fun getItemCount() = stockList.size

    fun setList(stocks: List<Stock>){
      this.stockList = stocks
      notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val symbView: TextView = view.symb_text
      val sellView: TextView = view.sell_text
      val buyView: TextView = view.buy_text
    }
  }

}


package com.example.localdatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.localdatabase.R
import com.example.localdatabase.data.Stock
import com.example.localdatabase.data.StockViewModel
import com.example.localdatabase.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

class ItemDetailFragment : Fragment() {

  private var item: Stock? = null
  private lateinit var mStockViewModel: StockViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mStockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

    arguments?.let {
      if (it.containsKey(ARG_ITEM_ID)) {
        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        val id = it.getInt(ARG_ITEM_ID)
        mStockViewModel.getById(id).observe(this,{
            stock-> item = stock;
          activity?.toolbar_layout?.title = item?.symbol;
        })
      }
    }
  }

  override fun onResume() {
    super.onResume()
    arguments?.let {
      if (it.containsKey(ARG_ITEM_ID)) {
        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        val id = it.getInt(ARG_ITEM_ID)
        mStockViewModel.getById(id).observe(this,{
            stock-> item = stock;
          activity?.toolbar_layout?.title = item?.symbol;
          item_detail.text = item?.desc + "\n"+ "Buy price: " + item?.buy_price + "\n" + "Sell price: " + item?.sell_price;
        })
        }
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val rootView = inflater.inflate(R.layout.item_detail, container, false)

    item?.let {
      rootView.item_detail.text = it.desc + "\n" + "Buy price: " + it.buy_price + "\n" + "Sell price: " + it.sell_price
    }
    return rootView
  }

  companion object {
    const val ARG_ITEM_ID = "id"
  }
}

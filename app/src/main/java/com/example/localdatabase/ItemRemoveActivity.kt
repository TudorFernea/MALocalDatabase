package com.example.localdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.localdatabase.R
import com.example.localdatabase.data.StockViewModel
import com.example.localdatabase.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_add.*
import kotlinx.android.synthetic.main.activity_item_delete.*
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_item_detail.detail_toolbar

class ItemRemoveActivity : AppCompatActivity() {

    private lateinit var mStockViewModel: StockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_delete)

        val id = intent.getIntExtra(ARG_ITEM_ID, 0)

        mStockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        tvConfirm.text = "Are you sure you want to remove this stock ?"
        btnRemoveNo.setOnClickListener{
            finish()
        }

        btnRemoveYes.setOnClickListener {
            mStockViewModel.deleteStock(id)
            finish()
        }

    }
    companion object {
        const val ARG_ITEM_ID = "id"
    }
}
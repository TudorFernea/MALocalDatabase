package com.example.localdatabase.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "stock_table")
data class Stock(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val symbol: String,
    val desc: String,
    val buy_price: Double?,
    val sell_price: Double?

)

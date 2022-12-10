package com.example.localdatabase.dummy

import java.util.*

object DummyContent {

  val ITEMS: MutableList<Stock> = ArrayList()

  val ITEM_MAP: MutableMap<String, Stock> = HashMap()

  private const val COUNT = 1

  init {
    // Add some sample items.
    for (i in 1..COUNT) {
      addItem(createDummyItem(i))
    }
  }

  public fun addItem(item: Stock) {
    ITEMS.add(item)
    ITEM_MAP[item.symbol] = item
  }

  public fun removeItem(symbol: String){
    val Stock = ITEM_MAP[symbol]
    ITEMS.remove(Stock)
    ITEM_MAP.remove(symbol)
  }

  public fun updateItem(symbol: String, desc:String, buy_price: Int, sell_price: Int){
    val Stock = ITEM_MAP[symbol]
    Stock?.desc = desc
    Stock?.buy_price = buy_price
    Stock?.sell_price = sell_price
  }

  private fun createDummyItem(position: Int): Stock {
    return Stock(   "APL", makeDetails(position),10,20)
  }

  private fun makeDetails(position: Int): String {
    val builder = StringBuilder()
    builder.append("Apple")
    for (i in 0 until position) {
      builder.append("\nMobile devices producer")
    }
    return builder.toString()
  }

  data class Stock(val symbol: String, var desc: String, var buy_price: Int, var sell_price: Int) {
    override fun toString(): String = desc
  }
}

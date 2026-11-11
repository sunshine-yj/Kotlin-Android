package com.androidbook.bookmarket.order


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.androidbook.bookmarket.R


import com.androidbook.bookmarket.model.Book
import com.androidbook.bookmarket.model.CartRepository.cartBooks

class OrderAdapter(private val context: Context) : RecyclerView.Adapter<OrderAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderTitle: TextView = itemView.findViewById(R.id.title)
        var orderid: TextView = itemView.findViewById(R.id.id)
        var rderPrice: TextView = itemView.findViewById(R.id.price)
        var rderQuantity: TextView = itemView.findViewById(R.id.quantity)
        var rderSum: TextView = itemView.findViewById(R.id.sum)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_order_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val book: Book = cartBooks.get(position)
        val str : String

        holder.orderTitle.setText(book.name)
        holder.orderid.setText(book.bookid)
        holder.rderPrice.setText(book.price.toString())
        holder.rderQuantity.setText(book.quantity.toString())
        holder.rderSum.setText((book.price * book.quantity).toString())
    }

    override fun getItemCount(): Int {
        return cartBooks.size
    }
}
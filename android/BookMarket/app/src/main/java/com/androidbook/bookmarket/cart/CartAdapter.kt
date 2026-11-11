package com.androidbook.bookmarket.cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.androidbook.bookmarket.R
import com.androidbook.bookmarket.model.Book
import com.androidbook.bookmarket.model.CartRepository.cartBooks

class CartAdapter( private val context: Context ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private lateinit  var selectChangedListener: CartAdapter.OnSelectChangedListener

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cartPicture: ImageView = itemView.findViewById(R.id.cart_pic)
        var cartTitle: TextView = itemView.findViewById(R.id.title)
        var cartPrice: TextView = itemView.findViewById(R.id.price)
        var cartParentLayout: CardView = itemView.findViewById(R.id.cart_parent_layout)
        var cartDelete: ImageButton = itemView.findViewById(R.id.cart_delete)
        var cartCheckBox: CheckBox = itemView.findViewById(R.id.cart_item_checkbox)
        var cartQuantity: TextView = itemView.findViewById(R.id.quantity)
        var cartSum: TextView = itemView.findViewById(R.id.sum)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder( holder: CartViewHolder, position: Int) {

        val book: Book = cartBooks.get(position)

        holder.cartTitle.setText(book.name)
        holder.cartPrice.setText(book.price.toString())
        holder.cartCheckBox.isChecked = (book.isCheck)
        holder.cartQuantity.setText(book.quantity.toString())
        holder.cartSum.setText((book.price * book.quantity).toString())

        when (book.bookid) {
            "BOOK1234" -> {
                holder.cartPicture.setImageResource(R.drawable.books_book1)
            }
            "BOOK1235" -> {
                holder.cartPicture.setImageResource(R.drawable.books_book2)
            }
            "BOOK1236" -> {
                holder.cartPicture.setImageResource(R.drawable.books_book3)
            }
            "BOOK1237" -> {
                holder.cartPicture.setImageResource(R.drawable.books_book4)
            }
        }

        holder.cartCheckBox.setOnClickListener {
            book.isCheck = holder.cartCheckBox.isChecked()
            selectChangedListener.onSelectChanged(cartBooks)
        }

        holder.cartDelete.setOnClickListener{
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("도서 삭제")
            alertDialog.setMessage("선택한 도서를 삭제하겠습니까?")
            alertDialog.setIcon(R.drawable.icon_dialog)

            val listener  = DialogInterface.OnClickListener { dialogInterface, i ->
                when(i){
                    DialogInterface.BUTTON_POSITIVE -> {
                        cartBooks.remove(book)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, cartBooks.size)
                        selectChangedListener.onSelectChanged(cartBooks)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialogInterface.cancel()
                    }
                }
            }

            alertDialog.setPositiveButton("예", listener)
            alertDialog.setNegativeButton("아니요", listener)

            alertDialog.show()
        }

    }

    override fun getItemCount(): Int {
        return cartBooks.size
    }

    interface OnSelectChangedListener {
        fun onSelectChanged(item : ArrayList<Book>)
    }

    fun setOnSelectChangedListener(listener: OnSelectChangedListener) {
        selectChangedListener = listener
    }

}
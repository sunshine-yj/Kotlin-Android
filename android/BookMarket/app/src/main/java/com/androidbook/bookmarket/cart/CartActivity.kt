package com.androidbook.bookmarket.cart

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.androidbook.bookmarket.R
import com.androidbook.bookmarket.model.Book
import com.androidbook.bookmarket.model.CartRepository.cartBooks
import com.androidbook.bookmarket.model.CartRepository.grandTotalCartItems
import com.androidbook.bookmarket.order.ShippingActivity

class CartActivity : AppCompatActivity() {

    lateinit var cartAdapter: CartAdapter
    lateinit var allChooseCheckBox : CheckBox
    var  cartTotalPriceObj: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cart)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

         cartTotalPriceObj  = findViewById(R.id.cart_total_price)
         cartTotalPriceObj?.text = grandTotalCartItems().toString()

        val orderButton : Button = findViewById(R.id.cart_order_button)

        orderButton.setOnClickListener {
            val intent = Intent(this@CartActivity, ShippingActivity::class.java)
            startActivity(intent)
        }

        val recyclerviewCart : RecyclerView = findViewById(R.id.cart_recyclerview)
        cartAdapter =  CartAdapter(this )
        recyclerviewCart.adapter = cartAdapter
        recyclerviewCart.layoutManager = LinearLayoutManager(this)

        cartAdapter.setOnSelectChangedListener(object : CartAdapter.OnSelectChangedListener {
            override fun onSelectChanged(item: ArrayList<Book>) {
                refreshRecyclerView()
            }
        })

        allChooseCheckBox = findViewById(R.id.cart_selectall_checkbox)

        allChooseCheckBox.setOnClickListener { v ->
            if ((v as CheckBox).isChecked) {
                doSelectAll()
            } else {
                doSelectNone()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
            android.R.id.home->{
                onBackPressed()
            }
            R.id.order_button->{
                val intent = Intent(this@CartActivity, ShippingActivity::class.java)
                startActivity(intent)
            }

            R.id.delete_button-> {
                if( cartAdapter.getItemCount()>0) {
                    val alertDialog = AlertDialog.Builder(this@CartActivity)
                    alertDialog.setTitle("장바구니 삭제")
                    alertDialog.setMessage("장바구니를 비우겠습니까?")
                    alertDialog.setIcon(R.drawable.icon_dialog)

                    val listener = DialogInterface.OnClickListener { dialogInterface, i ->
                        when (i) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                cartBooks.clear()
                                refreshRecyclerView()
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
                else Toast.makeText(this, "장바구니가 비어있습니다.", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun doSelectAll()  {
        val num = cartAdapter.getItemCount()

        if (num != null && num > 0) {
            var isChanged = false
            for (i in 0.. num-1) {
                val item: Book = cartBooks.get(i)

                if (!item.isCheck) {
                    item.isCheck = true
                    isChanged = true
                }
            }
            if (isChanged) {
                refreshRecyclerView()
            }
        }
    }


    fun doSelectNone() {
        val num = cartAdapter.getItemCount()
        if (num != null && num > 0) {
            var isChanged = false
            for (i in 0.. num-1) {
                val item: Book = cartBooks.get(i)
                if (item.isCheck) {
                    item.isCheck = false
                    isChanged = true
                }
            }
            if (isChanged) {
                refreshRecyclerView()
            }
        }
    }

    private fun refreshRecyclerView() {
        cartAdapter.notifyItemRangeRemoved(0, cartBooks.size)
        cartTotalPriceObj?.text = grandTotalCartItems().toString()
        setSelectAllCheckBoxState()
    }

    private fun setSelectAllCheckBoxState() {

        if (cartBooks == null) {
            allChooseCheckBox.setChecked(false)
            return
        }
        for (i in cartBooks.indices) {
            if (!cartBooks[i].isCheck) {
                allChooseCheckBox.setChecked(false)
                return
            }
        }
        allChooseCheckBox.setChecked(true)
    }
}
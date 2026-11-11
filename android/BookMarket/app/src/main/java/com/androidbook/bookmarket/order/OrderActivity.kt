package com.androidbook.bookmarket.order

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidbook.bookmarket.MainActivity
import com.androidbook.bookmarket.R
import com.androidbook.bookmarket.model.CartRepository
import com.androidbook.bookmarket.model.CartRepository.grandTotalCartItems

class OrderActivity : AppCompatActivity() {
    lateinit  var orderTotalObj: TextView
    lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order )

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        var intent = getIntent()
        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")
        val phone = intent.getStringExtra("phone")
        val zipcode = intent.getStringExtra("zipcode")
        val address = intent.getStringExtra("address")

        val tvDate: TextView = findViewById(R.id.textViewDate)
        val tvName: TextView = findViewById(R.id.textViewName)
        val tvZipcode: TextView = findViewById(R.id.textViewZipcode)
        val tvAddress: TextView = findViewById(R.id.textViewAddress)

        tvDate.setText("배송일 : " + date)
        tvName.setText("성명 : " + name + "(" + phone + ")")
        tvZipcode.setText("우편번호 : " + zipcode)
        tvAddress.setText("주소 : " + address)

        orderTotalObj = findViewById(R.id.textViewTotal)
        orderTotalObj.text = grandTotalCartItems().toString()

        val recyclerviewCart: RecyclerView = findViewById(R.id.order_recyclerview)
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        recyclerviewCart.addItemDecoration(decoration)
        orderAdapter = OrderAdapter(this)
        recyclerviewCart.adapter = orderAdapter
        recyclerviewCart.layoutManager = LinearLayoutManager(this)

        val orderCancel: Button = findViewById(R.id.buttonCancel)
        val orderSubmit: Button = findViewById(R.id.buttonSubmit)

        orderSubmit.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this@OrderActivity)
            alertDialog.setTitle("도서 주문 완료")
            alertDialog.setMessage("도서를 주문해 주셔서 감사합니다.")
            alertDialog.setIcon(R.drawable.icon_order)

            val listener = DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        CartRepository.cartBooks.clear()
                        val intent = Intent(this@OrderActivity, MainActivity::class.java)
                        startActivity(intent)
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

        orderCancel.setOnClickListener {
            orderSubmit.setOnClickListener {
                val alertDialog = AlertDialog.Builder(this@OrderActivity)
                alertDialog.setTitle("도서 주문 취소")
                alertDialog.setMessage("도서 주문을 취소합니다.")
                alertDialog.setIcon(R.drawable.icon_order)

                val listener = DialogInterface.OnClickListener { dialogInterface, i ->
                    when (i) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            CartRepository.cartBooks.clear()
                            val intent = Intent(this@OrderActivity, MainActivity::class.java)
                            startActivity(intent)
                        }

                    }
                }
                alertDialog.setPositiveButton("예", listener)
                alertDialog.show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
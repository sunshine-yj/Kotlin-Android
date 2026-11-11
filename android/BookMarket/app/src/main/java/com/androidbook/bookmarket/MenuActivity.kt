package com.androidbook.bookmarket

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onBookListClick(view: View?){

        Toast.makeText(getApplicationContext(),"도서목록 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }
    fun onEBookClick(view: View?){
        Toast.makeText(getApplicationContext(),"E-Book 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }

    fun onCustomerInquiryClick(view: View?){
        Toast.makeText(getApplicationContext(),"고객문의 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }

    fun onMyPageClick(view: View?){
        Toast.makeText(getApplicationContext(),"나의페이지 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }

    fun onMarketInfoClick(view: View?){
        Toast.makeText(getApplicationContext(),"마켓정보 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }
    fun onSettingClick(view: View?){
        Toast.makeText(getApplicationContext(),"설정 버튼을 클릭하였습니다",Toast.LENGTH_LONG).show()
    }
    fun onBannerClick(view: View?){
        val menuObj : ImageView = findViewById(R.id.imageView2)
        if (index %2 == 0) menuObj.setImageResource(R.drawable.menu_banner2)
        else menuObj.setImageResource(R.drawable.menu_banner1)
        index++
    }


}
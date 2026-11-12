package com.androidbook.bookmarket.mypage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R

class MyPageActivity : AppCompatActivity() {
    lateinit var menoObj : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mypage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mypage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        menoObj = findViewById(R.id.meno)

        menoObj.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
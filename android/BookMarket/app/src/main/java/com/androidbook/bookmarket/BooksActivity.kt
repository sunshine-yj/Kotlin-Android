package com.androidbook.bookmarket

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BooksActivity : AppCompatActivity(){

    lateinit var book1Obj : ImageView
    lateinit var book2Obj : ImageView
    lateinit var book3Obj : ImageView
    lateinit var book4Obj : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_books)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.books)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        book1Obj = findViewById(R.id.book1)
        book2Obj = findViewById(R.id.book2)
        book3Obj = findViewById(R.id.book3)
        book4Obj = findViewById(R.id.book4)


        book1Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "쉽게 배우는 알고리즘", Toast.LENGTH_SHORT).show()
        }
        book2Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "난생 처음 MySQL", Toast.LENGTH_SHORT).show()
        }
        book3Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "쉽게 배우는 PHP 웹 프로그래밍", Toast.LENGTH_SHORT).show()
        }
        book4Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "미라클 HTML5 + CSS3 + 자바스크립트", Toast.LENGTH_SHORT).show()
        }

    }

}
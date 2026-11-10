package com.example.tablelayoutproject

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var etObjA: EditText
    lateinit var etObjB:EditText
    lateinit var btObj: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etObjA = findViewById(R.id.editText1);
        etObjB = findViewById(R.id.editText2);
        btObj = findViewById(R.id.button);
    }

    fun onClickChoice(view: View?) {
        val partA = etObjA.text.toString().toInt()
        val partB = etObjB.text.toString().toInt()
        if (partA==2 && partB==5) Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_SHORT).show();
    }
}
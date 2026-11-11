package com.example.intentfilterproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etObj01 : EditText = findViewById(R.id.editText1)
        val etObj02 : EditText = findViewById(R.id.editText2)
        val etObj03 : EditText = findViewById(R.id.editText3)
        val email = etObj01.text.toString()
        val subject = etObj02.text.toString()
        val content = etObj03.text.toString()

        val button : Button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(etObj01.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, etObj02.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, etObj03.text.toString())
            val intent2 = Intent.createChooser(intent, "앱을 선택하세요")
            startActivity(intent2)
        }
    }
}
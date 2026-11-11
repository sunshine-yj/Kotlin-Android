package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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
            BookItem1()
        }
        book2Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "난생 처음 MySQL", Toast.LENGTH_SHORT).show()
            BookItem2()
        }
        book3Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "쉽게 배우는 PHP 웹 프로그래밍", Toast.LENGTH_SHORT).show()
            BookItem3()
        }
        book4Obj.setOnClickListener {
            Toast.makeText(getApplicationContext(), "미라클 HTML5 + CSS3 + 자바스크립트", Toast.LENGTH_SHORT).show()
            BookItem4()
        }
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    fun BookItem1(){
        val intent : Intent = Intent(this, BookActivity::class.java)
        intent.putExtra("bookid", "BOOK1234")
        intent.putExtra("name", "쉽게 배우는 알고리즘")
        intent.putExtra("price", "32000")
        intent.putExtra("date", "2024-01-03")
        intent.putExtra("writer", "문병로")
        intent.putExtra("description", "알고리즘에 대한 지식을 기반으로 제대로 된 프로그래밍을 하는 이들뿐 아니라, 알고리즘 속에 깃든 여러 가지 생각하는 방법, 자료구조, 테크닉을 통해 체계적으로 생각하는 훈련을 하고자 하는 모든 이를 대상으로 한다. ")
        intent.putExtra("category", "IT/모바일")
        startActivity(intent)

    }
    fun BookItem2(){
        val intent : Intent = Intent(this, BookActivity::class.java)
        intent.putExtra("bookid", "BOOK1235")
        intent.putExtra("name", "난생처음 MySQL")
        intent.putExtra("price", "25000")
        intent.putExtra("date", "2024-01-17")
        intent.putExtra("writer", "배은영")
        intent.putExtra("description", "175개의 다양한 실전 예제를 통해 SQL의 기초와 쿼리 작성법을 배웁니다. 무역회사 데이터베이스를 기반으로 누적되는 시나리오를 기반으로 누적되는 시나리오를 연습할 수 있습니다.")
        intent.putExtra("category", "IT/모바일")
        startActivity(intent)

    }
    fun BookItem3(){
        val intent : Intent = Intent(this, BookActivity::class.java)
        intent.putExtra("bookid", "BOOK1236")
        intent.putExtra("name", "쉽게 배우는 PHP 웹 프로그래밍")
        intent.putExtra("price", "29000")
        intent.putExtra("date", "2024-06-30")
        intent.putExtra("writer", "송미영")
        intent.putExtra("description", "‘PHP의 이론적 개념 → 기본 실습 → 응용 실습’ 순의 단계별 학습이 가능합니다. 각 장마다 제공하는 프로젝트 실습을 제작하며 북마켓 쇼핑몰을 완성하도록 구성하여 본문에서 배운 내용이 어디에 어떻게 적용되는지 확인할 수 있습니다.")
        intent.putExtra("category", "IT/모바일")
        startActivity(intent)

    }
    fun BookItem4(){
        val intent : Intent = Intent(this, BookActivity::class.java)
        intent.putExtra("bookid", "BOOK1237")
        intent.putExtra("name", "미라클 HTML5+CSS3+자바스크립트")
        intent.putExtra("price", "31000")
        intent.putExtra("date", "2024-07-11")
        intent.putExtra("writer", "조성호")
        intent.putExtra("description", "다채로운 예제를 통해 HTML5, CSS3, 자바스크립트를 설명합니다. 친절한 본문 설명과 더불어 캐릭터의 친근한 대화로 핵심 내용을 짚어주고, 실습하는 재미를 느낄 수 있는 동적인 예제와 시각적 요소를 충분히 활용한 예제를 다수 제공합니다.")
        intent.putExtra("category", "IT/모바일")
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_books, menu)
        val searchItem:MenuItem? = menu?.findItem(R.id.menu_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "도서명을 입력해주세요."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home->{
                onBackPressed()
            }
            R.id.menu_home -> {
                Toast.makeText(applicationContext,"홈으로 메뉴를 클릭하였습니다",Toast.LENGTH_LONG).show()
                finish()
                val intent:Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.main_video -> {
                Toast.makeText(applicationContext,"동영상강좌 메뉴를 클릭하였습니다",Toast.LENGTH_LONG).show()
                return true
            }
            R.id.menu_customer -> {
                Toast.makeText(applicationContext,"고객센터 메뉴를 클릭하였습니다",Toast.LENGTH_LONG).show()
                return true
            }
            R.id.menu_mypage -> {
                Toast.makeText(applicationContext,"마이페이지 메뉴를 클릭하였습니다",Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
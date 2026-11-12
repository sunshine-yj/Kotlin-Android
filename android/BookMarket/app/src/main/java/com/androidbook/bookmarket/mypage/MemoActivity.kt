package com.androidbook.bookmarket.mypage

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R
import java.time.LocalDate

class MemoActivity : AppCompatActivity() {

    val db = DatabaseHelperMemo(this)
    var selectItemPos : Int = -1
    lateinit  var lv: ListView
    lateinit  var adapter: SimpleAdapter
    lateinit var memoList: ArrayList<HashMap<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        lv = findViewById(R.id.memo_list)
        val insert : Button = findViewById(R.id.btnInsert)
        val update : Button = findViewById(R.id.btnUpdate)
        val delete : Button = findViewById(R.id.btnDelete)
        ReadData()

        lv.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectItemPos =  p2
            }
        }

        insert.setOnClickListener {
            val builder= AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_memo_dialog, null)

            builder.setView(dialogView)

            val  dialog = builder.show()
            val dlginsert : Button = dialogView.findViewById(R.id.insert)
            val dlgcancel : Button = dialogView.findViewById(R.id.cancel)

            dlginsert.setOnClickListener {
                val date: LocalDate = LocalDate.now()
                val title : EditText = dialogView.findViewById(R.id.title)
                val content : EditText = dialogView.findViewById(R.id.content)
                db.insertData(date.toString(), title.getText().toString(), content.getText().toString())
                dialog.dismiss()
                ReadData()
            }

            dlgcancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        delete.setOnClickListener {
            val alertDialog = android.app.AlertDialog.Builder(this@MemoActivity)
            alertDialog.setTitle("메모장 삭제")
            alertDialog.setMessage("선택한 메모를 삭제하겠습니까?")
            alertDialog.setIcon(R.drawable.icon_cart)

            val listener  = DialogInterface.OnClickListener { dialogInterface, i ->
                when(i){
                    DialogInterface.BUTTON_POSITIVE -> {

                        val count: Int
                        val checked: Int
                        count = adapter.getCount()

                        if (count >= 0) {
                            checked = selectItemPos
                            Log.d("11111111111",""+checked )
                            if (checked > -1 && checked < count) {
                                val id :Int = memoList[selectItemPos].get("id").toString().toInt()

                                db.deleteData(id)
                                memoList.removeAt(checked)
                                adapter.notifyDataSetChanged()
                            }
                        }

                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialogInterface.cancel()
                    }
                }
            }

            alertDialog.setPositiveButton("예", listener)
            alertDialog.setNegativeButton("아니요", listener)
            if (selectItemPos> -1) alertDialog.show()
            else Toast.makeText(getApplicationContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show()
        }

        update.setOnClickListener {

            val builder= AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_memo_dialog, null)
            builder.setView(dialogView)

            val dlgtitle : EditText = dialogView.findViewById(R.id.title)
            val dlgcontent : EditText = dialogView.findViewById(R.id.content)

            val dlginsert : Button = dialogView.findViewById(R.id.insert)
            dlginsert.setText("수정")
            val dlgcancel : Button = dialogView.findViewById(R.id.cancel)

            val count: Int
            val checked: Int
            count = adapter.getCount()
            if (count > 0) {
                checked = selectItemPos
                if (checked > -1 && checked < count) {

                    dlgtitle.setText(memoList[selectItemPos].get("title").toString())
                    dlgcontent.setText(memoList[selectItemPos].get("content").toString())

                    val  dialog = builder.show()

                    dlginsert.setOnClickListener {
                        val id :Int = memoList[selectItemPos].get("id").toString().toInt()
                        val date: LocalDate = LocalDate.now()
                        val title : EditText = dialogView.findViewById(R.id.title)
                        val content : EditText = dialogView.findViewById(R.id.content)
                        db.updateData(date.toString(), title.getText().toString(), content.getText().toString(), id)
                        dialog.dismiss()
                        memoList.clear()
                        ReadData()
                    }

                    dlgcancel.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                else Toast.makeText(getApplicationContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun ReadData(){
        memoList = db.readData()
        adapter= SimpleAdapter(
                this@MemoActivity,
                memoList,
                R.layout.activity_memo_item,
                arrayOf("date", "title", "content"),
                intArrayOf(R.id.tvDate, R.id.tvTitle, R.id.tvContent)
        )

        if (!memoList.isEmpty())
           lv.setAdapter(adapter)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setQueryHint("Search View Hint")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {  // 검색 버튼 누를 때 호출

                memoList.clear()
                memoList = db.searchReadData(p0.toString())
                adapter= SimpleAdapter(
                        this@MemoActivity,
                        memoList,
                        R.layout.activity_memo_item,
                        arrayOf("date", "title", "content"),
                        intArrayOf(R.id.tvDate, R.id.tvTitle, R.id.tvContent)
                )
                lv.setAdapter(adapter)

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                ReadData()
                return true
            }
        })

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

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

}

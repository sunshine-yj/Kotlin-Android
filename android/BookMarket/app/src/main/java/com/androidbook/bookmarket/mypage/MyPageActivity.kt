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
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
class MyPageActivity : AppCompatActivity() {
    lateinit var menoObj : Button
    lateinit var loginimg: ImageView
    lateinit var fab_add_photo: FloatingActionButton
    lateinit var m_imageFile: File
    private val REQUEST_TAKE_PHOTO = 100
    private val REQUEST_IMAGE_CHOOSE = 101
    lateinit var launcher: ActivityResultLauncher<Intent>
    var requestCode: Int = 0

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
        loginimg = findViewById(R.id.loginimg)
        fab_add_photo = findViewById(R.id.fab_add_photo)

        val pref : SharedPreferences = getSharedPreferences("prefile",  Context.MODE_PRIVATE)
        val nameStr = pref.getString("filename", null)
        if(!nameStr.isNullOrEmpty())
            Glide.with(this).load(nameStr).into(loginimg)

        fab_add_photo.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_mypage_picture_dialog, null)
            builder.setTitle("선택하세요")
            builder.setView(dialogView)
            builder.setNegativeButton("취소", null)
            val dialog = builder.show()

            val layoutCameraPick: LinearLayout = dialogView.findViewById(R.id.layoutCameraPick)
            val layoutGalleryPick: LinearLayout = dialogView.findViewById(R.id.layoutGalleryPick)

            layoutCameraPick.setOnClickListener {
                if (intent.resolveActivity(getPackageManager()) != null) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val photoFile: File = createImageFile()
                    val packageName = "com.androidbook.bookmarket.mypage.fileprovider"
                    val photoURI = FileProvider.getUriForFile(this, packageName, photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    requestCode = REQUEST_TAKE_PHOTO
                    launcher.launch(intent)
                    m_imageFile = photoFile
                } else Toast.makeText(this, "카메라를 오픈할 수 없습니다", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            layoutGalleryPick.setOnClickListener {

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                launcher.launch(intent)
                requestCode = REQUEST_IMAGE_CHOOSE
                dialog.dismiss()
            }

        }

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->

            val pref : SharedPreferences = getSharedPreferences("prefile",  Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = pref.edit()
            if (requestCode == REQUEST_TAKE_PHOTO && result.resultCode == RESULT_OK) {
                //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(m_imageFile))
                Glide.with(this).load(Uri.fromFile(m_imageFile)).into(loginimg)


                editor.putString("filename", Uri.fromFile(m_imageFile).toString())
                editor.apply()

            }
            else      if (requestCode == REQUEST_IMAGE_CHOOSE && result.resultCode == RESULT_OK) {
                //  loginimg.setImageURI(data?.data)
                Glide.with(this).load(result.data?.data).into(loginimg)


                editor.putString("filename", result.data?.data.toString())
                editor.apply()
            }


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

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile: File = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        return imageFile
    }

}
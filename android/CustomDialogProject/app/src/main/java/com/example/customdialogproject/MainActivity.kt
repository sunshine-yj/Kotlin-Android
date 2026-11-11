package com.example.customdialogproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvInput: TextView
    private var tvResultCheck = false
    private var tvInputCheck = false
    private var FirstNum: Double = 0.0
    private var SecondNum: Double = 0.0
    private var ResultNum: Double = 0.0
    private var ArithOper: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_calculate, null)
            builder.setView(dialogView)
            val dialog = builder.show()

            tvResult = dialogView.findViewById(R.id.tvResult)
            tvInput = dialogView.findViewById(R.id.tvIntput)

            val buttonclose: Button = dialogView.findViewById(R.id.buttonclose)

            buttonclose.setOnClickListener {
                dialog.dismiss()

            }
        }

    }

    fun numClick(view : View){
        if(!tvResultCheck){
            tvResult.text = ""
        }
        tvResult.append((view as ImageButton).tag.toString())
        tvResultCheck = true
        var str = tvResult.text.toString()
        SecondNum = str.toDouble()
    }

    fun BackClick(view: View){
        var str = tvResult.text.toString()
        if(str.isNotEmpty()){
            str = str.substring(0, str.length - 1)
            tvResult.text = str
        }
    }

    fun ClearClick(view: View){
        tvResult.text = ""
        tvResultCheck = false
        SecondNum = 0.0
    }

    fun ACClick(view: View){
        tvResult.text = ""
        tvInput.text = ""
        tvResultCheck = false
        tvInputCheck = false
        FirstNum = 0.0
        SecondNum = 0.0
        ArithOper = ""
    }

    fun DotClick(view: View) {
        var dotCheck = false
        tvResult.text.let {
            if (it.contains(".")) {
                dotCheck = true
            }
        }
        if (!tvResultCheck) {
            tvResult.text = "0."
            tvResultCheck = true
        } else if (!dotCheck){
            tvResult.append(".")
        }
    }

    fun OperClick(view: View){
        var btn = (view as ImageButton).tag.toString()

        if (!tvInputCheck){
            if (SecondNum % 1.0 == 0.0)
                tvInput.text = SecondNum.toLong().toString() + " "+ btn
            else tvInput.text = SecondNum.toString() + " "+ btn

            FirstNum = SecondNum
            tvInputCheck = true
            tvResultCheck = false
        }else{
            if (!tvResultCheck){

                if(FirstNum % 1.0 == 0.0)
                    tvInput.text = FirstNum.toLong().toString() + " "+ btn
                else tvInput.text = FirstNum.toString() + " "+ btn

            }else{
                when(ArithOper){
                    "+" -> ResultNum = FirstNum + SecondNum
                    "-" -> ResultNum = FirstNum - SecondNum
                    "x" -> ResultNum = FirstNum * SecondNum
                    else -> ResultNum = FirstNum / SecondNum
                }
                ResultNum = round(ResultNum * 1000000) / 1000000

                if (FirstNum % 1.0 == 0.0)
                    tvInput.text = ResultNum.toLong().toString() + " "+ btn
                else tvInput.text = ResultNum.toString() + " "+ btn

                tvResultCheck = false
                FirstNum = ResultNum
            }
        }
        ArithOper = btn.toString()
    }
    fun EqualClick(view: View){
        tvInput.text = ""
        if (FirstNum % 1.0 == 0.0)
            tvInput.append(FirstNum.toLong().toString() + " "+ ArithOper + " ")
        else tvInput.append(FirstNum.toString() + " "+ ArithOper+ " ")

        if (SecondNum % 1.0 == 0.0)
            tvInput.append(SecondNum.toLong().toString() + " = ")
        else tvInput.append(SecondNum.toString() + " = ")
        when(ArithOper){
            "+" -> ResultNum = FirstNum + SecondNum
            "-" -> ResultNum = FirstNum - SecondNum
            "x" -> ResultNum = FirstNum * SecondNum
            else -> ResultNum = FirstNum / SecondNum
        }
        ResultNum = round(ResultNum * 1000000000) / 1000000000
        if (ResultNum % 1.0 == 0.0)
            tvInput.append(ResultNum.toLong().toString())
        else tvInput.append(ResultNum.toString())


        FirstNum = ResultNum
        tvResultCheck = false
    }
}
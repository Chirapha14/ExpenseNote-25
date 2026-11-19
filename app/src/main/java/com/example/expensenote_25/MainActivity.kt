package com.example.expensenote_25

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensenote_25.databinding.ActivityMainBinding
import com.example.expensenote_25.databinding.DialogAddExpenseBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
 ////สร้างตัวแปรสำหรับ UI
 private lateinit var etAmount: EditText
 private lateinit var etNote: EditText
 private lateinit var btnAdd: Button
 private lateinit var btnClear: Button
 private lateinit var tvLastItem: TextView
 private lateinit var tvTotal: TextView



 //ตัวแปรสำหรับเก็บค่ายอดรวมทั้งหมด
 //private var total: Double = 0.0
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var adapter: ExpenseAdapter
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อมโยงตัวแปร เข้ากับ ID .osohk XML
        etAmount = findViewById<EditText>(R.id.etAmount)
        etNote = findViewById<EditText>(R.id.etNote)
        btnAdd = findViewById<Button>(R.id.btnAdd)
        btnClear = findViewById<Button>(R.id.btnClear)
        tvTotal = findViewById<TextView>(R.id.tvTotal)
        tvLastItem = findViewById<TextView>(R.id.tvLastItem)

        //กำหนดการทำงาน่เมื่อผู้ใช้ทำการกดปุ่มเพิ่มรายการ
        btnAdd.setOnClickListener {
            //สร้างตัวแปรที่เอาไว้เก็บข้อมูลจากช่อง
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            //ทำการตรวจสอบว่าผู้ใช้ทำการกรอกจำนวนเงินมาแล้วหรือยัง
            if (amountText.isNotEmpty()) {
                Toast.makeText(  this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                //แจ้งเตือนผู้ใช้ว่าให้หกรอกตัวเลขมา ไม่ใช่ตัวหนังสือ
                Toast.makeText( this, "กรุณากรอกจำนวนเงินให้ถูกต้อง", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //เอาจำนวนมารวมกัน
            total += amount

            //ตวรจสอบว่ามีการกรอกคำอธิบายมั้ย ถ้าไม่มี ให้เป็นคำว่า "ไม่ระบุ"
            val showNote = if (noteText.isEmpty()) noteText


            }
        }
    }
}

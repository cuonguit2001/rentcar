package com.example.thuexe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class QuanLySanPham : AppCompatActivity() {
    private lateinit var QLSPreturnBtn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_san_pham)

        QLSPreturnBtn = findViewById(R.id.itemBackBtn)
        QLSPreturnBtn.setOnClickListener(View.OnClickListener {
            this.finish()
        })
    }
}
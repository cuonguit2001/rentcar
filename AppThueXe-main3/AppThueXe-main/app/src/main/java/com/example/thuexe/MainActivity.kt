package com.example.thuexe

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cars_layout.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var searchText: EditText
    private lateinit var searchText2: EditText
    private lateinit var imageBrand: ImageView
    private lateinit var avatarCustomer: ImageView
    private lateinit var car: LinearLayout
    private  var db: DatabaseReference
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchText = findViewById(R.id.searchText)
        var temp = arrayOf<String>("nextPage", "aaa", "new")
        searchText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val text: String = searchText.text.toString()
                db.child("Timkiem").setValue(text)
             //   tim_kiem_car2.setText(searchText.text.toString())
               // searchText2 = findViewById(R.id.tim_kiem_car2)
               // searchText2.setText(text)

                        val intent = Intent(this@MainActivity, carsActivity::class.java)
                        startActivity(intent)
                      //
                 //  startActivity(intent)

                return@OnKeyListener true
            }
            false
        })

        imageBrand = findViewById(R.id.imageBrandAudi)

        imageBrand.setOnClickListener(View.OnClickListener {
            db.child("Timkiem").setValue("Audi")
            val intent = Intent(this@MainActivity, carsActivity::class.java)
            startActivity(intent)
           // startActivity(intent)
        })
        goi_y1.setOnClickListener(View.OnClickListener {
            db.child("Timxe").setValue("51A 19876")
            val intent = Intent(this, carDetailActivity::class.java)
            startActivity(intent)

        })
        avatarCustomer = findViewById(R.id.avatarCustomer)

        avatarCustomer.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, personalSettingActivity::class.java)
            startActivity(intent)
        })
        logo_toyota.setOnClickListener(View.OnClickListener {
            db.child("Timkiem").setValue("Toyota")
            val intent = Intent(this@MainActivity, carsActivity::class.java)
            startActivity(intent)
           // startActivity(intent)
        })
        tatca.setOnClickListener(View.OnClickListener {
            db.child("Timkiem").setValue("")
            val intent = Intent(this@MainActivity, carsActivity::class.java)
            startActivity(intent)
            //startActivity(intent)
        })
//        car = findViewById(R.id.car_1)
//        car.setOnClickListener {
//
//            val intent = Intent(this, carDetailActivity::class.java)
//            startActivity(intent)
//        }
    }
}
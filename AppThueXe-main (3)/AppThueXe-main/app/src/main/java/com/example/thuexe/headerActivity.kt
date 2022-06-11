package com.example.thuexe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class headerActivity: AppCompatActivity() {
    private lateinit var searchText: EditText
    private lateinit var avatarCustomer: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.header_layout)

        searchText = findViewById(R.id.searchText)
        var temp = arrayOf<String>("nextPage", "aaa", "new")
        searchText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val text: String = searchText.text.toString()
                for(i in temp.indices){
                    if( text?.equals(temp[i])){
                        val intent = Intent(this, carsActivity::class.java)
                        startActivity(intent)
                    }
                }
                return@OnKeyListener true
            }
            false
        })

        avatarCustomer = findViewById(R.id.avatarCustomer)

        avatarCustomer.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, personalSettingActivity::class.java)
            startActivity(intent)
        })
    }

    fun getContext(): Context{
        return this
    }
}
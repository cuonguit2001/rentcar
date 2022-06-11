package com.example.myapplication.extensions

import android.app.Activity
import android.widget.Toast

object Extensions1 {
    fun Activity.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
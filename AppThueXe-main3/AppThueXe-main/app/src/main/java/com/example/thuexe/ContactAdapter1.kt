package com.example.thuexe

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.thuexe.utils.Car
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.example.myapplication.extensions.Extensions.toast
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

import java.io.ByteArrayOutputStream
import java.io.IOException

@Suppress("NAME_SHADOWING")
class ContactAdapter1(context: Context, layout: Int, contacts: List<Car>) :
    BaseAdapter() {
    var storage = Firebase.storage
    private var myContext: Context = context
    private var myLayout: Int = layout
    private var mContacts: List<Car> = contacts
    val storageRef = storage.reference
    override fun getCount(): Int {
        return mContacts.size
    }

    override fun getItem(i: Int): Any {
        return mContacts[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint( "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cvView: View? = convertView
        if (cvView == null) {
            cvView = LayoutInflater.from(myContext).inflate(R.layout.cars_layout_adaper,null, false)
        }
        val contact = getItem(position) as Car
        // Get view


        val namecar = cvView?.findViewById(R.id.name_adapter) as TextView
        val gia = cvView?.findViewById(R.id.gia_adapter) as TextView
        storageRef.child(contact.Img.toString()).downloadUrl.addOnSuccessListener { urlImage ->
            Glide.with(cvView).load(urlImage).override(400,200).into(cvView?.findViewById(R.id.img_adapter) as ImageView)


        }
        namecar.text = contact.name
        gia.text=contact.gia.toString()+"/ngay"
        return cvView
    }

}
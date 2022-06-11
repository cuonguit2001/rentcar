package com.example.thuexe

import Adapter.clientAdapter
import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.example.thuexe.databinding.ActivityQuanLyKhachHangBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.personal_infomation_layout.*

class QuanLyKhachHang : AppCompatActivity() {
    private lateinit var binding: ActivityQuanLyKhachHangBinding
    private lateinit var clientlist: ArrayList<Client>
    private var db: DatabaseReference
    lateinit var gioitinh: String
    private lateinit var key: String

    init {
        db = FirebaseDatabase.getInstance().getReference()

    }

    private var db1: DatabaseReference

    init {
        db1 = FirebaseDatabase.getInstance().getReference()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuanLyKhachHangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clientlist = ArrayList()

        val user = FirebaseUtils.firebaseAuth.currentUser
        if (user != null) {
            db.child("User").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast(snapshot.getValue(Car::class.java).toString())
                    val comment = snapshot.getValue<User>()
                    val clientName = comment!!.name
                    val clientPhone = "0" + comment!!.sodienthoai.toString()
                    //toast(clientName)
                    val client = Client(clientName, clientPhone)
                    clientlist.add(client)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast("snapshot?.getValue().toString()")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")

                }

            })
        }
        val clientName = arrayOf("Chau Khac Dat","Pham Dac Trung","Huynh Thanh Sang")
        val clientPhone = arrayOf("0123456789","0135792468","9876543210")

        clientlist = ArrayList()

//        for (i in clientName.indices){
//            val client = Client(clientName[i], clientPhone[i])
//            clientlist.add(client)
//        }
        binding.clientListView.isClickable=true
        binding.clientListView.adapter = clientAdapter(this,clientlist)
    }
}
package com.example.thuexe

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.personal_setting_layout.*

class Dashboard : AppCompatActivity() {
    private lateinit var backwardBtn: TextView
    private lateinit var addItemTbn: Button
    private lateinit var clientManager: Button
    private lateinit var contractsManager: Button
    private lateinit var itemManager: Button
    private  var db: DatabaseReference
    private lateinit var key : String
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        backwardBtn = findViewById(R.id.adminBackBtn)
        addItemTbn = findViewById(R.id.ThemSP)
        itemManager = findViewById(R.id.QuanLySP)
        contractsManager = findViewById(R.id.QuanLyHD)
        clientManager = findViewById(R.id.QuanLyKH)

        val user = FirebaseUtils.firebaseAuth.currentUser
        if (user != null) {
            // customerId.setText(user.email)
            db.child("User").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast(snapshot.getValue(Car::class.java).toString())
                    val comment = snapshot.getValue<User>()
                    if(user.email==comment!!.Email){
                        //     toast(snapshot.key.toString())
                        key=snapshot.key.toString()
                        name_admin.setText( comment!!.name)
                        toast(key)
                    }


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

        backwardBtn.setOnClickListener(View.OnClickListener {
            this.finish()
        })

        addItemTbn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ThemSanPham::class.java)
            startActivity( intent )
        }  )

        itemManager.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, QuanLySanPham::class.java)
            startActivity( intent )
        }  )

        contractsManager.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, QuanLyHopDong::class.java)
            startActivity( intent )
            startActivity( intent )
        }  )

        clientManager.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, QuanLyKhachHang::class.java)
            startActivity( intent )
            startActivity( intent )
        }  )
    }
}
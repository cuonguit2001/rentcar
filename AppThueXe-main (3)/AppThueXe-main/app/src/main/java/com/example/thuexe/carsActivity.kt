package com.example.thuexe

import Adapter.CarActivityAdapter
import Model.Car
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.extensions.Extensions.toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.cars_layout.*
import kotlinx.android.synthetic.main.cars_layout_adaper.*

class carsActivity : AppCompatActivity() {
    private lateinit var homeTitle: TextView

    val ls : ArrayList<Car> = ArrayList()
    private lateinit var searchText1: EditText
    private val USER_NODE = "users"
    private  var db: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
       // searchText1 = findViewById(R.id.tim_kiem_car2)
        var intent = getIntent()
        var Bs= intent.getStringExtra("timkiem")

        if(Bs.toString()=="") toast("123") else toast("1234")
        if(Bs.toString() !="") {
          //  tim_kiem_car2.setText(Bs.toString())
        }
//        db.child("Timkiem").get().addOnSuccessListener {
//            Bs=it.value.toString()
//            tim_kiem_car2.setText(it.value.toString())
//        }.addOnFailureListener{
//        }
        setContentView(R.layout.cars_layout)
        homeTitle = findViewById(R.id.Home_Title)
        homeTitle.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
//        var adapter = ContactAdapter(this, R.layout.cars_layout_adaper, ls)
//        rental_car_listview.setAdapter(adapter)
        db.child("Car").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // toast(snapshot.getValue(Car::class.java).toString())
                val comment = snapshot.getValue<Car>()
                val user123 = Car("112Toyota Vios",70220000,"51A 24324",4,"B","Ho Chi Minh","")
                //if(Bs=="") toast("123") else toast("1234")
                if(comment!!.name.contains(Bs.toString())) {
                    ls.add(comment!!)
                }

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

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
        var adapter = ContactAdapter1(this, R.layout.cars_layout_adaper, ls)
        rental_car_listview.setAdapter(adapter)

        rental_car_listview.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

//            if(bookmark_car.isClickable){
//
//                    toast("Da them vao Bookmark")
//
//            }
//            else{
                db.child("Timxe").setValue(ls[i].Bienso.toString())
                toast(ls[i].Bienso)
                //db.child("Timxe").setValue(ls[i].Bienso.toString())
                val intent = Intent(this, carDetailActivity::class.java)
                startActivity(intent)
//            }

        })

//
//        // val lvContact: ListView = findViewById<View>(R.id) as ListView
//
//
//     //   ls.add(user)
//     //   adapter.notifyDataSetChanged()
        tim_kiem_car2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val text: String = tim_kiem_car2.text.toString()
                db.child("Timkiem").setValue(text)
                //   tim_kiem_car2.setText(searchText.text.toString())
                // searchText2 = findViewById(R.id.tim_kiem_car2)
                // searchText2.setText(text)

                val intent = Intent(this@carsActivity, carsActivity::class.java)
                intent.putExtra("timkiem", text)
                startActivity(intent)
                //
                //  startActivity(intent)

                return@OnKeyListener true
            }
            false
        })

//        adapter = ContactAdapter(this, R.layout.cars_layout_adaper, ls)
//        adapter.notifyDataSetChanged()
//        rental_car_listview.setAdapter(adapter)
//        adapter.notifyDataSetChanged()
        super.onCreate(savedInstanceState)
    }

    fun timkiem(){
        ls.clear()
        tim_kiem_car2.text.toString()
        //   toast(tim_kiem_car2.text.toString())

        db.child("Car").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // toast(snapshot.getValue(Car::class.java).toString())
                val comment = snapshot.getValue<Car>()
                if(tim_kiem_car2.text.toString()!=""){
                    if(comment!!.name.contains(tim_kiem_car2.text.toString())){
                        //     toast("12424123")
                        ls.add(comment!!)
                        //    toast(ls.size.toString())

                    }else {

                    }
                } else{
                    ls.add(comment!!)
                    //  toast("12424")
                    //  toast(ls.size.toString())
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


}
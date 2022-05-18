package com.example.thuexe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.thuexe.utils.Car
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.cars_layout.*


class carsActivity : AppCompatActivity() {
    private lateinit var homeTitle: TextView

    val ls : ArrayList<Car> = ArrayList()
    private val USER_NODE = "users"
    private  var db: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {


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


                ls.add(comment!!)

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

        rental_car_listview.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            db.child("Timxe").setValue(ls[i].Bienso.toString())
            val intent = Intent(this, carDetailActivity::class.java)
            startActivity(intent)
        })

//
//        // val lvContact: ListView = findViewById<View>(R.id) as ListView
//
//
//     //   ls.add(user)
//     //   adapter.notifyDataSetChanged()

        tim_kiem_car2.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    timkiem()
                    rental_car_listview.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                    return true
                }
                return false
            }
        })
//        adapter = ContactAdapter(this, R.layout.cars_layout_adaper, ls)
//        adapter.notifyDataSetChanged()
//        rental_car_listview.setAdapter(adapter)
//        adapter.notifyDataSetChanged()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        db.child("Timkiem").get().addOnSuccessListener {
            tim_kiem_car2.setText(it.value.toString())
            if (it.value.toString()!= "") {
                timkiem()
            }
        }.addOnFailureListener{

        }
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
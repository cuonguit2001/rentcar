package com.example.thuexe

import Adapter.userCommentAdapter
import Model.userCommentModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.extensions.Extensions.toast
import com.example.thuexe.utils.Car
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.booking_car_layout.*

import kotlinx.android.synthetic.main.cars_layout.*
import kotlinx.android.synthetic.main.booking_car_layout.gia_booking
import kotlinx.android.synthetic.main.car_detail_layout.*

class carDetailActivity : AppCompatActivity() {
    private lateinit var imageSwitcher: ImageSwitcher
    private lateinit var imageBefore: RelativeLayout
    private lateinit var imageNext: RelativeLayout
    private lateinit var recyclerView_userCmt: RecyclerView
    private lateinit var booking_textBtn: TextView
    private lateinit var name: TextView
    private lateinit var gia: TextView
    private lateinit var Bs: String
    private  var db: DatabaseReference
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }
    private var recycleLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var list_userComment: ArrayList<userCommentModel>

    val images = arrayOf(R.drawable.bmw_m5_img, R.drawable.mercedes, R.drawable.ford_mustang)
    var counter = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_detail_layout)

        imageSwitcher = findViewById(R.id.imageSwitcher)
        imageBefore = findViewById(R.id.imageBefore)
        imageNext = findViewById(R.id.imageNext)
        setForSwitching()

        imageNext.setOnClickListener {
            counter++
            if(counter == images.size){
                counter = 0
            }
            val imageForChange = images[counter]
            imageSwitcher.setImageResource(imageForChange)
        }

        imageBefore.setOnClickListener {
            counter--
            if(counter < 0){
                counter = images.size-1
            }
            val imageForChange = images[counter]
            imageSwitcher.setImageResource(imageForChange)
        }

        recyclerView_userCmt = findViewById(R.id.recyclerView_userCmt)

        recycleLayoutManager = LinearLayoutManager(this)

        recyclerView_userCmt.layoutManager = recycleLayoutManager

        list_userComment = arrayListOf()
        getData()

        back_imageButton.setOnClickListener {
            val intent = Intent(this, carsActivity::class.java)
            db.child("Timkiem").setValue("")
            startActivity(intent)
        }

        booking_textBtn = findViewById(R.id.booking_textButton)
        booking_textBtn.setOnClickListener {
            val intent = Intent(this, bookingCarActivity::class.java)
            startActivity(intent)
        }
        db.child("Timxe").get().addOnSuccessListener {
            Bs=it.value.toString()
        }.addOnFailureListener{
        }
        name = findViewById(R.id.name_detail)
        db.child("Car").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // toast(snapshot.getValue(Car::class.java).toString())
                val comment = snapshot.getValue<Car>()
                if(comment!!.Bienso==Bs){
                //    toast("123214")

                    name.setText(comment!!.name.toString())

                         gia_detail.setText(comment!!.gia.toString()+"VND/Ngay")
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
    }

    override fun onStart() {
        super.onStart()

    }
    fun setForSwitching(){
        setFactory()
        setAnimations()
    }

    fun setFactory(){
        imageSwitcher.setFactory{
            getImageView()
        }
    }

    fun getImageView(): ImageView{
        val imageView = ImageView(this)
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
        imageView.scaleType=ImageView.ScaleType.FIT_XY
        imageView.setImageResource(images[0])
        return imageView
    }

    fun setAnimations(){
        val animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)

        val animOut1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        val animIn1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        imageSwitcher.outAnimation = animOut1
        imageSwitcher.inAnimation = animIn1
    }

    fun getData() {
        list_userComment.add(userCommentModel("JohnWih", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("Nothing", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("NoHject", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("JemMye", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))


        recyclerView_userCmt.adapter = userCommentAdapter(list_userComment)
    }
}
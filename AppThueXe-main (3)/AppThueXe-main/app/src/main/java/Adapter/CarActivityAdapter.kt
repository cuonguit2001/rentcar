package Adapter

import Model.Car
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thuexe.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.example.myapplication.extensions.Extensions1.toast
class CarActivityAdapter(private val list_userComment: ArrayList<Car>): RecyclerView.Adapter<CarActivityAdapter.ViewHolder>() {

    var storage = Firebase.storage
    val storageRef = storage.reference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarActivityAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cars_layout_adaper, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CarActivityAdapter.ViewHolder, position: Int) {
        var currentItem = list_userComment[position]

        holder.name.text = currentItem.name.toString()
        holder.userCommentCmt.text = currentItem.gia.toString()+"/ngay"
//        storageRef.child(currentItem.Img.toString()).downloadUrl.addOnSuccessListener { urlImage ->
//            Glide.with(holder.carAvatar).load(urlImage).override(400,200).into(holder.carAvatar)
//
//
//        }
    }

    override fun getItemCount(): Int {
        return list_userComment.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var carAvatar: ImageView
        var name: TextView
        var userCommentCmt: TextView

        init {
            carAvatar = itemView.findViewById(R.id.img_adapter)
            name = itemView.findViewById(R.id.name_adapter)
            userCommentCmt = itemView.findViewById(R.id.gia_adapter)
        }
    }
}
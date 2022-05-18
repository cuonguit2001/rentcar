package Adapter

import Model.userCommentModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thuexe.R

class userCommentAdapter(private val list_userComment: ArrayList<userCommentModel>): RecyclerView.Adapter<userCommentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userCommentAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_comment_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: userCommentAdapter.ViewHolder, position: Int) {
        var curentItem = list_userComment[position]
        holder.userCommentAvatar.setImageResource(curentItem.get_userAvatar())
        holder.userCommentName.text = curentItem.get_userName()
        holder.userCommentCmt.text = curentItem.get_userComment()
    }

    override fun getItemCount(): Int {
        return list_userComment.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userCommentAvatar: ImageView
        var userCommentName: TextView
        var userCommentCmt: TextView

        init {
            userCommentAvatar = itemView.findViewById(R.id.userComment_avatar)
            userCommentName = itemView.findViewById(R.id.userComment_name)
            userCommentCmt = itemView.findViewById(R.id.userComment_cmt)
        }
    }
}
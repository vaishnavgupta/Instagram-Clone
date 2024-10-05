package com.example.instagramcloneapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.R
import com.example.instagramcloneapp.Utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyHomePostsAdapter(var context: Activity, var data: ArrayList<Post>) : RecyclerView.Adapter<MyHomePostsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pImg = itemView.findViewById<ImageView>(R.id.posts_profile_image)
        val pName = itemView.findViewById<TextView>(R.id.posts_name)
        val pTime = itemView.findViewById<TextView>(R.id.posts_time)
        val pPostImg = itemView.findViewById<ImageView>(R.id.post_img)
        val pCaption = itemView.findViewById<TextView>(R.id.post_capt)
        val likeBtn=itemView.findViewById<ImageView>(R.id.like_img_view)
        val shareBtn=itemView.findViewById<ImageView>(R.id.share_img_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item_home_page, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = data[position]

        // Fetch user data from Firebase
        try {
            Firebase.firestore.collection(USER_NODE).document(currItem.uid).get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                if (user != null) {
                    // Load user image with null check
                    Glide.with(context).load(user.image).placeholder(R.drawable.profile).into(holder.pImg);
                    holder.pName.text = user.name ?: "Unknown User"
                }
            }
        } catch (e: Exception) {
            // Handle exception if Firebase retrieval fails
            e.printStackTrace()
        }

        // Set time and caption
        try {
            val text = TimeAgo.using(currItem.time.toLong())
            holder.pTime.text = text
        }catch (e:Exception){holder.pTime.text = ""}
        holder.pCaption.text = currItem.caption

        // Load post image with null check
        Glide.with(context).load(currItem.postUrl).placeholder(R.drawable.loading).into(holder.pPostImg)
        //clicking on like
        holder.likeBtn.setOnClickListener {
            holder.likeBtn.setImageResource(R.drawable.heart_red)
        }
        holder.shareBtn.setOnClickListener {
            //opening share window of the android
            val i=Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,currItem.postUrl)
            context.startActivity(i)
        }
    }
}


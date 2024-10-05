package com.example.instagramcloneapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.R
import com.squareup.picasso.Picasso

class MyProfilePostsAdapter(var context:Activity,var data:ArrayList<Post>):RecyclerView.Adapter<MyProfilePostsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val postsImage=itemView.findViewById<ImageView>(R.id.post_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.my_profile_posts_rv_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem=data[position]
        //loading the image in the image view by Picasso
        Picasso.get().load(currItem.postUrl).into(holder.postsImage)
    }
}
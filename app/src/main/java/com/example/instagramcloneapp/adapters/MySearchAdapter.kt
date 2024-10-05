package com.example.instagramcloneapp.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.R
import com.example.instagramcloneapp.adapters.MyProfileReelsAdapter.MyViewHolder

class MySearchAdapter(var context:Context,var data:ArrayList<User>):RecyclerView.Adapter<MySearchAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var usr_name=itemView.findViewById<TextView>(R.id.searchName)
        var usr_img=itemView.findViewById<ImageView>(R.id.search_uimage)
        var followBtm=itemView.findViewById<Button>(R.id.search_follow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.each_item_in_search_rv,parent,false)
        return MySearchAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currItem=data[position]
        holder.usr_name.text=currItem.name
        Glide.with(context).load(currItem.image).placeholder(R.drawable.profile).into(holder.usr_img)
        holder.followBtm.setOnClickListener {
            holder.followBtm.text="Unfollow"
        }


    }
}
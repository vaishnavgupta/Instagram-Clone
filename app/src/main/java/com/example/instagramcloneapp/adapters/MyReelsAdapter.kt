package com.example.instagramcloneapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramcloneapp.ModelClass.Reels
import com.example.instagramcloneapp.R
import com.example.instagramcloneapp.adapters.MyProfileReelsAdapter.MyViewHolder

class MyReelsAdapter(var context:Activity,var data:ArrayList<Reels>):RecyclerView.Adapter<MyReelsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val video=itemView.findViewById<VideoView>(R.id.videoViewreels)
        val prflImg=itemView.findViewById<ImageView>(R.id.reel_profile_image)
        val rlCapt=itemView.findViewById<TextView>(R.id.reel_capt)
        val prgBars=itemView.findViewById<ProgressBar>(R.id.progressBarreel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.reels_sect_layout,parent,false)
        return MyReelsAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem=data[position]
        holder.rlCapt.text=currItem.caption
        Glide.with(context).load(currItem.profileLink)
            .placeholder(R.drawable.profile)
            .into(holder.prflImg);
        holder.video.setVideoPath(currItem.reelUrl)
        holder.video.setOnPreparedListener {
            holder.video.start()
            holder.prgBars.visibility=View.GONE
        }

    }
}
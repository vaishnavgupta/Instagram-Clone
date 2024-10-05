package com.example.instagramcloneapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramcloneapp.ModelClass.Reels
import com.example.instagramcloneapp.adapters.MyProfileReelsAdapter
import com.example.instagramcloneapp.databinding.FragmentProfileMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ProfileMyReelsFragment : Fragment() {
    private lateinit var binding: FragmentProfileMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileMyReelsBinding.inflate(inflater, container, false)

        val reelsArrayList=ArrayList<Reels>()
        val mAdapter: MyProfileReelsAdapter = MyProfileReelsAdapter(requireActivity(),reelsArrayList)
        binding.recyclerMyreels.adapter=mAdapter
        binding.recyclerMyreels.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)   //3post with verticle orientation

        //filling data from firestore to postUrlData
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+"REEL").get().addOnSuccessListener {  //it is giving thr data
            //var tempList= arrayListOf<Post>()
            for(i in it.documents){
                val reels: Reels =i.toObject<Reels>()!!
                reelsArrayList.add(reels)
            }
            //postUrlData.addAll(tempList)
            mAdapter.notifyDataSetChanged()
        }



        return binding.root
    }

    companion object {

    }
}
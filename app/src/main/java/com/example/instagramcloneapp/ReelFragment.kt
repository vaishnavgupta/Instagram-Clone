package com.example.instagramcloneapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramcloneapp.ModelClass.Reels
import com.example.instagramcloneapp.adapters.MyReelsAdapter
import com.example.instagramcloneapp.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    private lateinit var rlsAdapter: MyReelsAdapter
    private lateinit var reelsList: ArrayList<Reels>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater,container,false)
        reelsList=ArrayList<Reels>()
        rlsAdapter=MyReelsAdapter(requireActivity(),reelsList)   //here this adapter works as recycler view but used for view pager as scrolling is required
        binding.reelsViewPager.adapter=rlsAdapter
        Firebase.firestore.collection("REELS").get().addOnSuccessListener {
            for(i in it.documents){
                var reel:Reels=i.toObject<Reels>()!!
                reelsList.add(reel)
            }
            reelsList.reverse()  //new reel first
            rlsAdapter.notifyDataSetChanged()
        }





        return binding.root
    }

    companion object {


    }
}
package com.example.instagramcloneapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.adapters.MyProfilePostsAdapter
import com.example.instagramcloneapp.databinding.FragmentProfileMyPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ProfileMyPostFragment : Fragment() {
    private lateinit var binding:FragmentProfileMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding=FragmentProfileMyPostBinding.inflate(inflater, container, false)

        var postUrlData=ArrayList<Post>()
        var mAdapter:MyProfilePostsAdapter= MyProfilePostsAdapter(requireActivity(),postUrlData)
        binding.recycView.adapter=mAdapter
        binding.recycView.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)   //3post with verticle orientation

        //filling data from firestore to postUrlData
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {  //it is giving thr data
            //var tempList= arrayListOf<Post>()
            for(i in it.documents){
                var post:Post=i.toObject<Post>()!!
                postUrlData.add(post)
            }
            //postUrlData.addAll(tempList)
            mAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}
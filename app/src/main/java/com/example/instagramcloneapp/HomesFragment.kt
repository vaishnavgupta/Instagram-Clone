package com.example.instagramcloneapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.adapters.MyHomePostsAdapter
import com.example.instagramcloneapp.databinding.FragmentHomesBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomesFragment : Fragment() {
    private lateinit var binding:FragmentHomesBinding
    private lateinit var postsData:ArrayList<Post>
    private lateinit var mAdapter:MyHomePostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomesBinding.inflate(inflater,container,false)

        //material toolbar code
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        //posts recycler view
        val recyclerViewPosts=binding.recyclerPosts
        postsData= ArrayList()
        mAdapter= MyHomePostsAdapter(requireActivity(),postsData)
        recyclerViewPosts.layoutManager=LinearLayoutManager(requireActivity())
        recyclerViewPosts.adapter=mAdapter

        //setting data in postsdata
        Firebase.firestore.collection("Post").get().addOnSuccessListener {
            postsData.clear()
            for(i in it.documents){
                val post:Post=i.toObject<Post>()!!
                postsData.add(post)
            }
            mAdapter.notifyDataSetChanged()
        }




        return binding.root
    }

    companion object {

    }

    //setting menu file in the material toolbar

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.material_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
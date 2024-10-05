package com.example.instagramcloneapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.Utils.USER_NODE
import com.example.instagramcloneapp.adapters.MySearchAdapter
import com.example.instagramcloneapp.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: MySearchAdapter
    private lateinit var userList:ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        userList=ArrayList()
        adapter=MySearchAdapter(requireContext(),userList)
        binding.searchRv.adapter=adapter
        binding.searchRv.layoutManager=LinearLayoutManager(requireContext())
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            userList.clear()
            for(i in it.documents){
                if(i.id.toString()==Firebase.auth.currentUser?.uid){
                    continue
                }
                val u:User=i.toObject<User>()!!
                userList.add(u)
            }
            userList.reverse()
            adapter.notifyDataSetChanged()
        }






        return binding.root
    }

    companion object {

    }
}
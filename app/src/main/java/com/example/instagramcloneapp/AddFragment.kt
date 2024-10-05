package com.example.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramcloneapp.addPost.AddPostActivity
import com.example.instagramcloneapp.addPost.AddReelsActivity
import com.example.instagramcloneapp.databinding.FragmentAddBinding
import com.example.instagramcloneapp.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {  //to display a bottom navigation bar
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddBinding.inflate(inflater,container,false)


        binding.createPost.setOnClickListener {
            val intent= Intent(requireActivity(), AddPostActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
        binding.createReel.setOnClickListener {
            val intent= Intent(requireActivity(), AddReelsActivity::class.java)
            activity?.startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}
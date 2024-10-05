package com.example.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.Utils.USER_NODE
import com.example.instagramcloneapp.adapters.ViewPagerAdapter
import com.example.instagramcloneapp.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var vpAdapter:ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false)


        binding.button.setOnClickListener {
            //opening signup activity on editProfile button
            val intent= Intent(activity,LoginorSignupActivity::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        //view pager adapter --> JUST LIKE RV here adapter is required to pass fragment manager
        vpAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager)
        vpAdapter.addFragment(ProfileMyPostFragment(),"My Post")
        vpAdapter.addFragment(ProfileMyReelsFragment(),"My Reels")
        binding.viewPager.adapter=vpAdapter

        //setting tab layouyt with view pager
        binding.tabLayout.setupWithViewPager(binding.viewPager)


        return binding.root

    }

    companion object {


    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User=it.toObject<User>()!!  //it haS ALL DATA FROM THE FIREBASE
                binding.name.text=user.name.toString()
                binding.bio.text=user.email.toString()
                if(user.image.isNullOrEmpty()==false){
                    Picasso.get().load(user.image).into(binding.profileImage);
                }
        }
    }
}
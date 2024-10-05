package com.example.instagramcloneapp.addPost

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramcloneapp.BottomNavigationActivity
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.R
import com.example.instagramcloneapp.Utils.POST
import com.example.instagramcloneapp.Utils.USER_NODE
import com.example.instagramcloneapp.Utils.USER_POST_FOLDER
import com.example.instagramcloneapp.Utils.USER_PROFILE_FOLDER
import com.example.instagramcloneapp.Utils.uploadImage
import com.example.instagramcloneapp.databinding.ActivityAddPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddPostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityAddPostBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    var imageUrl:String=""
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                //making a func that in Utils class that takes uri and store in firebase storage and return downlodable url
                uploadImage(
                    uri,
                    USER_POST_FOLDER,
                ) {   //here it give URL of the image that we have selected from gallery
                    if (it == null) {
                    } else {
                        imageUrl=it   //setting url in imageUrl variable
                        binding.addPostImg.setImageURI(uri)
                    }
                }
            }
        }


            override fun onCreate(savedInstanceState: Bundle?) {

                super.onCreate(savedInstanceState)
                //enableEdgeToEdge()
                setContentView(binding.root)


                //code to add back btn in toolbar
                val toolbar = binding.materialToolbar
                setSupportActionBar(toolbar)
                getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
                getSupportActionBar()?.setDisplayShowHomeEnabled(true);
                binding.materialToolbar.setNavigationOnClickListener {
                    startActivity(Intent(this@AddPostActivity,BottomNavigationActivity::class.java))
                    finish()
                }

                //launcher of gallery
                binding.addPostImg.setOnClickListener {
                    galleryLauncher.launch("image/*")

                }

                //functionality of cancel button
                binding.canceloutlinedButton.setOnClickListener {
                    finish()
                }

                //add post button clicking
                binding.addoutlinedButton.setOnClickListener {
                    auth=FirebaseAuth.getInstance()
                    var post:Post=Post()

                    //addOn-adding name and time to the post
                    post.uid=auth.currentUser!!.uid   //getting uid of user

                    post.time=System.currentTimeMillis().toString()
                    post.caption=binding.editTextCaption.text.toString()
                    post.postUrl=imageUrl
                    //creating a firestore database collection
                    Firebase.firestore.collection(POST).document().set(post)   //document is blank because one user can have multiple posts
                        .addOnSuccessListener {
                            Firebase.firestore.collection(auth.currentUser!!.uid).document().set(post)    //making another collection that is used to store the particular users post
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Post Added Successfully",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@AddPostActivity,BottomNavigationActivity::class.java))
                                    finish()
                                }
                        }
                }


        }
}
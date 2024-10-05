package com.example.instagramcloneapp.addPost

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramcloneapp.BottomNavigationActivity
import com.example.instagramcloneapp.ModelClass.Post
import com.example.instagramcloneapp.ModelClass.Reels
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.R
import com.example.instagramcloneapp.Utils.POST
import com.example.instagramcloneapp.Utils.USER_NODE
import com.example.instagramcloneapp.Utils.USER_POST_FOLDER
import com.example.instagramcloneapp.Utils.uploadImage
import com.example.instagramcloneapp.Utils.uploadVideo
import com.example.instagramcloneapp.databinding.ActivityAddPostBinding
import com.example.instagramcloneapp.databinding.ActivityAddReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityAddReelsBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private var videoUrl:String=""
    lateinit var progDialog:ProgressDialog
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                //making a func that in Utils class that takes uri and store in firebase storage and return downlodable url
                uploadVideo(
                    uri,
                    "USER_REEL_FOLDER",
                    progDialog
                ) {   //here it give URL of the image that we have selected from gallery
                    if (it == null) {
                    } else {
                        videoUrl=it   //setting url in imageUrl variable

                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        progDialog=ProgressDialog(this)

        //code to add back btn in toolbar
        val toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@AddReelsActivity, BottomNavigationActivity::class.java))
            finish()
        }


        //launcher of gallery
        binding.addReelsImg.setOnClickListener {
            galleryLauncher.launch("video/*")   //for video

        }

        //functionality of cancel button
        binding.canceloutlinedButton.setOnClickListener {
            finish()
        }

        //add post button clicking
        binding.uploadoutlinedButton.setOnClickListener {
            auth= FirebaseAuth.getInstance()
            Firebase.firestore.collection(USER_NODE).document(auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User=it.toObject<User>()!!
                val reel: Reels = Reels()
                reel.caption=binding.editTextCaption.text.toString()
                reel.reelUrl=videoUrl
                reel.profileLink=user.image.toString()
                //creating a firestore database collection
                Firebase.firestore.collection("REELS").document().set(reel)   //document is blank because one user can have multiple posts
                    .addOnSuccessListener {
                        Firebase.firestore.collection(auth.currentUser!!.uid+"REEL").document().set(reel)    //making another collection WITH UID+REEL
                            .addOnSuccessListener {
                                Toast.makeText(this,"Reel Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@AddReelsActivity,BottomNavigationActivity::class.java))
                                finish()
                            }
                    }

            }

        }


    }
}
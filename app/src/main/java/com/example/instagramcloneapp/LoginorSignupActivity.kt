package com.example.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.Utils.USER_NODE
import com.example.instagramcloneapp.Utils.USER_PROFILE_FOLDER
import com.example.instagramcloneapp.Utils.uploadImage
import com.example.instagramcloneapp.databinding.ActivityLoginorSignupBinding
import com.example.instagramcloneapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class LoginorSignupActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginorSignupBinding.inflate(layoutInflater)
    }
    lateinit var userClass:User
    private lateinit var auth:FirebaseAuth


    //loading of image
    private val galleryLauncher=registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let {
            //making a func that in Utils class that takes uri and store in firebase storage and return downlodable url
            uploadImage(uri, USER_PROFILE_FOLDER){   //here it give URL of the image that we have selected from gallery
                if(it==null){
                }else{
                    userClass.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        userClass=User()

        //intent from profile fragment
        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1){
                binding.registerBtn.text="Update Profile"
                //Already filling previous data  from Firestore to the fields
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        userClass=it.toObject<User>()!!  //userClass is getting all the fields
                        if(!userClass.image.isNullOrEmpty()){
                            Picasso.get().load(userClass.image).into(binding.profileImage);
                        }
                        binding.editTextName.setText(userClass.name)
                        binding.editTextEmailAddress.setText(userClass.email)
                        binding.editTextPassword.setText(userClass.password)

                    }
            }
        }

        binding.loginTv.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        //giving differnt colour to text view
        val text = "<font color=#FF000000>Already have an account? </font> <font color=#C13584>Login</font>"
        binding.loginTv.setText(Html.fromHtml(text))

        binding.registerBtn.setOnClickListener {
            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1){
                    //update the profile image only
                    Firebase.firestore.collection(USER_NODE).document(auth.currentUser!!.uid).set(userClass)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
                            val intent= Intent(this,BottomNavigationActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                }

            }
            else if(binding.editTextName.text.toString().isEmpty() || binding.editTextPassword.text.toString().isEmpty() || binding.editTextEmailAddress.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(binding.editTextEmailAddress.text.toString(),binding.editTextPassword.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){

                            userClass.name=binding.editTextName.text.toString()
                            userClass.email=binding.editTextEmailAddress.text.toString()
                            userClass.password=binding.editTextPassword.text.toString()
                            Firebase.firestore.collection(USER_NODE).document(auth.currentUser!!.uid).set(userClass)   //usernode in utils class
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                                    val intent= Intent(this,BottomNavigationActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
                                }
                        }
                        else{
                            Toast.makeText(this,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.addImadeBtn.setOnClickListener{
            galleryLauncher.launch("image/*")
        }

    }
}
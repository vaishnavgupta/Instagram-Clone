package com.example.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramcloneapp.ModelClass.User
import com.example.instagramcloneapp.databinding.ActivityLoginBinding
import com.example.instagramcloneapp.databinding.ActivityLoginorSignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding by lazy {
            ActivityLoginBinding.inflate(layoutInflater)
        }
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            if(binding.loginEditEmail.text.toString().isEmpty() || binding.loginEditPassword.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                val user=User(binding.loginEditPassword.text.toString(),binding.loginEditEmail.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,BottomNavigationActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}
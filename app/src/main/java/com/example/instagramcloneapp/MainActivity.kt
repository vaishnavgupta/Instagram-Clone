package com.example.instagramcloneapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        window.statusBarColor=Color.TRANSPARENT
        Handler().postDelayed({      //2 parameter--> runnable and the delay millisec
            if(FirebaseAuth.getInstance().currentUser==null){
                val intent = Intent(this, LoginorSignupActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, BottomNavigationActivity::class.java)
                startActivity(intent)
                finish()
            }

        },3000)

    }
}
package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding   //setting view binding to the activity
    private lateinit var auth:FirebaseAuth              //declaring firebase object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() //initializing the firebase object

        val currentUser:FirebaseUser? = auth.currentUser    //getting the details of current sign in user (if any)
        if (currentUser!= null){                            //checking if a user is already sign in or not
            startActivity(Intent(this,Home::class.java))
            finish()
        }

        binding.btnSignin.setOnClickListener {
            login()
        }
        binding.btnRegisterNow.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }
    }
    private fun login(){
        val email = binding.loginEmail.text.toString()         //getting user credentials in string format
        val password = binding.loginPassword.text.toString()   //getting user credentials in string format

        //checking if both the fields are filled or not
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Credentials cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }

        //signing in the user with their email and password
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"login Succssfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Home::class.java))
                finish()
            }else{
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
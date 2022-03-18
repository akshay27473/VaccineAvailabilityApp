package com.example.covid_19vaccineavailabilityapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.covid_19vaccineavailabilityapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog:ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    private var email=" "
    private var password=" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar=supportActionBar!!
        actionBar.title="Login"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait....")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth= FirebaseAuth.getInstance()



        binding.noaccountTv.setOnClickListener(){
            startActivity(Intent(this,Sign_Up::class.java))
            finish()
        }


        binding.loginBtn.setOnClickListener(){
            validateData()
        }

    }

    private fun validateData() {
        email = binding.emailEt.text.toString()
        password = binding.passwordEt.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid Email Format")
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEt.error="Please Enter Password"
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this,MainActivity::class.java))
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }


}
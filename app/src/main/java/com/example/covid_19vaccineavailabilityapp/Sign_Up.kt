package com.example.covid_19vaccineavailabilityapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.covid_19vaccineavailabilityapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_Up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var actionBar: ActionBar

    private  lateinit var progressDialog:ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth


    lateinit var btn:TextView
    private  var email=" "
    private var password=""
    private var confirmpassword=" "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btn=findViewById(R.id.alreadyaccount)

        actionBar=supportActionBar!!
        actionBar.title="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)



        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait....")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth= FirebaseAuth.getInstance()


        btn.setOnClickListener(){
            startActivity(Intent(this,Login::class.java))
            finish()

        }
        binding.signupBtn.setOnClickListener(){
            validateData()
        }


    }

    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()
        confirmpassword=binding.confirmEt.text.toString().trim()



        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error="Invalid Email Formatt"
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEt.error="Please Enter Password"

        }

        else if( password.length<6){
            binding.passwordEt.error="Password must be atleast 6 characters"

        }
        else if(TextUtils.isEmpty(confirmpassword)){
            binding.confirmEt.error="Confirm Password cant be empty"
        }
        else{
           if(password.contentEquals(confirmpassword)){
               firebaseSignup()
           }
            else{
                binding.confirmEt.error="Please enter same password as above"
            }
        }
    }

    private fun firebaseSignup() {
       progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                progressDialog.dismiss()
               // Toast.makeText(this,"Account Created with email $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Login::class.java))
                finish()
            }
            .addOnFailureListener(){ e->
                progressDialog.dismiss()

                Toast.makeText(this,"Sign Up Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}
package com.android.example.travelpartner

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.example.travelpartner.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding //data-Binding Variable declaration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login) //set Content using Data Binding

        val actionBar = supportActionBar
        actionBar!!.title= "Log In"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //when the use doesn't have an account and presses on register he will be sent to the register activity
        binding.register.setOnClickListener{

            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
            //onBackPressed()
        }
        //Login Button on Click Listener
        binding.btnLogin.setOnClickListener {
            when
            {
                //this 'when' checks if the user has entered something in the email section
                TextUtils.isEmpty(binding.edLoginMail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //if empty display a toast saying please enter email
                }
                //this when checks if the user has entered something in the password section
                TextUtils.isEmpty(binding.edLoginPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //if empty display a toast saying please enter password
                }
                else -> {
                    //if the input is not empty, first of all we have to trim the input to get rid of empty spaces
                    val email:String = binding.edLoginMail.text.toString().trim { it <= ' '}
                    val password:String = binding.edLoginPassword.text.toString().trim { it <= ' '}

                    //Log in using FireBaseAuth
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {task ->

                            if (task.isSuccessful){
                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are logged in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //the following block will send the user to the Dashboard activity if the login was successful
                                val intent =
                                    Intent(this@LoginActivity, Dashboard::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("email_id", email) //save email to get it later using intent
                                intent.putExtra("logged", "true") //save email to get it later using intent
                                startActivity(intent)
                                finish()
                            } else {
                                //If the registration is not successful then show error message
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }
    }
}
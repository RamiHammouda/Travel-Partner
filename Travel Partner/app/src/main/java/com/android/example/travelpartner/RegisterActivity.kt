package com.android.example.travelpartner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.example.travelpartner.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding //data-Binding Variable declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register) //set Content using Data Binding

        val actionBar = supportActionBar
        actionBar!!.title= "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //when the use does have an account and presses on login he will be sent to the login activity
        binding.login.setOnClickListener{
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //Register Button on Click Listener
        binding.btnRegister.setOnClickListener {
            when
            {
                //this 'when' checks if the user has entered something in the email section
                TextUtils.isEmpty(binding.edRegisterMail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //if empty display a toast saying please enter email
                }
                //this when checks if the user has entered something in the password section
                TextUtils.isEmpty(binding.edRegisterPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //if empty display a toast saying please enter password
                }
                else -> {
                    //if the input is not empty, first of all we have to trim the input to get rid of empty spaces
                    val email:String = binding.edRegisterMail.text.toString().trim { it <= ' '}
                    val password:String = binding.edRegisterPassword.text.toString().trim { it <= ' '}

                    //Create an instance and a register with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                //if the registration is successful
                                if (task.isSuccessful){
                                    //firebase register user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //the following block will send the user to the Dashboard activity if the registration was successful
                                    val intent =
                                        Intent(this@RegisterActivity, Dashboard::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //If the registration is not successful then show error message
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )



                }

            }
        }


    }
}
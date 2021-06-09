package com.android.example.travelpartner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val actionBar = supportActionBar
        actionBar!!.title= "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)


        login.setOnClickListener{
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            when
            {

                TextUtils.isEmpty(ed_register_mail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@SignupActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(ed_register_password.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@SignupActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email:String = ed_register_mail.text.toString().trim { it <= ' '}
                    val password:String = ed_register_password.text.toString().trim { it <= ' '}

                    //Create an instance and a register with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {task ->

                                //if the registration is successful
                                if (task.isSuccessful){
                                    //firebase register user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@SignupActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@SignupActivity, DashBoard::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //If the registration is not successful then show error message
                                    Toast.makeText(
                                        this@SignupActivity,
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
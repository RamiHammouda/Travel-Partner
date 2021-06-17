package com.android.example.travelpartner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.example.travelpartner.databinding.ActivityHomeBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding //data-Binding Variable declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home) //set Content using Data Binding

        val imageSlider = binding.imageSlider //get our imageSlider from Layout
        val images = ArrayList<SlideModel>() //create an Array to store home images that will be added later
        images.add(SlideModel(R.drawable.image1_home,null, null))
        images.add(SlideModel(R.drawable.image2_home,null, null))
        images.add(SlideModel(R.drawable.image3_home,null, null))
        images.add(SlideModel(R.drawable.image4_home,null, null))
        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP) //setting all the images to our imageSlider

        val loginButton = binding.buttonLogin
        loginButton.setOnClickListener {
            val myIntent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(myIntent)
            //setContentView(R.layout.activity_login);
        }


        val signUpButton = binding.buttonSignup
        signUpButton.setOnClickListener {
            val myIntent1 = Intent(this@HomeActivity, RegisterActivity::class.java)
            startActivity(myIntent1)
            //setContentView(R.layout.activity_login);
        }
    }
}
package com.android.example.travelpartner;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.image1, null));
        images.add(new SlideModel(R.drawable.image2, null));
        images.add(new SlideModel(R.drawable.image3, null));
        images.add(new SlideModel(R.drawable.image4, null));
        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
                //setContentView(R.layout.activity_login);
            }
        });


        Button signUpButton = findViewById(R.id.button_signup);
        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent1 = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(myIntent1);
                //setContentView(R.layout.activity_login);
            }
        });                                                                                            

    }
}

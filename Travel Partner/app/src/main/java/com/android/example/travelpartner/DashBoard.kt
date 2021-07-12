package com.android.example.travelpartner

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.travelpartner.databinding.ActivityDashboardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding //data-Binding Variable declaration
    private val tripsViewModel: TripsSharedViewModel by viewModels()
    private val db = Firebase.firestore //declare the dataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard) //set Content using Data Binding


        //adding our fragments
        val profileFragment = ProfileFragment()
        val notificationFragment = NotificationsFragment()
        val searchFragment = SearchFragment()
        val tripFragment = TripsFragment1()
        val trip6Fragment = TripsFragment6()
        val chooseNameFragment = ChooseNameFragment()

        val email = intent.getStringExtra("email_id") //get email from login activity using intent
        tripsViewModel.email = email //assign the email we got using intent to a viewModel variable

        //get the logged variable from the login or register activity using intent
        //if it is from the register activity logged = "false"
        //if it is from the login activity logged = "true"
        var logged = intent.getStringExtra("logged")

        //this is the first fragment that shows when the user logs in or registers a new account
        if (logged == "true")
        {
            makeCurrentFragment(profileFragment)
            tripsViewModel.userHasTrip = true
        }
        else {
            //logged is "false" and that means that the user is registering and needs to be forwarded to the fragment where he needs to create a name for his profile
            makeCurrentFragment(chooseNameFragment)
            logged = "true" //after the user chooses his profile name the logged variable must be set to "true" so that the choose name fragment won't appear again
        }


        //on click listener to the bottomNavigation where the fragment will be changed depending on the id of the clicked button in the navigation bar
        binding.bottomNavigation.setOnItemSelectedListener { id ->
            when (id) {
                R.id.profile -> {
                    //if the logged is "true" that means the user is logging in and doesn't need to create a username since he created one while registering
                    if (logged == "true")
                    {
                        makeCurrentFragment(profileFragment)
                    }
                    else {
                        //logged is "false" and that means that the user is registering and needs to be forwarded to the fragment where he needs to create a name for his profile
                        makeCurrentFragment(chooseNameFragment)
                    }
                }
                R.id.search -> makeCurrentFragment(searchFragment)
                R.id.trip ->
                {   //if a trip is already created we should show it when the user clicks on the myTripsTab, otherwise we should forward the user to the "create new trip" fragment
                    if (tripsViewModel.userHasTrip == false) {
                        makeCurrentFragment(tripFragment)
                    }
                    if (tripsViewModel.userHasTrip == true)
                    {makeCurrentFragment(trip6Fragment)}
                }
                R.id.notification -> makeCurrentFragment(notificationFragment)
            }
        }
    }
    //this function will set the current fragment to the one passed as parameter
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}
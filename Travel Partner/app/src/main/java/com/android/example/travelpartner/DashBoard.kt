package com.android.example.travelpartner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.travelpartner.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding //data-Binding Variable declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard) //set Content using Data Binding


        //adding our fragments
        val profileFragment = ProfileFragment()
        val notificationFragment = NotificationsFragment()
        val searchFragment = SearchFragment()
        val tripFragment = TripsFragment1()

        //first fragment after login or register
        makeCurrentFragment(profileFragment)

        //on click listener to the bottomNavigation where the fragment will be changed depending on the id of the clicked button in the navigation bar
        binding.bottomNavigation.setOnItemSelectedListener { id ->
            when (id) {
                R.id.profile -> makeCurrentFragment(profileFragment)
                R.id.search -> makeCurrentFragment(searchFragment)
                R.id.trip -> makeCurrentFragment(tripFragment)
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
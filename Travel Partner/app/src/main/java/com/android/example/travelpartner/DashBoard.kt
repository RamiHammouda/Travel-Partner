package com.android.example.travelpartner

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.example.travelpartner.Fragments.HomeFragment
import com.android.example.travelpartner.Fragments.NotificationFragment
import com.android.example.travelpartner.Fragments.ProfileFragment
import com.android.example.travelpartner.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        val profileFragment = ProfileFragment()
        val notificationFragment = NotificationFragment()
        val searchFragment = SearchFragment()
        val homeFragment = HomeFragment()


        makeCurrentFragment(profileFragment)

        bottom_navigation.setOnItemSelectedListener { id ->
            var fragment: Fragment? = null
            when (id) {
                R.id.profile -> makeCurrentFragment(profileFragment)
                R.id.search -> makeCurrentFragment(searchFragment)
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.notification -> makeCurrentFragment(notificationFragment)
            }
            true
        }
    }
        private fun makeCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, fragment)
                commit()
            }
}

package com.android.example.travelpartner

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentTrips6Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TripsFragment6.newInstance] factory method to
 * create an instance of this fragment.
 */

class TripsFragment6 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentTrips6Binding //Declare the Data binding variable
    private val tripsViewModel:TripsSharedViewModel by activityViewModels()  //initialize the ViewModel variable
    private val db = Firebase.firestore //declare the dataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTrips6Binding>(inflater,
            R.layout.fragment_trips6,container,false)           //Initialize the Data binding variable

        //val trips6Fragment = TripsFragment6()
        binding.delete.setOnClickListener{
            //binding.tripImage.setImageDrawable(null)
            binding.all.removeAllViews()
            tripsViewModel.userHasTrip = false //when the trip is deleted the user has no more trips so he should be forwarded to first trip fragment when he presses the "myTrips" tab

            //after that we have to forward the user to the page where he can create a new trip because he just deleted his current one
            val trips1Fragment = TripsFragment1()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips1Fragment)
            transaction.commit()
        }

//        val whatIsThePlanText = tripsViewModel.whatIsThePlan //Get the string from the viewModel from fragment number 3
//        binding.plan.text = whatIsThePlanText //set the text we got in the current layout
//
//        binding.startDate.text = tripsViewModel.startDate //Assign the startDate value from the viewModel to the corresponding from the current fragment
//        binding.endDate.text = tripsViewModel.endDate //Assign the endDate value from the viewModel to the corresponding from the current fragment
//
//        binding.destinationCountry.text = tripsViewModel.destination //Assign the destination value from the viewModel to the corresponding Textview from the current fragment
//
//        binding.withYourName.text = tripsViewModel.name //Assign the name value from the profile fragment to a TextView in the current fragment

        //the following block will be responsible to get the trip data of the logged in user from the firebase database by identifying him through his name
        db.collection("trips")
            .whereEqualTo("name", tripsViewModel.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //here we assign the trip data to the viewTexts in the fragment and then we store the data in the viewModel in case we need it later
                    binding.destinationCountry.text = document["destination"].toString()
                    tripsViewModel.destination = document["destination"].toString()
                    binding.endDate.text = document["endDate"].toString()
                    tripsViewModel.endDate = document["endDate"].toString()
                    binding.startDate.text = document["startDate"].toString()
                    tripsViewModel.startDate = document["startDate"].toString()
                    binding.plan.text = document["thePlan"].toString()
                    tripsViewModel.whatIsThePlan = document["thePlan"].toString()
                    binding.withYourName.text = document["name"].toString()
                    tripsViewModel.name = document["name"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        return binding!!.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TripsFragment6.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TripsFragment6().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.android.example.travelpartner

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.travelpartner.databinding.FragmentTrips5Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TripsFragment5.newInstance] factory method to
 * create an instance of this fragment.
 */
class TripsFragment5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        val binding = DataBindingUtil.inflate<FragmentTrips5Binding>(inflater,
            R.layout.fragment_trips5,container,false)           //Initialize the Data binding variable

        //setOnClickListener for the button called "next" so that when that button is clicked the next fragment will appear in place of the second one
        binding.publishButton.setOnClickListener {

            val trips6Fragment = TripsFragment6()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

            //When the publish button is clicked we have to store all the data gathered for the "new Trip"
            // Create a new user with a first and last name
            val trip = hashMapOf(
                "name" to tripsViewModel.name,
                "destination" to tripsViewModel.destination,
                "startDate" to tripsViewModel.startDate,
                "endDate" to tripsViewModel.endDate,
                "preferredGender" to tripsViewModel.preferredGender,
                "minimumAge" to tripsViewModel.minimumAge,
                "maximumAge" to tripsViewModel.maximumAge,
                "thePlan" to binding.thePlan.text.toString(),
            )

            //Every user will have a document with his name where his trip will be saved
            db.collection("trips").document(tripsViewModel.name + " Trip").set(trip)

            //show next fragment
            transaction.replace(R.id.fl_wrapper, trips6Fragment)
            transaction.commit()
            tripsViewModel.whatIsThePlan =
                binding.thePlan.text.toString()  //saving the plan in ViewModel on button Click
            tripsViewModel.userHasTrip =
                true //set the verification variable to true. This variable will be responsible for which fragment will appear when the user clicks on my trips and a trip is already created
        }

        //setOnClickListener for the button called "previous" so that when that button is clicked the previous fragment will appear in place of the current one
        binding.previousButton.setOnClickListener{
            val trips3Fragment = TripsFragment3()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips3Fragment)
            transaction.commit()
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
         * @return A new instance of fragment TripsFragment5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TripsFragment5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
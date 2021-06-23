package com.android.example.travelpartner


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentProfileBinding //Declare the Data binding variable
    private val tripsViewModel:TripsSharedViewModel by activityViewModels() //initialize the ViewModel variable
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
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,
            R.layout.fragment_profile,container,false)           //Initialize the Data binding variable


        //the following block will be responsible to get the user data from the firebase database by identifying him through his email that he used for the login
        db.collection("users")
            .whereEqualTo("email", tripsViewModel.email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //here we assign the user data to the viewTexts in the fragment and then we store the data in the viewModel in case we need it later
                    binding.name.text = document["name"].toString()
                    tripsViewModel.name = document["name"].toString()
                    binding.address.text = document["address"].toString()
                    tripsViewModel.addresss = document["address"].toString()
                    binding.phoneNumber.text = document["phoneNumber"].toString()
                    tripsViewModel.phoneNumber = document["phoneNumber"].toString()
                    binding.twitter.text = document["twitterLink"].toString()
                    tripsViewModel.twitter = document["twitterLink"].toString()
                    binding.facebook.text = document["facebookLink"].toString()
                    tripsViewModel.addresss = document["facebookLink"].toString()
                    binding.aboutMe.text = document["aboutMe"].toString()
                    tripsViewModel.aboutMe = document["aboutMe"].toString()
                    binding.email.text = document["email"].toString()
                    tripsViewModel.email = document["email"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        //the following block will give to a boolean variable stored in viewModel a corresponding value
        //if the user has a trip, that means a trip that is saved in the firebase Database then the boolean variable will receive "true"
        //when this variable is true, when the user clicks on the "myTrips" button he will be forwarded to the fragment "Trips6Fragment" where he can see his trip
        //if the user hasn't created any trip yet, that means he has no trip that is saved in the firebase Database then the boolean variable will receive "false"
        //when this variable is false, when the user clicks on the "myTrips" button he will be forwarded to the fragment "Trips1Fragment" where he can create a new trip
        db.collection("trips")
            .whereEqualTo("name", tripsViewModel.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id}  => ${document.data}")
                    tripsViewModel.userHasTrip = true //when the user has a trip saved in the firebase database
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                tripsViewModel.userHasTrip = false //when the use has no trips saved in the firebase database
            }



        //setOnClickListener for the settings icon so that when that when the icon is clicked the settings fragment will appear
        binding.settings.setOnClickListener{
            val profileSettingsFragment = ProfileSettingsFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, profileSettingsFragment)
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
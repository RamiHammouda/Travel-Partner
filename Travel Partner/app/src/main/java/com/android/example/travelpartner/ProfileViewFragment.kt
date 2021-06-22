package com.android.example.travelpartner

import android.content.ContentValues
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
import com.android.example.travelpartner.databinding.FragmentProfileViewBinding
import com.android.example.travelpartner.databinding.FragmentSearchBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileViewFragment : Fragment() {
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
        val binding = DataBindingUtil.inflate<FragmentProfileViewBinding>(inflater,
            R.layout.fragment_profile_view,container,false)           //Initialize the Data binding variable

        //get the name of the person shown in the search tab so that we show his profile informations
        binding.name.text = tripsViewModel.currentSearchProfile
        //this block will show the profile data of the person shown on the search tab
        db.collection("users")
            .whereEqualTo("name", tripsViewModel.currentSearchProfile)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id}  => ${document.data}")
                        binding.address.text = document.data["address"].toString()
                        binding.aboutMe.text = document.data["aboutMe"].toString()
                        binding.phoneNumber.text = document.data["phoneNumber"].toString()
                        binding.twitter.text = document.data["twitterLink"].toString()
                        binding.facebook.text = document.data["facebookLink"].toString()
                    }
                }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        //setOnClickListener for back arrow so that when that arrow is clicked the search fragment will appear in place of the current one
        binding.arrowBack.setOnClickListener{
            val searchFragment = SearchFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, searchFragment)
            transaction.commit()
        }

        //setOnClickListener for the star so when the star is clicked a toast will be shown
        //this star will be responsible for the notifications
        binding.star.setOnClickListener{
            Toast.makeText(activity,"You have Starred this Profile", Toast.LENGTH_LONG).show()
            val trip = hashMapOf(
                "name" to binding.name.text,
                "star" to true,
                "starredBy" to tripsViewModel.name
            )
            //Every user will have a document with his name where his star status will be saved
            db.collection("users").document(binding.name.text as String? + " starred").set(trip)
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
         * @return A new instance of fragment ProfileViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
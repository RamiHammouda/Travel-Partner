package com.android.example.travelpartner

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentSearchBinding
import com.android.example.travelpartner.databinding.FragmentTrips5Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
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
        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater,
            R.layout.fragment_search,container,false)           //Initialize the Data binding variable
        var str:String? = null

        //this block will show the data of one person that has the same destination
        db.collection("trips")
            .whereEqualTo("destination", tripsViewModel.destination)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //this if condition will be used to prevent the fragment from showing the user own trip in the search tab
                    if (document.data["name"].toString() != tripsViewModel.name) {
                        //we have to make sure we restore visibility if it is set as gone when the user did not find any partners on a particular trip but now there are options
                        binding.wholeSuggestionTab.visibility = View.VISIBLE
                        binding.viewProfile.visibility = View.VISIBLE
                        binding.button.visibility = View.VISIBLE
                        Log.d(TAG, "${document.id}  => ${document.data}")
                        binding.name.text = document.data["name"].toString()
                        str = document.data["name"].toString()
                        binding.destinationDate.text =
                            document.data["destination"].toString() + " From " + document.data["startDate"].toString() + " to " + document.data["endDate"].toString()
                        binding.plan.text = document.data["thePlan"].toString()
                        tripsViewModel.currentSearchProfile = document.data["name"].toString()  //save the name of the person so we can implement his view profile button
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        //this "next" button will show a second person with the same destination
        binding.button.setOnClickListener {
            //this block will show the data of a second person that has the same destination with the date of travel and his plan
            db.collection("trips")
                .whereEqualTo("destination", tripsViewModel.destination)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.data["name"] != str) {
                            //we have to make sure we restore visibility if it is set as gone when the user did not find any partners on a particular trip but now there are options
                            binding.wholeSuggestionTab.visibility = View.VISIBLE
                            binding.viewProfile.visibility = View.VISIBLE
                            binding.button.visibility = View.VISIBLE

                            Log.d(TAG, "${document.id}  => ${document.data}")
                            binding.name.text = document.data["name"].toString()
                            binding.destinationDate.text =
                                document.data["destination"].toString() + " From " + document.data["startDate"].toString() + " to " + document.data["endDate"].toString()
                            binding.plan.text = document.data["thePlan"].toString()
                            tripsViewModel.currentSearchProfile = document.data["name"].toString()  //save the name of the person so we can implement his view profile button
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }

        //setOnClickListener for the button called "previous" so that when that button is clicked the previous fragment will appear in place of the current one
        binding.viewProfile.setOnClickListener{
            val viewProfileFragment = ProfileViewFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, viewProfileFragment)
            transaction.commit()
        }

        //binding.name is equal to example1 means that nothing is changed and that means that there is no possible trip partners so we have to hide the layout and give a feedback "no available suggestions"
        if (binding.name.text == "Example1")
        {
            binding.wholeSuggestionTab.visibility = View.GONE
            binding.viewProfile.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.suggestions.text = "No Available Suggestions"
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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
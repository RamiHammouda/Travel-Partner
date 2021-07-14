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
import com.android.example.travelpartner.databinding.FragmentNotificationsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationsFragment : Fragment() {
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
        val binding = DataBindingUtil.inflate<FragmentNotificationsBinding>(inflater,
            R.layout.fragment_notifications,container,false)           //Initialize the Data binding variable

        //set the viewProfile button to invisible
        binding.viewProfile.visibility = View.GONE

        //with this block we will show the notification if the user profile has been starred by getting the field called "starredBy" if it exists
        db.collection("users")
            .whereEqualTo("name", tripsViewModel.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //if starredBy exists
                        if (document.data["starredBy"].toString() != "null") {
                            binding.viewProfile.visibility = View.VISIBLE //first of all we must set the button "viewProfile" to visible
                            //after that we have to show the notification text
                            Log.d(ContentValues.TAG, "${document.id}  => ${document.data}")
                            binding.notif.text =
                                document.data["starredBy"].toString() + " wants to join you on your trip to " + tripsViewModel.destination + " Start talking now!"
                            tripsViewModel.currentSearchProfile =
                                document.data["starredBy"].toString()
                        }
                    }
                }
            .addOnFailureListener {  exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)

            }

        //setOnClickListener for the button called "previous" so that when that button is clicked the notification fragment will appear in place of the current one
        binding.viewProfile.setOnClickListener{
            val viewProfileFragment = ProfileViewFromNotification()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, viewProfileFragment)
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
         * @return A new instance of fragment NotificationsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
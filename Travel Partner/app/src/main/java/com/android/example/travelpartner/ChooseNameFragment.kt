package com.android.example.travelpartner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentChooseNameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseNameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentChooseNameBinding //Declare the Data binding variable
    private val tripsViewModel: TripsSharedViewModel by activityViewModels() //initialize the ViewModel variable
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
        val binding = DataBindingUtil.inflate<FragmentChooseNameBinding>(
            inflater,
            R.layout.fragment_choose_name, container, false
        )           //Initialize the Data binding variable



        //binding.name.text = tripsViewModel.name

        //setOnClickListener for the button called "validate" so that when that button is clicked the next fragment will appear and the name will be saved to the viewModel
        binding.validate.setOnClickListener {
            val profileFragment = ProfileFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, profileFragment)
            transaction.commit()

            //Save name to viewModel variable from the editText
            tripsViewModel.name = binding.name.text.toString()

            //On validate click we have to create a new profile for the new user and initialize all variables that will be saved to the firebase database using a HashMap
            val user = hashMapOf(
                "name" to tripsViewModel.name,
                "email" to tripsViewModel.email,
                "aboutMe" to tripsViewModel.aboutMe,
                "address" to tripsViewModel.address,
                "phoneNumber" to tripsViewModel.phoneNumber,
                "twitterLink" to tripsViewModel.twitter,
                "facebookLink" to tripsViewModel.facebook,
            )
            //Every user will have a document with his name where his trip will be saved
            db.collection("users").document(tripsViewModel.email + " Profile").set(user)
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
         * @return A new instance of fragment ChooseNameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseNameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
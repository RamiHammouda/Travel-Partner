package com.android.example.travelpartner


import android.os.Bundle
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

        //Assign the Data from the viewModel
        binding.name.text = tripsViewModel.name
        binding.aboutMe.text = tripsViewModel.aboutMe
        binding.phoneNumber.text = tripsViewModel.phoneNumber
        binding.twitter.text = tripsViewModel.twitter
        binding.facebook.text = tripsViewModel.facebook
        binding.address.text = tripsViewModel.addresss

        // Create a new user with all his properties
        val user = hashMapOf(
            "name" to tripsViewModel.name,
            "aboutMe" to tripsViewModel.aboutMe,
            //"email" to tripsViewModel.,
            "phoneNumber" to tripsViewModel.phoneNumber,
            "twitterLink" to tripsViewModel.twitter,
            "facebookLink" to tripsViewModel.facebook,
            "age" to tripsViewModel.age,
            "gender" to tripsViewModel.gender,
            "address" to tripsViewModel.addresss,
        )

        //Every user will have a document identifying him
        db.collection("users").document(tripsViewModel.name + " Profile").set(user)


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
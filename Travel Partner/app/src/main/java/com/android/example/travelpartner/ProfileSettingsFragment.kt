package com.android.example.travelpartner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentProfileSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentProfileSettingsBinding //Declare the Data binding variable
    private val tripsViewModel:TripsSharedViewModel by activityViewModels() //initialize the ViewModel variable

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
        val binding = DataBindingUtil.inflate<FragmentProfileSettingsBinding>(inflater,
            R.layout.fragment_profile_settings,container,false)           //Initialize the Data binding variable

        //setOnClickListener for the "Save all" button so that when that button is pressed all the new data will be saved and you will be forwarded to the profile fragment
        binding.saveAll.setOnClickListener{
            //transaction to another fragment
            val profileFragment = ProfileFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, profileFragment)
            transaction.commit()

            //saving data to viewModel if the EditText box text is changed
            if (binding.aboutMe.text.toString().isNotEmpty())
            {
                tripsViewModel.aboutMe = binding.aboutMe.text.toString()
            }
            if (binding.name.text.toString().isNotEmpty())
            {
                tripsViewModel.name = binding.name.text.toString()
            }
            if (binding.phoneNumber.text.toString().isNotEmpty())
            {
                tripsViewModel.phoneNumber = binding.phoneNumber.text.toString()
            }
            if (binding.twitter.text.toString().isNotEmpty())
            {
                tripsViewModel.twitter = binding.twitter.text.toString()
            }
            if (binding.facebook.text.toString().isNotEmpty())
            {
                tripsViewModel.facebook = binding.facebook.text.toString()
            }
            if (!binding.address.text.toString().isEmpty())
            {
                tripsViewModel.addresss = binding.address.text.toString()
            }
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
         * @return A new instance of fragment ProfileSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
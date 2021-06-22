package com.android.example.travelpartner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.android.example.travelpartner.databinding.FragmentTrips3Binding
import kotlinx.android.synthetic.main.fragment_trips3.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TripsFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class TripsFragment3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentTrips3Binding //Declare the Data binding variable
    private val tripsViewModel: TripsSharedViewModel by activityViewModels() //initialize the ViewModel variable

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
        val binding = DataBindingUtil.inflate<FragmentTrips3Binding>(
            inflater,
            R.layout.fragment_trips3, container, false
        )           //Initialize the Data binding variable


        //setOnClickListener for the button called "next" so that when that button is clicked the next fragment will appear in place of the second one
        binding.nextButton.setOnClickListener {
            val trips5Fragment = TripsFragment5()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips5Fragment)
            transaction.commit()

            tripsViewModel.minimumAge =
                binding.minimumAge.text.toString()  //saving the minimumAge in ViewModel on "next" button Click
            tripsViewModel.maximumAge =
                binding.minimumAge.text.toString()  //saving the maximumAge in ViewModel on "next" button Click

            //The following if conditions will verify which checkbox is checked and depending on the checked box the preferred gender will be saved
            if (binding.checkBoxAll.isChecked) {
                tripsViewModel.preferredGender = "All"
            } else
                if (binding.checkBoxMale.isChecked) {
                    tripsViewModel.preferredGender = "Male"
                } else
                    if (binding.checkBoxFemale.isChecked) {
                        tripsViewModel.preferredGender = "Female"
                    } else
                        if (binding.checkBoxOther.isChecked) {
                            tripsViewModel.preferredGender = "Other"
                        }
        }

        //setOnClickListener for the button called "previous" so that when that button is clicked the previous fragment will appear in place of the current one
        binding.previousButton.setOnClickListener {
            val trips2Fragment = TripsFragment2()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips2Fragment)
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
         * @return A new instance of fragment TripsFragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TripsFragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
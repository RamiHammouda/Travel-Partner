package com.android.example.travelpartner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import com.android.example.travelpartner.databinding.FragmentTrips4Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TripsFragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
//Where To Fragment
class TripsFragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTrips4Binding? = null //Declare the Data binding variable
    private val binding get() = _binding!! //assign the _binding value to the binding variable

    override fun onResume() {
        //onResume function is used so that we don't loose the dropdown items when navigating between fragments
        super.onResume()
        val countries = resources.getStringArray(R.array.countries) //get the countries array from resource and assign it to the variable "countries"
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_countries, countries) //create an adapter for our array
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

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
        _binding = FragmentTrips4Binding.inflate(inflater,container,false)   //Initialize the Data binding variable

        //setOnClickListener for the button called "next" so that when that button is clicked the next fragment will appear in place of the second one
        binding.nextButton.setOnClickListener{
            val trips2Fragment = TripsFragment2()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips2Fragment)
            transaction.commit()
        }

        //setOnClickListener for the button called "previous" so that when that button is clicked the previous fragment will appear in place of the current one
        binding.previousButton.setOnClickListener{
            val trips1Fragment = TripsFragment1()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, trips1Fragment)
            transaction.commit()
        }

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TripsFragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TripsFragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
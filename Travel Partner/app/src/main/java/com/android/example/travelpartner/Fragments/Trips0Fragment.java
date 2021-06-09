package com.android.example.travelpartner.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.example.travelpartner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Trips0Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Trips0Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Trips0Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Trips0Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Trips0Fragment newInstance(String param1, String param2) {
        Trips0Fragment fragment = new Trips0Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button b1;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trips0, container, false);

        b1 = v.findViewById(R.id.create_first_trip);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v)
          {
              Trips1Fragment trips1Fragment = new Trips1Fragment();
              FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
              transaction.replace(R.id.fl_wrapper, trips1Fragment);
              transaction.commit();
          }
        });



        return v;
    }
}
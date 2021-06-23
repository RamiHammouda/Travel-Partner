package com.android.example.travelpartner

import androidx.lifecycle.ViewModel

class TripsSharedViewModel: ViewModel() {
    var whatIsThePlan:String? = "nothing"
    var startDate:String? = "17.05.2021"
    var endDate:String? = "27.05.2021"
    var minimumAge:String? = "0"
    var maximumAge:String? = "100"
    var preferredGender:String? = "All"
    var destination:String? = "Spain"
    var age:String? = "20"
    var email:String? = null
    var gender:String? = null
    var currentSearchProfile:String? = null

    var aboutMe:String? = "NAN"
    var name:String? = null
    var phoneNumber:String? = "NAN"
    var twitter:String? = "NAN"
    var facebook:String? = "NAN"
    var addresss:String? = "NAN"

    var userHasTrip = false
}
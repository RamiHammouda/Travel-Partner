package com.android.example.travelpartner

import androidx.lifecycle.ViewModel

class TripsSharedViewModel: ViewModel() {
    var whatIsThePlan:String? = "nothing"
    var startDate:String? = "17.05.2021"
    var endDate:String? = "27.05.2021"
    var destination:String? = "Spain"

    var aboutMe:String? = "my name is my name is my name is my name is my name is my name is my name is my name is my name is my name is my name is"
    var name:String? = "Rami Hammouda"
    var phoneNumber:String? = "+491565128656"
    var twitter:String? = "/Rami"
    var facebook:String? = "facebook.com/rami.hammouda"
    var addresss:String? = "Berlin,Germany"

    var tripCreatedVerif = false
}
package com.herts.partimer.request

import java.io.Serializable

class AddJobRequest : Serializable {
    var userId: Int = 0
    var jobTitle: String = ""
    var category: String = ""
    var role: String = ""
    var city: String = ""
    var experience: Int = 0
    var payPerHour: Double = 0.0
    var monday: String = ""
    var tuesday: String = ""
    var wednesday: String = ""
    var thursday: String = ""
    var friday: String = ""
    var saturday: String = ""
    var sunday: String = ""
    var jobReferences: Boolean = false
    var immediateJoining: Boolean = false
    var verifiedEmail: Boolean = true
    var profilePicture: Boolean = true
}
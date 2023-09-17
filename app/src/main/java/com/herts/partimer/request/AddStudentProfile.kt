package com.herts.partimer.request

import java.io.Serializable

class AddStudentProfile : Serializable {

    var userId: String = ""
    var city: String = ""
    var monday: String = ""
    var tuesday: String = ""
    var wednesday: String = ""
    var thursday: String = ""
    var friday: String = ""
    var saturday: String = ""
    var sunday: String = ""
    var immediateJoining: Boolean = false
    var categoryList: ArrayList<Cat>? = null
    var experienceList: ArrayList<Experience>? = null
    var referenceList: ArrayList<Reference>? = null
}

class Cat : Serializable {

    var category: String = ""
    var role: String = ""
}

class Experience : Serializable {

    var jobDescription: String = ""
    var category: String = ""
    var role: String = ""
    var year: Int = 0
}

class Reference : Serializable {

    var name: String = ""
    var relation: String = ""
    var email: String = ""
}
package com.herts.partimer.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class JobsByCategoryResponse : Serializable {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("jobProfileList")
    var jobProfileList: ArrayList<JobProfileList>? = null

    @SerializedName("allJobProfileList")
    var allJobProfileList: ArrayList<JobProfileList>? = null
}

class JobProfileList : Serializable {


    var jobProfileId: Int? = null
    var userId: Int? = null
    var jobTitle: String? = null
    var category: String? = null
    var role: String? = null
    var city: String? = null
    var experience: Int? = null
    var payPerHour: Int? = null
    var monday: String? = null
    var tuesday: String? = null
    var wednesday: String? = null
    var thursday: String? = null
    var friday: String? = null
    var saturday: String? = null
    var sunday: String? = null
    var immediateJoining: Boolean? = null
    var verifiedEmail: Boolean? = null
    var profilePicture: Boolean? = null
    var matchingObject: Matching? = null
    var jobReferences: Boolean?= null

}

class Matching : Serializable {
    var reference: Reference? = null
    var role: Role? = null
    var city: City? = null
    var emailVerification: EmailVerification? = null
    var immediateJoining: ImmediateJoining? = null
    var availability: Availability? = null
    var category: Category? = null
    var experience: Experience? = null
    var percentage: PercentageJ? = null
    var profilePicture:ProfilePictureJ?= null
}

class ProfilePictureJ:Serializable{
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0
}

class PercentageJ : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0
}

class Reference : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class Role : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class City : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class EmailVerification : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class ImmediateJoining : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class Availability : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class Category : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class Experience : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

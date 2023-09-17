package com.herts.partimer.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StudentsByCategoryResponse : Serializable {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("studentProfileList")
    var studentProfileList: ArrayList<StudentProfileList>? = null

    @SerializedName("allStudentProfileList")
    var allStudentProfileList: ArrayList<StudentProfileList>? = null
}

class StudentProfileList : Serializable {


    var studentProfileId: Int? = null
    var userId: Int? = null
    var city: String? = null
    var monday: String? = null
    var tuesday: String? = null
    var wednesday: String? = null
    var thursday: String? = null
    var friday: String? = null
    var saturday: String? = null
    var sunday: String? = null
    var immediateJoining: Boolean? = null
    var verifiedEmail: Boolean? = null
    var profilePicture: String? = null
    var matchingObject: MatchingS? = null
    var categoryList: ArrayList<CategoryList>? = null
    var experienceList: ArrayList<ExperienceList>? = null
    var referenceList: ArrayList<ReferenceList>? = null
    var user: UserS? = null
}

class UserS : Serializable {
    var user_id: Int? = null
    var role_id: Int? = null
    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null
}

class CategoryList : Serializable {
    var studentCategoryId: Int? = null
    var userId: Int? = null
    var category: String? = null
    var role: String? = null
}

class ExperienceList : Serializable {
    var studentCategoryId: Int? = null
    var userId: Int? = null
    var jobDescription: String? = null
    var category: String? = null
    var role: String? = null
    var year: Int? = null
}

class ReferenceList : Serializable {
    var studentCategoryId: Int? = null
    var userId: Int? = null
    var name: String? = null
    var relation: String? = null
    var email: String? = null
}


class MatchingS : Serializable {
    var reference: ReferenceS? = null
    var profilePicture: ProfilePicture? = null
    var role: Role? = null
    var city: City? = null
    var emailVerification: EmailVerificationS? = null
    var immediateJoining: ImmediateJoiningS? = null
    var availability: AvailabilityS? = null
    var category: CategoryS? = null
    var experience: ExperienceS? = null
    var percentage: Percentage? = null
}

class Percentage : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0
}

class ProfilePicture : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0
}

class ReferenceS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class RoleS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class CityS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class EmailVerificationS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class ImmediateJoiningS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class AvailabilityS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class CategoryS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

class ExperienceS : Serializable {
    var match: Double = 0.0
    var union: Double = 0.0
    var all: Double = 0.0
    var weights: Double = 0.0
    var percentage: Double = 0.0

}

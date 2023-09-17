package com.herts.partimer.request

import java.io.Serializable

class AddStudentApproachRequest : Serializable {

    var userId: Int = 0
    var studentProfileId: Int = 0
    var status: Int = 0
}
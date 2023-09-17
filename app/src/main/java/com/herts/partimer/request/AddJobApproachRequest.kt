package com.herts.partimer.request

import java.io.Serializable

class AddJobApproachRequest : Serializable {

    var userId: Int = 0
    var jobProfileId: Int = 0
    var status: Int = 0
}
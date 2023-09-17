package com.herts.partimer.request

import java.io.Serializable

class SendCodeRequest : Serializable {

    var userId: Int = 0
    var emailAddress: String = ""
}
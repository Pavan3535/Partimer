package com.herts.partimer.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SendCodeResponse : Serializable {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

}
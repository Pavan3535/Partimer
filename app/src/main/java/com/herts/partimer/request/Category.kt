package com.herts.partimer.request

import com.herts.partimer.view.Role
import java.io.Serializable

class Category() : Serializable {

    var category: String = ""
    var isSelected: Boolean = false
    var role: ArrayList<Roles>? = null
}

class Roles() : Serializable {

    var role: String = ""
    var isSelected: Boolean = false
}
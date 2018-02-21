package com.banana.verywild.privilegemeeting

import java.io.Serializable

/**
 * Created by lineplus on 20/02/2018.
 */

class Message : Serializable {
    var title: String? = null
    var message: String? = null

    constructor() {}

    constructor(title: String, message: String) {
        this.title = title
        this.message = message
    }
}

package com.masterjefferson.flockulator.frameworks.realm.storage

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * ${FILE_NAME}
 * Created by jeff on 9/24/17.
 */
open class RealmTweet(
    @PrimaryKey var id: Long = 0,
    var timestamp: Date = Date(),
    var userName: String = "",
    var text: String = ""
) : RealmObject()

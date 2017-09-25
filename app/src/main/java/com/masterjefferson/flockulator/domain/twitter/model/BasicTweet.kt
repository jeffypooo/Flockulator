package com.masterjefferson.flockulator.domain.twitter.model

import java.util.*

/**
 * ${FILE_NAME}
 * Created by jeff on 9/22/17.
 */
data class BasicTweet(val id: Long, val timestamp: Date, val userName: String, val text: String) {

  fun mentions(): List<String>? {
    var regex = Regex(".*@([A-Za-z0-9_]+).*")
    val groups = regex.find(text)?.groupValues
    return groups?.subList(1, groups.size)
  }

  fun hashTags(): List<String>? {
    var regex = Regex(".*#([A-Za-z0-9_]+).*")
    val groups = regex.find(text)?.groupValues
    return groups?.subList(1, groups.size)
  }
}
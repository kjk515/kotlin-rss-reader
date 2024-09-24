package rss

import java.util.*

class Article(
    val guid: String,
    val title: String,
    val date: Date,
    val contents: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        return guid == other.guid
    }

    override fun hashCode(): Int {
        return guid.hashCode()
    }
}
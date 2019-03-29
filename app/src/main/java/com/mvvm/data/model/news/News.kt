package com.mvvm.data.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "news")
@JsonClass(generateAdapter = true)
data class News(
    @PrimaryKey
    @Json(name = "title") val title: String,
    @Json(name = "url") val image: String="https://cataas.com/cat"
)
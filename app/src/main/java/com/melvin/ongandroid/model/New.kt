package com.melvin.ongandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class New(
        @SerializedName("title")
        val title:String,
        @SerializedName("descript")
        val descript:String,
        @SerializedName("photo")
        val photo: String
    ): Parcelable

data class NewsList(
    @SerializedName("results")
    val newsList: List<New>
)
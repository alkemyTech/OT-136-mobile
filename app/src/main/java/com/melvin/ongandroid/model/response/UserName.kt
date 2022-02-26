package com.melvin.ongandroid.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserName(
        @SerializedName("name")
        val name:String):Parcelable


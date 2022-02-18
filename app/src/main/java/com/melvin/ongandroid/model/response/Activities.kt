package com.melvin.ongandroid.model.response

import com.google.gson.annotations.SerializedName

data class Activities(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("data") val dataActivities: Array<DataActivities>,
    @SerializedName("Message") val message : String
)

data class DataActivities(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("category_id") val category: Int
)
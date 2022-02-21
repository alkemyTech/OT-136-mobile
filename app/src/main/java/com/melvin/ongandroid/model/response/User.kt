package com.melvin.ongandroid.model.response

import retrofit2.http.Field


data class User(
    @Field("name")val name: String,
    @Field("email")val email: String,
    @Field("password")val password: String
)

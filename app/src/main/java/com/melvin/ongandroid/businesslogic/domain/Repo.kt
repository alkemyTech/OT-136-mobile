package com.melvin.ongandroid.businesslogic.domain

import android.content.Context
import com.melvin.ongandroid.model.User

interface Repo {
    suspend fun postUser(user:User, context: Context?)
    suspend fun postToken(user: String, pass: String, context: Context?)
}
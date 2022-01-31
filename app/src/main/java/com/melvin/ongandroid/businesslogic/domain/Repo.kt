package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.model.User

interface Repo {
    suspend fun postUser(user:User)
}
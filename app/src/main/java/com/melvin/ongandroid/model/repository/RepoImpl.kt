package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.User


class RepoImpl(private val dataSource:DataSource): Repo {
     override suspend fun postUser(user: User, context: Context?){
               dataSource.postRegister(user, context)
    }
}
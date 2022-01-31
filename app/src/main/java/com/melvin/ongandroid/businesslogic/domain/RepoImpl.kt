package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.User


class RepoImpl(private val dataSource:DataSource):Repo {

     override suspend fun postUser(user: User) {
                dataSource.postRegister(user)
    }


}
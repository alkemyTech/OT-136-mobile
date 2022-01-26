package com.melvin.ongandroid.businesslogic.domain

import com.melvin.ongandroid.businesslogic.data.DataSource


class RepoImpl(private val dataSource:DataSource):Repo {

     override suspend fun postUser() {
       return dataSource.addDummyUser()
    }


}
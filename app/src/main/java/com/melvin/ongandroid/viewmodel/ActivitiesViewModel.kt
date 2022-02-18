package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.model.response.Activities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivitiesViewModel : ViewModel() {


    val liveDataActivities = MutableLiveData<Activities>()
    val repoImpl = RepoImpl(DataSource())

    fun getActivities() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = repoImpl.getActivities()
            if (call.isSuccessful)
                liveDataActivities.postValue(call.body())
        }
    }
}
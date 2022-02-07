package com.melvin.ongandroid.model.service

import com.melvin.ongandroid.model.DefaultResponse
import retrofit2.Call
import retrofit2.Response

interface OnAPIResponse {

    fun onSuccess(response: Response<DefaultResponse>)
    fun onFailure(msg: String)
    fun onLoading(response: Response<DefaultResponse>)
}
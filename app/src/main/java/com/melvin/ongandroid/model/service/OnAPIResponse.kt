package com.melvin.ongandroid.model.service

interface OnAPIResponse {
    fun onSuccess()
    fun onFailure(msg: String)
}
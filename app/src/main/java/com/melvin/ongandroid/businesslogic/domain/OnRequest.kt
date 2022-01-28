package com.melvin.ongandroid.businesslogic.domain

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.model.DefaultResponse
import retrofit2.Call
import retrofit2.Response

interface OnRequest {
    fun dialogBuilder(context: Context?)
}


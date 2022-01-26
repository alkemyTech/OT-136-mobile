package com.melvin.ongandroid.businesslogic.data

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.melvin.ongandroid.businesslogic.domain.RestApiService
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.view.SignUpFragment
import java.security.AccessController.getContext

class DataSource {

    private val user=User("juan", "juan@gmail.com","lalala")

    suspend fun addDummyUser() {
        val apiService = RestApiService()
        val userInfo = User(
            name = "Alex",
            email = "alex@gmail.com",
            password = "32",
             )

        apiService.postUser(userInfo) {
            if (it?.email != null) {
                //Toast.makeText(requireContext(),"User was succesfully register",Toast.LENGTH_LONG).show()
            } else {
                //Toast.makeText(requireContext(),"Error registering new user", Toast.LENGTH_LONG).show()
            }
        }
    }
}
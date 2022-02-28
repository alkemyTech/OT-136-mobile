package com.melvin.ongandroid.model.repository

import android.content.Context
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.*
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.Slides
import com.melvin.ongandroid.model.Testimonials
import com.melvin.ongandroid.model.response.*
import com.melvin.ongandroid.model.service.OnAPIResponse
import retrofit2.Response


class RepoImpl(private val dataSource: DataSource): Repo {

    override suspend fun postUser(user: User, context: Context?, onAPIResponse: OnAPIResponse) {
              dataSource.postRegister(user, context, onAPIResponse)
    }

    override suspend fun postToken(user: String, pass: String): Response<VerifyUser> {
        return dataSource.authUser(user,pass)
    }

    override suspend fun getNewsList(newName: String): Resource<List<New>> {
        return dataSource.getNewsByName(newName)
    }

    override suspend fun getFourTestimonials(): Response<Testimonials> {
        return dataSource.getFourTestimonials()
    }
    override suspend fun getAllTestimonials(): Response<Testimonials> {
        return dataSource.getAllTestimonials()
    }

    override suspend fun getSlides(): Response<Slides> {
        return dataSource.getSlides()
    }

    suspend fun getActivities(): ResourceBase<Activities> {
        return dataSource.getActivities()
    }

    override suspend fun getWeList(weName: String): Resource<List<We>> {
        return dataSource.getMembersByName(weName)
    }
    override suspend fun postContact(name: String, phone: String, email: String, message: String)
            : Response<Contacts> {
        return dataSource.postContact(name,phone,email,message)
    }
    override suspend fun getUser(email: String?):Resource<UserName>{
        return dataSource.getUserByName(email)
    }
}
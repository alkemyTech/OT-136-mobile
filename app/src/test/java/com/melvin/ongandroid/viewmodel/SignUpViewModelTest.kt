package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.LiveDataTestUtil
import com.melvin.ongandroid.MainCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@ExperimentalMultiplatform
@RunWith(JUnit4::class)

    class SignUpViewModelTest {
    @get:Rule
     val instant= InstantTaskExecutorRule()

    @get:Rule
    val dataSourceMock :DataSource = mock()
    val coroutineRule = MainCoroutineRule()
    val repoImplMock= RepoImpl(dataSourceMock)
    private lateinit var vm : SignUpViewModel

    @Before
    fun vmForce(){
        vm = SignUpViewModel(repoImplMock)
    }


    @Test
    fun validacionPrueba() {

        val email = "erik@123hotmail.com"
        val pass = 123456


    }


}







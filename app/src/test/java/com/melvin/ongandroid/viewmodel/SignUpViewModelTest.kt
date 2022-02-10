package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.MainCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)

class SignUpViewModelTest {
    @get:Rule
    val instant = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    val dataSourceMock: DataSource = mock()
    val repoImplMock = RepoImpl(dataSourceMock)
    private lateinit var vmMock: SignUpViewModel
    private lateinit var vm: SignUpViewModel


    @Before
    fun vmForce() {
        vm = SignUpViewModel(RepoImpl(DataSource()))
        vmMock = SignUpViewModel(repoImplMock)

    }

    @Test
    fun CuandoElNombreEstaCorrecto() {
        val email = "12345678"

        assertTrue(vm.verifyPassword(email))
    }

    @Test
    fun CuandoElEamilEstaCorrecto() {
        val email = "erik@hotmail.com"
        val result = vm.verifyEmail(email)
        assertTrue(result)
    }
}
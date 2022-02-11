package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.MainCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import org.junit.Assert.*
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

    //test verifyEmail
    @Test
    fun theEmailIsCorrect() {
        val email = "erik@hotmail.com"
        val result = vm.verifyEmail(email)
        assertTrue(result)
    }

    @Test
    fun emailFieldIsEmpty() {
        val email = " "
        val result = vm.verifyEmail(email)
        assertEquals(false, result)
    }

    //test verifyPassword
    @Test
    fun thePasswordIsCorrect() {
        val pass = "erik5678"
        val result = vm.verifyPassword(pass)
        assertTrue(result)
    }

    @Test
    fun passwordFieldIsEmpty() {
        val pass = ""
        val result = vm.verifyPassword(pass)
        assertEquals(false, result)
    }

    //test verifyConfirmPassword
    @Test
    fun theConfirmPasswordIsCorrect() {
        val pass = "erik5678"
        val result = vm.verifyConfirmPassword(pass)
        assertTrue(result)
    }

    @Test
    fun confirmPasswordFieldIsEmpty() {
        val pass = ""
        val result = vm.verifyConfirmPassword(pass)
        assertEquals(false, result)
    }

    @Test
    fun passwordsAreTheSame() {
        val pass = "erik123456"
        val result = vm.verifyPassword(pass)
        val result1 = vm.verifyConfirmPassword(pass)
        assertSame(result, result1)
    }
}
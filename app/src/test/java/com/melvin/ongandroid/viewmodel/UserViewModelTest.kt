package com.melvin.ongandroid.viewmodel

import android.text.Editable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.Response
import com.melvin.ongandroid.MainCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.getOrAwaitValue
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.model.response.Data
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.doNothing

import android.R.string.no

import android.R.attr.password
import org.mockito.Mockito
import org.mockito.kotlin.*


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UserViewModelTest{

    /*
    * Evaluar mediante tests unitarios si la validación de campos de Log In es correcta o no.
    * Deberán evaluarse todos los casos posibles (campos completos, incompletos, mail erroneo, entre otros).
    * -> El formato de mail no es valido
    * -> Contraseña con menos de 4 digitos es invalido
    * -> caracteres invalidos en mail
    * -> caracteres invalidos en contraseña
    * -> Mail no registrado
    * -> Contraseña no coinside con mail
    * -> Mail registrado con contraseña correcta
    * -> Si ya esta logeado
    * */

    //private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val dataSourceMock : DataSource = mock()
    private val repoMock = RepoImpl(dataSourceMock)
    private lateinit var vmMock : UserViewModel

    @Before
    fun setUp() {
        vmMock = UserViewModel(repoMock)
    }

    @Test
    fun `when email and password is registered then login`() {
        val email = "ana@ana.com"
        val pass = "analia"
        val data = Data()

        runBlocking{
            whenever(repoMock.postToken(email,pass))
                .thenReturn(retrofit2.Response.success(VerifyUser(true,data,"user login okey",null)))
            vmMock.postToken(email,pass)
            assertEquals(VerifyUser(true,data,"user login okey", null), vmMock.liveDataUser.getOrAwaitValue())
        }
    }

    @Test
    fun `when email no registred then no login`() {
        val email = "mail@fake.com"
        val pass = "password"

        runBlocking{
            whenever(repoMock.postToken(email,pass))
                .thenReturn(retrofit2.Response.success(VerifyUser(null,null,null,"no token")))
            vmMock.postToken(email,pass)
            assertEquals(VerifyUser(null,null,null,"no token"), vmMock.liveDataUser.getOrAwaitValue())
        }
    }

    @Test
    fun `when email is empty then no login`() {
        //preparacion
        val email = ""
        val pass = "password"
        //doNothing().´when´(vmMock.validateEmail(email),vmMock.validEmail.value=false)
        //doNothing().`when`(vmMock).validateEmail(email)
        doCallRealMethod().whenever(vmMock.validateEmail(email))
            //whenever(vmMock.validateEmail(email))
            //when
            vmMock.validateEmail(email)
            //then
            assertFalse(vmMock.validEmail.getOrAwaitValue())
    }


/*
    @Test
    fun whenMailIsEmptyNoLogin(): Unit = runBlocking {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            val mail = ""
            val pass = "1234"
            vm.postToken(mail, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
    @Test
    fun whenPasswordIsEmptyNoLogin(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val mail = "user@user.com"
            val pass = ""
            vm.postToken(mail, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
    @Test
    fun whenPasswordIsInvalidFormatNoLogin(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val mail = "user@user.com"
            val pass = "a&123"
            vm.postToken(mail, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
    @Test
    fun `when the number of characters in the password is less than 4 no login`(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val mail = "user@user.com"
            val pass = "123"
            vm.postToken(mail, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
*/
}
package com.melvin.ongandroid.viewmodel

import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class UserViewModelTest{

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var vm : UserViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        vm = UserViewModel(RepoImpl(DataSource()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun whenUserInputIsEmptyReturnsError(): Unit = runBlocking {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            val user = ""
            val pass = "1234"
            vm.postToken(user, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
    @Test
    fun whenPasswordInputIsEmptyReturnsError(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val user = "User"
            val pass = ""
            vm.postToken(user, pass)
            val result = vm.liveDataUser.value
            assertNull(result)
        }
    }
}
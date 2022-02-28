package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.MainCoroutineRule
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.getOrAwaitValue
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.model.response.VerifyUser
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UserViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var vm : UserViewModel

    @Before
    fun setUp() {
        vm = UserViewModel(RepoImpl(DataSource()))
    }

    @Test
    fun `when email and password is registered then login`() {
        val email = "ana@ana.com"
        val pass = "analia"

        runBlocking{
            vm.postToken(email,pass)

            assertTrue(vm.liveDataUser.getOrAwaitValue().success!!)
            assertNotNull(vm.liveDataUser.getOrAwaitValue().data)
            assertEquals("user login okey",vm.liveDataUser.getOrAwaitValue().message)
            assertNull(vm.liveDataUser.getOrAwaitValue().messageError)
        }
    }

    @Test
    fun `when email no registered then no login`() {
        val email = "mail@fake.com"
        val pass = "password"

        runBlocking {
            vm.postToken(email,pass)

            assertEquals(VerifyUser(null,null,null,"No token"), vm.liveDataUser.getOrAwaitValue())
        }
    }
}
package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.melvin.ongandroid.TestCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.model.We
import com.melvin.ongandroid.model.repository.Repo
import com.melvin.ongandroid.model.repository.RepoImpl
import org.junit.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: WeViewModel
    @Mock
    private lateinit var Repository: Repo
    @Mock
    private lateinit var weResponseObserver: Observer<Resource<List<We>>>

    @Before
    fun setUp() {
        viewModel = WeViewModel(RepoImpl(DataSource()))
    }

    val emptyList = arrayListOf<We>()
    val weName="jose"

    @Test
    fun `when fetching results ok then return a list successfully`() {
        testCoroutineRule.runBlockingTest {
            viewModel.fetchWeList.observeForever(weResponseObserver)
            whenever(Repository.getWeList(weName)).thenAnswer {
                Resource.Success(emptyList)
            }
        }
        assertNotNull(viewModel.fetchWeList.value)
        assertEquals(Resource.Success(emptyList), viewModel.fetchWeList.value)
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.fetchWeList.observeForever(weResponseObserver)
            verify(weResponseObserver).onChanged(Resource.Loading())
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            viewModel.fetchWeList.observeForever(weResponseObserver)
            whenever(Repository.getWeList(weName)).thenAnswer {
                Resource.Failure<We>(exception)
            }
        }
        assertNotNull(viewModel.fetchWeList.value)
        assertEquals(Resource.Failure<We>(exception), viewModel.fetchWeList.value)
    }

    @After
    fun tearDown() {
        viewModel.fetchWeList.observeForever(weResponseObserver)
    }
}
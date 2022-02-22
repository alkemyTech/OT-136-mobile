package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melvin.ongandroid.TestCoroutineRule
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.getOrAwaitValue
import com.melvin.ongandroid.model.We
import com.melvin.ongandroid.model.repository.RepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: WeViewModel
    private val dataSourceMock: DataSource = mock()
    private val repoImplMock = RepoImpl(dataSourceMock)
    private lateinit var vmMock: WeViewModel

    @Before
    fun setUp() {
        viewModel = WeViewModel(RepoImpl(DataSource()))
        vmMock = WeViewModel(repoImplMock)
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        // GIVEN
        testCoroutineRule.runBlockingTest {
        // WHEN
            viewModel.fetchWeList()
        // THEN
            assertNotNull(viewModel.listWe.getOrAwaitValue())
        }
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.fetchWeList()
        }
        assertTrue(viewModel.loading.getOrAwaitValue())
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
                whenever(repoImplMock.getWeList("hola")).thenThrow(exception)
            vmMock.fetchWeList()
        }
        assertEquals(Resource.Failure<We>(exception),vmMock.listWe.value)
    }
}
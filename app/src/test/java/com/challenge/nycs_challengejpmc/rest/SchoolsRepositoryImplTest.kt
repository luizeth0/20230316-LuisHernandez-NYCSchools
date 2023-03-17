package com.challenge.nycs_challengejpmc.rest

import com.challenge.nycs_challengejpmc.utils.UIState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class SchoolsRepositoryImplTest {

    private lateinit var testObject: SchoolsRepository

    private val mockApi = mockk<SchoolsAPI>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = SchoolsRepositoryImpl(mockApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get schools info when the server retrieves a list of schools returns a SUCCESS state`() {
        // AAA

        // assigment
        coEvery { mockApi.getSchools() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns listOf(mockk(), mockk(), mockk())
        }

        // ACTION
        val job = testScope.launch {
            testObject.getSchools().collect {
                if (it is UIState.SUCCESS) {
                    // ASSERTION
                    assert(true)
                    assertEquals(3, it.response.size)
                }
            }
        }
        job.cancel()
    }
}
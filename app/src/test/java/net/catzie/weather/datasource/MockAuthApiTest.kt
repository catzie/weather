package net.catzie.weather.datasource

import com.google.common.truth.Truth.assertThat
import net.catzie.weather.datasource.auth.MockAuthApi
import net.catzie.weather.model.auth.AuthLoginInput
import net.catzie.weather.model.auth.AuthRegisterInput
import org.junit.Before
import org.junit.Test

class MockAuthApiTest {

    val SUT = MockAuthApi()

    @Before
    fun setUp() {
    }

    @Test
    fun givenLoginFunction_whenProvidedCompleteInput_thenReturnCode200() {
        val authInput = AuthLoginInput("myuser", "mypass")
        val result = SUT.login(authInput)
        assertThat(result.code).isEqualTo(200)
    }

    @Test
    fun givenLoginFunction_whenProvidedNoInput_thenReturnNotCode200() {
        val authInput = AuthLoginInput("", "   ")
        val result = SUT.login(authInput)
        assertThat(result.code).isNotEqualTo(200)
    }

    @Test
    fun givenRegisterFunction_whenProvidedCompleteInput_thenReturnCode200() {
        val authInput = AuthRegisterInput("myuser", "mypass", "fname", "lname")
        val result = SUT.register(authInput)
        assertThat(result.code).isEqualTo(200)
    }

    @Test
    fun givenRegisterFunction_whenProvidedNoInput_thenReturnNotCode200() {
        val authInput = AuthRegisterInput("", "  ", "  ", "")
        val result = SUT.register(authInput)
        assertThat(result.code).isNotEqualTo(200)
    }
}
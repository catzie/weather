package net.catzie.weather.datasource.auth

import net.catzie.weather.Utils.Randoms.getRandomId
import net.catzie.weather.model.auth.AuthLoginInput
import net.catzie.weather.model.auth.AuthRegisterInput
import net.catzie.weather.model.auth.FakeAuthResponse

class MockAuthApi { // Non-mocked version will be ApiInterface


    fun login(authLoginInput: AuthLoginInput): MockRetrofitAuthResponse {//todo on real auth: change MockRetrofitAuthResponse into retrofit2.Response<AuthResponse>
        val mockRetrofitResponse = MockRetrofitAuthResponse()

        with(authLoginInput) {

            // Just check for filled values in this mock function
            if (username.isBlank() || password.isBlank()) {
                // fail
                mockRetrofitResponse.code = 401
                mockRetrofitResponse.body = null
            } else {
                //todo success
                mockRetrofitResponse.code = 200
                mockRetrofitResponse.body = FakeAuthResponse(getRandomId().toString())
            }
        }

        return mockRetrofitResponse
    }

    fun register(authRegisterInput: AuthRegisterInput): MockRetrofitAuthResponse {//todo on real auth: change MockRetrofitAuthResponse into retrofit2.Response<AuthResponse>
        val mockRetrofitResponse = MockRetrofitAuthResponse()

        with(authRegisterInput) {

            // Just check for filled values in this mock function
            if (username.isBlank() || password.isBlank() || firstname.isBlank() || lastname.isBlank()) {
                // fail
                mockRetrofitResponse.code = 401
                mockRetrofitResponse.body = null
            } else {
                //todo success
                mockRetrofitResponse.code = 200
                mockRetrofitResponse.body = FakeAuthResponse(getRandomId().toString())
            }
        }

        return mockRetrofitResponse
    }

}
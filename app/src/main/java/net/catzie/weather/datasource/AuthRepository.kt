package net.catzie.weather.datasource

import net.catzie.social.model.auth.AuthLoginInput
import net.catzie.social.model.auth.AuthRegisterInput
import net.catzie.weather.model.FakeAuthResponse


class AuthRepository(private val mockAuthApi: MockAuthApi) {


    suspend fun login(authInput: AuthLoginInput): MockRetrofitAuthResponse {
        return mockAuthApi.login(authInput)
    }

    suspend fun register(authRegInput: AuthRegisterInput): MockRetrofitAuthResponse {
        return mockAuthApi.register(authRegInput)
    }
}

class MockRetrofitAuthResponse {
    var code = 0
    var body: FakeAuthResponse? = null

    // Set code in repo, get in vm
    fun code(): Int {
        return code
    }

    // Set code in repo, get in vm
    fun body(): FakeAuthResponse? {
        return body
    }

}

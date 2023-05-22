package net.catzie.weather.ui.auth

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import net.catzie.social.model.auth.AuthLoginInput
import net.catzie.social.model.auth.AuthRegisterInput
import net.catzie.weather.R
import net.catzie.weather.datasource.AuthRepository
import net.catzie.weather.datasource.MockAuthApi
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.FakeAuthResponse

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ApiResult<FakeAuthResponse>>()
    val loginResult: LiveData<ApiResult<FakeAuthResponse>> = _loginResult

    private val _registrationResult = MutableLiveData<ApiResult<FakeAuthResponse>>()
    val registrationResult: LiveData<ApiResult<FakeAuthResponse>> = _registrationResult

    fun onClickLogin(username: String, password: String) {

        val authInput = AuthLoginInput(username, password)

        viewModelScope.launch {

            val result = authRepository.login(authInput)

            if (result.code() == 200) {
                result.body()?.let { auth ->

                    // todo Save token or user info

                    // todo Give signal to log user in on success

                }
            } else {

                // Update observers on failure
                _loginResult.value = ApiResult.Error(R.string.login_res_failed)
            }
        }
    }

    fun onClickRegister(username: String, password: String, firstname: String, lastname: String) {

        val authRegInput = AuthRegisterInput(username, password, firstname, lastname)

        viewModelScope.launch {

            val result = authRepository.register(authRegInput)
            if (result.code() == 200) {
                result.body()?.let { auth ->
                    // todo Save token or user info

                    // todo Give signal to log user in on success
                }
            } else {
                _registrationResult.value = ApiResult.Error(R.string.registration_res_failed)
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    return AuthViewModel(
                        AuthRepository(MockAuthApi())
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
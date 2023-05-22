package net.catzie.weather.model.auth

data class AuthRegisterInput(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
)

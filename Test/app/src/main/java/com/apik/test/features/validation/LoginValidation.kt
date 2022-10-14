package com.apik.test.features.validation

object SignInValidation {
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    fun emailValidation(email: String): Boolean{
        if(email.matches(emailPattern) && email.isNotEmpty()){
            return true
        }
        return false
    }
    fun passwordValidation(password: String): Boolean{
        if(password.length > 6){
            return true
        }
        return false
    }
}
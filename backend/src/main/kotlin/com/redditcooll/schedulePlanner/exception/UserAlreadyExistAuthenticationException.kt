package com.redditcooll.schedulePlanner.exception

import org.springframework.security.core.AuthenticationException

class UserAlreadyExistAuthenticationException(msg: String?) : AuthenticationException(msg) {
    companion object {
        private const val serialVersionUID = 5570981880007077317L
    }
}
package com.redditcooll.schedulePlanner.exception

import org.springframework.security.core.AuthenticationException

class OAuth2AuthenticationProcessingException : AuthenticationException {
    constructor(msg: String?, t: Throwable?) : super(msg, t) {}
    constructor(msg: String?) : super(msg) {}

    companion object {
        private const val serialVersionUID = 3392450042101522832L
    }
}
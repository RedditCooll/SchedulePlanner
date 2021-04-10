package com.redditcooll.schedulePlanner.dto

class JwtAuthenticationResponse(jwt: String, buildUserInfo: UserInfo) {
    var accessToken: String? = null
    var user: UserInfo? = null
}
package com.redditcooll.schedulePlanner.dto

class JwtAuthenticationResponse(jwt: String, buildUserInfo: UserInfo) {
    var accessToken: String? = jwt
    var user: UserInfo? = buildUserInfo
}
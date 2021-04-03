package com.redditcooll.schedulePlanner.dto

import lombok.Value

@Value
class JwtAuthenticationResponse(jwt: String, buildUserInfo: UserInfo) {
    var accessToken: String? = null
    var user: UserInfo? = null
}
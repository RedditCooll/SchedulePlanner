package com.redditcooll.schedulePlanner.dto

import lombok.Value

@Value
class UserInfo(toString: String, displayName: String?, email: Any?, roles: MutableList<String>) {
    var id: String? = null
    var displayName: String? = null
    var email: String? = null
    var roles: List<String>? = null
}
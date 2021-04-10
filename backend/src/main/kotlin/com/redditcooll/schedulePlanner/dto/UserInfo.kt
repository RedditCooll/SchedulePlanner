package com.redditcooll.schedulePlanner.dto

class UserInfo(id: String, displayName: String?, email: String?, roles: MutableList<String>) {
    var id: String? = id
    var displayName: String? = displayName
    var email: String? = email
    var roles: List<String>? = roles
}
package com.redditcooll.schedulePlanner.security.oauth2.user

class LinkedinOAuth2UserInfo(attributes: Map<String?, Any?>?) : OAuth2UserInfo(attributes) {
    override val id: String?
        get() = attributes!!["id"] as String?

    override val name: String?
        get() = attributes!!["localizedFirstName"] as String? + " " + attributes!!["localizedLastName"] as String?

    override val email: String?
        get() = attributes!!["emailAddress"] as String?

    override val imageUrl: String?
        get() = attributes!!["pictureUrl"] as String?
}
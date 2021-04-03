package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.dto.SignUpRequest
import com.redditcooll.schedulePlanner.exception.UserAlreadyExistAuthenticationException
import com.redditcooll.schedulePlanner.model.User
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import java.util.*
import kotlin.jvm.Throws

interface UserService {
    @Throws(UserAlreadyExistAuthenticationException::class)
    fun registerNewUser(signUpRequest: SignUpRequest?): User?
    fun findUserByEmail(email: String?): User?
    fun findUserById(id: Long?): Optional<User?>?
    fun processUserRegistration(registrationId: String?, attributes: MutableMap<String?, Any?>?, idToken: OidcIdToken?, userInfo: OidcUserInfo?): LocalUser?
}
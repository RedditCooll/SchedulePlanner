package com.redditcooll.schedulePlanner.security.oauth2

import com.redditcooll.schedulePlanner.exception.OAuth2AuthenticationProcessingException
import com.redditcooll.schedulePlanner.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class CustomOidcUserService : OidcUserService() {
    @Autowired
    private val userService: UserService? = null

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = super.loadUser(userRequest)
        return try {
            userService!!.processUserRegistration(userRequest.clientRegistration.registrationId, oidcUser.attributes, oidcUser.idToken,
                    oidcUser.userInfo)!!
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw OAuth2AuthenticationProcessingException(ex.message, ex.cause)
        }
    }
}
package com.redditcooll.schedulePlanner.security.oauth2

import com.redditcooll.schedulePlanner.dto.SocialProvider
import com.redditcooll.schedulePlanner.exception.OAuth2AuthenticationProcessingException
import com.redditcooll.schedulePlanner.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.web.client.RestTemplate
import java.util.*
import kotlin.jvm.Throws

@Service
class CustomOAuth2UserService : DefaultOAuth2UserService() {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val env: Environment? = null

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)
        return try {
            val attributes: MutableMap<String?, Any?> = HashMap(oAuth2User.attributes)
            val provider = oAuth2UserRequest.clientRegistration.registrationId
            if (provider == SocialProvider.LINKEDIN.providerType) {
                populateEmailAddressFromLinkedIn(oAuth2UserRequest, attributes)
            }
            userService!!.processUserRegistration(provider, attributes, null, null)!!
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw OAuth2AuthenticationProcessingException(ex.message, ex.cause)
        }
    }

    @Throws(OAuth2AuthenticationException::class)
    fun populateEmailAddressFromLinkedIn(oAuth2UserRequest: OAuth2UserRequest, attributes: MutableMap<String?, Any?>) {
        val emailEndpointUri = env!!.getProperty("linkedin.email-address-uri")
        Assert.notNull(emailEndpointUri, "LinkedIn email address end point required")
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth2UserRequest.accessToken.tokenValue)
        val entity: HttpEntity<*> = HttpEntity("", headers)
        var response: ResponseEntity<MutableMap<*, *>>
        response = restTemplate.exchange<MutableMap<*, *>>(emailEndpointUri!!, HttpMethod.GET, entity, MutableMap::class.java)
        val list = response.body!!["elements"] as List<*>?
        val map = (list!![0] as MutableMap<*, *>)["handle~"] as Map<*, *>?
        attributes.putAll(map as Map<out String?, Any?>)
    }
}
package com.redditcooll.schedulePlanner.security.oauth2

import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.util.Assert
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class OAuth2AccessTokenResponseConverterWithDefaults : Converter<Map<String, String?>, OAuth2AccessTokenResponse> {
    private var defaultAccessTokenType = OAuth2AccessToken.TokenType.BEARER
    override fun convert(tokenResponseParameters: Map<String, String?>): OAuth2AccessTokenResponse {
        val accessToken = tokenResponseParameters[OAuth2ParameterNames.ACCESS_TOKEN]
        var accessTokenType = defaultAccessTokenType
        if (OAuth2AccessToken.TokenType.BEARER.value.equals(tokenResponseParameters[OAuth2ParameterNames.TOKEN_TYPE], ignoreCase = true)) {
            accessTokenType = OAuth2AccessToken.TokenType.BEARER
        }
        var expiresIn: Long = 0
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
            try {
                expiresIn = java.lang.Long.valueOf(tokenResponseParameters[OAuth2ParameterNames.EXPIRES_IN])
            } catch (ex: NumberFormatException) {
            }
        }
        var scopes: Set<String>? = emptySet()
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            val scope = tokenResponseParameters[OAuth2ParameterNames.SCOPE]
            scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).collect(Collectors.toSet())
        }
        val additionalParameters: MutableMap<String, Any?> = LinkedHashMap()
        tokenResponseParameters.entries.stream().filter { e: Map.Entry<String, String?> -> !TOKEN_RESPONSE_PARAMETER_NAMES.contains(e.key) }
                .forEach { e: Map.Entry<String, String?> -> additionalParameters[e.key] = e.value }
        return OAuth2AccessTokenResponse.withToken(accessToken).tokenType(accessTokenType).expiresIn(expiresIn).scopes(scopes).additionalParameters(additionalParameters).build()
    }

    fun setDefaultAccessTokenType(defaultAccessTokenType: OAuth2AccessToken.TokenType?) {
        Assert.notNull(defaultAccessTokenType, "defaultAccessTokenType cannot be null")
        this.defaultAccessTokenType = defaultAccessTokenType
    }

    companion object {
        private val TOKEN_RESPONSE_PARAMETER_NAMES = Stream
                .of(OAuth2ParameterNames.ACCESS_TOKEN, OAuth2ParameterNames.TOKEN_TYPE, OAuth2ParameterNames.EXPIRES_IN, OAuth2ParameterNames.REFRESH_TOKEN, OAuth2ParameterNames.SCOPE)
                .collect(Collectors.toSet())
    }
}
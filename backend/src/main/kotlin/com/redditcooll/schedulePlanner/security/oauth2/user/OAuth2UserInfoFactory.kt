package com.redditcooll.schedulePlanner.security.oauth2.user

import com.redditcooll.schedulePlanner.dto.SocialProvider
import com.redditcooll.schedulePlanner.exception.OAuth2AuthenticationProcessingException

object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String?, Any?>?): OAuth2UserInfo {
        return if (registrationId.equals(SocialProvider.GOOGLE.providerType, ignoreCase = true)) {
            GoogleOAuth2UserInfo(attributes)
        } else if (registrationId.equals(SocialProvider.FACEBOOK.providerType, ignoreCase = true)) {
            FacebookOAuth2UserInfo(attributes)
        } else if (registrationId.equals(SocialProvider.GITHUB.providerType, ignoreCase = true)) {
            GithubOAuth2UserInfo(attributes)
        } else if (registrationId.equals(SocialProvider.LINKEDIN.providerType, ignoreCase = true)) {
            LinkedinOAuth2UserInfo(attributes)
        } else if (registrationId.equals(SocialProvider.TWITTER.providerType, ignoreCase = true)) {
            GithubOAuth2UserInfo(attributes)
        } else {
            throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}
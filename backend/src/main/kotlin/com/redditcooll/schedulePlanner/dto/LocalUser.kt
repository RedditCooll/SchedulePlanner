package com.redditcooll.schedulePlanner.dto

import com.redditcooll.schedulePlanner.model.User
import com.redditcooll.schedulePlanner.util.GeneralUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

class LocalUser @JvmOverloads constructor(userID: String?,
                                          password: String?,
                                          enabled: Boolean,
                                          accountNonExpired: Boolean,
                                          credentialsNonExpired: Boolean,
                                          accountNonLocked: Boolean,
                                          authorities: Collection<GrantedAuthority?>?,
                                          var user: User,
                                          idToken: OidcIdToken? = null,
                                          userInfo: OidcUserInfo? = null) : org.springframework.security.core.userdetails.User(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities), OAuth2User, OidcUser {
    private var idToken: OidcIdToken? = idToken
    private var userInfo: OidcUserInfo? = userInfo
    private var attributes: Map<String, Any>? = null

    fun setAttributes(attributes: Map<String?, Any?>?) {
        this.attributes = attributes as Map<String, Any>?
    }

    override fun getName(): String {
        return user.displayName!!
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes!!
    }

    override fun getClaims(): Map<String, Any> {
        return attributes!!
    }

    override fun getUserInfo(): OidcUserInfo {
        return userInfo!!
    }

    override fun getIdToken(): OidcIdToken {
        return idToken!!
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = -2845160792248762779L
        @JvmStatic
		fun create(user: User, attributes: Map<String?, Any?>?, idToken: OidcIdToken?, userInfo: OidcUserInfo?): LocalUser {
            val localUser = LocalUser(userID = user.email, password = user.password, enabled = user.enabled,
                    accountNonExpired = true, credentialsNonExpired = true, accountNonLocked = true,
                    authorities = GeneralUtils.buildSimpleGrantedAuthorities(user.roles!!),
                    user = user, idToken = idToken, userInfo = userInfo)
            localUser.setAttributes(attributes)
            return localUser
        }
    }
}
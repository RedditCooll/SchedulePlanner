package com.redditcooll.schedulePlanner.util

import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.dto.SocialProvider
import com.redditcooll.schedulePlanner.dto.UserInfo
import com.redditcooll.schedulePlanner.model.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import java.util.stream.Collectors

object GeneralUtils {
    fun buildSimpleGrantedAuthorities(roles: Set<Role>): List<SimpleGrantedAuthority> {
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.name))
        }
        return authorities
    }

    fun toSocialProvider(providerId: String): SocialProvider {
        for (socialProvider in SocialProvider.values()) {
            if (socialProvider.providerType == providerId) {
                return socialProvider
            }
        }
        return SocialProvider.LOCAL
    }

    fun buildUserInfo(localUser: LocalUser): UserInfo {
        val roles = localUser.authorities.stream().map { item: GrantedAuthority -> item.authority }.collect(Collectors.toList())
        val user = localUser.user
        return UserInfo(user.id.toString(), user.displayName, user.email, roles)
    }
}
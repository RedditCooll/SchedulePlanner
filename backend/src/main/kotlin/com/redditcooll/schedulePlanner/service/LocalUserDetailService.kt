package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.exception.ResourceNotFoundException
import com.redditcooll.schedulePlanner.model.User
import com.redditcooll.schedulePlanner.util.GeneralUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service("localUserDetailService")
class LocalUserDetailService : UserDetailsService {
    @Autowired
    private val userService: UserService? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): LocalUser {
        val user = userService!!.findUserByEmail(email)
                ?: throw UsernameNotFoundException("User $email was not found in the database")
        return createLocalUser(user)
    }

    @Transactional
    fun loadUserById(id: Long?): LocalUser {
        val user = userService!!.findUserById(id)?.orElseThrow { ResourceNotFoundException("User", "id", id) }
        return createLocalUser(user!!)
    }

    private fun createLocalUser(user: User): LocalUser {
        return LocalUser(user.email, user.password, user.enabled, true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.roles!!), user)
    }
}
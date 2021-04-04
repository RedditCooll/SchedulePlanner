package com.redditcooll.schedulePlanner.config

import com.redditcooll.schedulePlanner.dto.SocialProvider
import com.redditcooll.schedulePlanner.model.Role
import com.redditcooll.schedulePlanner.model.User
import com.redditcooll.schedulePlanner.repo.RoleRepository
import com.redditcooll.schedulePlanner.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class SetupDataLoader : ApplicationListener<ContextRefreshedEvent?> {
    private var alreadySetup = false

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Transactional
    override fun onApplicationEvent(p0: ContextRefreshedEvent) {
        if (alreadySetup) {
            return
        }
        // Create initial roles
        val userRole = createRoleIfNotFound(Role.ROLE_USER)
        val adminRole = createRoleIfNotFound(Role.ROLE_ADMIN)
        val modRole = createRoleIfNotFound(Role.ROLE_MODERATOR)
        createUserIfNotFound("admin@redditcooll.com", mutableSetOf(userRole, adminRole, modRole))
        alreadySetup = true
    }

    @Transactional
    private fun createUserIfNotFound(email: String, roles: Set<Role>): User {
        var user = userRepository!!.findByEmail(email)

        if (user == null) {
            var user = User()
            user.displayName = "Admin"
            user.email = email
            user.password = passwordEncoder!!.encode("admin@")
            user.roles = roles
            user.provider = SocialProvider.LOCAL.providerType
            user.enabled = true
            val now = Calendar.getInstance().time
            user.createdDate = now
            user.modifiedDate = now
            user = userRepository.save(user)
            return user!!
        }
        return user!!
    }

    @Transactional
    private fun createRoleIfNotFound(name: String): Role {
        var role = roleRepository!!.findByName(name)
        if (role == null) {
            role = roleRepository.save(Role(name))
        }
        return role
    }
}
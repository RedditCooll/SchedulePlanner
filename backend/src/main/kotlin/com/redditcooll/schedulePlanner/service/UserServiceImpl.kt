package com.redditcooll.schedulePlanner.service

import com.redditcooll.schedulePlanner.repo.RoleRepository
import com.redditcooll.schedulePlanner.repo.UserRepository
import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.dto.LocalUser.Companion.create
import com.redditcooll.schedulePlanner.dto.SignUpRequest
import com.redditcooll.schedulePlanner.dto.SignUpRequest.Companion.builder
import com.redditcooll.schedulePlanner.dto.SocialProvider
import com.redditcooll.schedulePlanner.exception.OAuth2AuthenticationProcessingException
import com.redditcooll.schedulePlanner.exception.UserAlreadyExistAuthenticationException
import com.redditcooll.schedulePlanner.model.Role
import com.redditcooll.schedulePlanner.model.User
import com.redditcooll.schedulePlanner.security.oauth2.user.OAuth2UserInfo
import com.redditcooll.schedulePlanner.security.oauth2.user.OAuth2UserInfoFactory
import com.redditcooll.schedulePlanner.util.GeneralUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.util.*
import kotlin.jvm.Throws

@Service
class UserServiceImpl : UserService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Transactional(value = "transactionManager")
    @Throws(UserAlreadyExistAuthenticationException::class)
    override fun registerNewUser(signUpRequest: SignUpRequest?): User? {
        if (signUpRequest!!.userID != null && userRepository!!.existsById(signUpRequest.userID!!)) {
            throw UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.userID.toString() + " already exist")
        } else if (userRepository!!.existsByEmail(signUpRequest.email)) {
            throw UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.email.toString() + " already exist")
        }
        var user = buildUser(signUpRequest)
        val now = Calendar.getInstance().time
        user.createdDate = now
        user.modifiedDate = now
        user = userRepository.save(user)
        userRepository.flush()
        return user
    }

    private fun buildUser(formDTO: SignUpRequest?): User {
        val user = User()
        user.displayName = formDTO!!.displayName
        user.email = formDTO.email
        user.password = passwordEncoder!!.encode(formDTO.password)
        val roles = HashSet<Role>()
        roleRepository!!.findByName(Role.ROLE_USER)?.let { roles.add(it) }
        user.roles = roles
        user.provider = formDTO.socialProvider?.providerType
        user.enabled = true
        user.providerUserId = formDTO.providerUserId
        return user
    }

    override fun findUserByEmail(email: String?): User? {
        return userRepository!!.findByEmail(email)
    }

    @Transactional
    override fun processUserRegistration(registrationId: String?, attributes: MutableMap<String?, Any?>?, idToken: OidcIdToken?, userInfo: OidcUserInfo?): LocalUser? {

        // FIXME: when the mail is hidden on gitHub
        //attributes?.put("email", "your-email")

        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId!!, attributes)
        if (StringUtils.isEmpty(oAuth2UserInfo.name)) {
            throw OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider")
        } else if (StringUtils.isEmpty(oAuth2UserInfo.email)) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }
        val userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo)
        var user = findUserByEmail(oAuth2UserInfo.email)
        user = if (user != null) {
            if (!user.provider.equals(registrationId) && !user.provider.equals(SocialProvider.LOCAL.providerType)) {
                throw OAuth2AuthenticationProcessingException("Looks like you're signed up with " + user.provider.toString() + " account. Please use your " + user.provider.toString() + " account to login.")
            }
            updateExistingUser(user, oAuth2UserInfo)
        } else {
            registerNewUser(userDetails)
        }
        return create(user!!, attributes, idToken, userInfo)
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo): User {
        existingUser.displayName = oAuth2UserInfo.name
        return userRepository!!.save(existingUser)
    }

    private fun toUserRegistrationObject(registrationId: String?, oAuth2UserInfo: OAuth2UserInfo): SignUpRequest {
        return builder.addProviderUserID(oAuth2UserInfo.id).addDisplayName(oAuth2UserInfo.name).addEmail(oAuth2UserInfo.email)
                .addSocialProvider(GeneralUtils.toSocialProvider(registrationId!!)).addPassword("changeit").build()
    }

    override fun findUserById(id: Long?): Optional<User?>? {
        return userRepository!!.findById(id!!)
    }

    override fun findAllUsers(): MutableList<User?>{
        return userRepository!!.findAll()
    }
}
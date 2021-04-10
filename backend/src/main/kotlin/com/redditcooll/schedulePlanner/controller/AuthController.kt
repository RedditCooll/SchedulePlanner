package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.dto.*
import com.redditcooll.schedulePlanner.exception.UserAlreadyExistAuthenticationException
import com.redditcooll.schedulePlanner.security.jwt.TokenProvider
import com.redditcooll.schedulePlanner.service.UserService
import com.redditcooll.schedulePlanner.util.GeneralUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userService: UserService? = null

    @Autowired
    var tokenProvider: TokenProvider? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        val authentication = authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(loginRequest!!.email, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider!!.createToken(authentication)
        val localUser = authentication.principal as LocalUser
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpRequest?): ResponseEntity<*> {
        try {
            userService!!.registerNewUser(signUpRequest)
        } catch (e: UserAlreadyExistAuthenticationException) {
            return ResponseEntity(ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok().body(ApiResponse(true, "User registered successfully"))
    }
}
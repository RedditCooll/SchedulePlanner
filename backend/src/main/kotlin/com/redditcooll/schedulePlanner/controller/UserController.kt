package com.redditcooll.schedulePlanner.controller

import com.redditcooll.schedulePlanner.config.CurrentUser
import com.redditcooll.schedulePlanner.dto.LocalUser
import com.redditcooll.schedulePlanner.service.UserService
import com.redditcooll.schedulePlanner.util.GeneralUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser user: LocalUser?): ResponseEntity<*> {
        var userInfo = GeneralUtils.buildUserInfo(user!!)
        return ResponseEntity.ok(userInfo)
    }

    @GetMapping("/user/all")
    fun getAllUsers(): ResponseEntity<*> {
        return ResponseEntity.ok(userService.findAllUsers())
    }

    @get:PreAuthorize("hasRole('USER')")
    @get:GetMapping("/user")
    val userContent: ResponseEntity<*>
        get() = ResponseEntity.ok("User content goes here")

    @get:PreAuthorize("hasRole('ADMIN')")
    @get:GetMapping("/admin")
    val adminContent: ResponseEntity<*>
        get() = ResponseEntity.ok("Admin content goes here")

    @get:PreAuthorize("hasRole('MODERATOR')")
    @get:GetMapping("/mod")
    val moderatorContent: ResponseEntity<*>
        get() = ResponseEntity.ok("Moderator content goes here")
}
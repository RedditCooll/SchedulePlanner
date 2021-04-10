package com.redditcooll.schedulePlanner.dto

import javax.validation.constraints.NotBlank

class LoginRequest {
    var email: @NotBlank String? = null
    var password: @NotBlank String? = null
}
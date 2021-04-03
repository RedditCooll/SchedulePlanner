package com.redditcooll.schedulePlanner.dto

import lombok.Data
import javax.validation.constraints.NotBlank

@Data
class LoginRequest {
    var email: @NotBlank String? = null
    var password: @NotBlank String? = null
}
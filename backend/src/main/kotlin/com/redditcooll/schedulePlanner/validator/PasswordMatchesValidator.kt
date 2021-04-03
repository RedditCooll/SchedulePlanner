package com.redditcooll.schedulePlanner.validator

import com.redditcooll.schedulePlanner.dto.SignUpRequest
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordMatchesValidator : ConstraintValidator<PasswordMatches?, SignUpRequest> {
    override fun isValid(user: SignUpRequest, context: ConstraintValidatorContext): Boolean {
        return user.password.equals(user.matchingPassword)
    }
}
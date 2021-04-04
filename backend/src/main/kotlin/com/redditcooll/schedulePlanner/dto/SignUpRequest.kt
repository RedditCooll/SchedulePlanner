package com.redditcooll.schedulePlanner.dto

import com.redditcooll.schedulePlanner.validator.PasswordMatches
import lombok.Data
import javax.validation.constraints.NotEmpty

@Data
@PasswordMatches
class SignUpRequest(val providerUserId: String?, val displayName: String?, val email: String?, val password: String?, val socialProvider: SocialProvider?) {
    val userID: Long? = null
    val matchingPassword: @NotEmpty String? = null

    class Builder {
        var providerUserID: String? = null
        var displayName: String? = null
        var email: String? = null
        var password: String? = null
        var socialProvider: SocialProvider? = null
        fun addProviderUserID(userID: String?): Builder {
            providerUserID = userID
            return this
        }

        fun addDisplayName(displayName: String?): Builder {
            this.displayName = displayName
            return this
        }

        fun addEmail(email: String?): Builder {
            this.email = email
            return this
        }

        fun addPassword(password: String?): Builder {
            this.password = password
            return this
        }

        fun addSocialProvider(socialProvider: SocialProvider?): Builder {
            this.socialProvider = socialProvider
            return this
        }

        fun build(): SignUpRequest {
            return SignUpRequest(providerUserID, displayName, email, password, socialProvider)
        }
    }

    companion object {
        @JvmStatic
		val builder: Builder
            get() = Builder()
    }
}
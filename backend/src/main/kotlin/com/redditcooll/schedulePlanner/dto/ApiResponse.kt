package com.redditcooll.schedulePlanner.dto

import lombok.Value

@Value
class ApiResponse(b: Boolean, error: String) {
    var success: Boolean? = null
    var message: String? = null
}
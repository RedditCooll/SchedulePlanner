package com.redditcooll.schedulePlanner.exception

import lombok.Getter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(private val resourceName: String?, private val fieldName: String?, private val fieldValue: Any?) : RuntimeException(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)) {

    companion object {
        private const val serialVersionUID = 7004203416628447047L
    }

}
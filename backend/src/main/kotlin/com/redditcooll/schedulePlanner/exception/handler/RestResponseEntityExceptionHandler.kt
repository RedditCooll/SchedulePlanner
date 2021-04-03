package com.redditcooll.schedulePlanner.exception.handler

import com.redditcooll.schedulePlanner.dto.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.stream.Collectors

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        logger.error("400 Status Code", ex)
        val result = ex.bindingResult
        val error = result.allErrors.stream().map { e: ObjectError ->
            if (e is FieldError) {
                return@map e.field + " : " + e.getDefaultMessage()
            } else {
                return@map e.objectName + " : " + e.defaultMessage
            }
        }.collect(Collectors.joining(", "))
        return handleExceptionInternal(ex, ApiResponse(false, error), HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
}
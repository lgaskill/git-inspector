package com.lgaskill.git_inspector.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(HttpClientErrorException.NotFound::class)
    fun handleNotFoundExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex,
            "No account found with the requested username.",
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request,
        )!!
    }

    @ExceptionHandler(Exception::class)
    fun handleOtherExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.error("Unhandled exception occurred", ex)
        return handleExceptionInternal(
            ex,
            "Request failed unexpectedly.",
            HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request,
        )!!
    }
}
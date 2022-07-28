package com.axxiome.feign.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Basic Auth Credentials are not correct")
public class UserNotAuthorizedException extends RuntimeException {
}

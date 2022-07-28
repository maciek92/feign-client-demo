package com.axxiome.feign.demo.service;

import com.axxiome.feign.demo.exception.BadRequestException;
import com.axxiome.feign.demo.exception.UserNotAuthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                return new BadRequestException();
            case 401:
                return new UserNotAuthorizedException();
            default:
                return new Exception("Another Error");
        }
    }

}

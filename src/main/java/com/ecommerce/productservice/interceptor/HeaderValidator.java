package com.ecommerce.productservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;

import java.util.Collections;
import java.util.List;

public class HeaderValidator {

    public static void requestValidator(HttpServletRequest request,String apiKey, String apiSecret) throws BadRequestException {
        List<String> requestHeaders = Collections.list(request.getHeaderNames());
        if(!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            if (!requestHeaders.contains(apiKey)) {
                throw new BadRequestException("API Key is not passed");
            } else {
                if (!request.getHeader(apiKey).equals(apiSecret)) {
                    throw new BadRequestException("API Secret is Invalid");
                }
            }
        }
    }
}

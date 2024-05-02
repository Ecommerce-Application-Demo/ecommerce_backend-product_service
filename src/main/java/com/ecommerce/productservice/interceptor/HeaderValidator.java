package com.ecommerce.productservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeaderValidator {

    public static void requestValidator(HttpServletRequest request,String apiKey, String apiSecret) throws BadRequestException {
        AtomicBoolean flag = new AtomicBoolean(false);
        Collections.list(request.getHeaderNames()).forEach(s -> {
            if(s.equalsIgnoreCase(apiKey))
                flag.set(true);
        });
        if(!flag.get()){
            throw new BadRequestException("API Key is not passed");
        } else {
            if(!request.getHeader(apiKey).equals(apiSecret)){
                throw new BadRequestException("API Secret is Invalid");
            }
        }
    }
}

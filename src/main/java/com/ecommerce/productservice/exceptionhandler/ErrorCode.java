package com.ecommerce.productservice.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GENERAL_EXCEPTION(500,100,"Something went wrong"),

    API_KEY_NOT_PASSED(400,211,"API Key is not passed"),
    INVALID_API_SECRET(400,214,"API Secret is Invalid"),

    INVALID_EMAIL(400,134,"Given email is in invalid format."),
    EMAIL_NOT_BLANK(400,140,"Email can not be blank");

    private final int httpStatusCode;
    private final int errorCode;
    private final String errorMessage;
}

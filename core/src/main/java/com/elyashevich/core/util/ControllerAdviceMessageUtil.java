package com.elyashevich.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerAdviceMessageUtil {

    public static final String NOT_FOUND_MESSAGE = "Resource was not found.";
    public static final String ALREADY_EXISTS_MESSAGE = "Resource already exists.";
    public static final String NOT_SUPPORTED_MESSAGE = "Http method with this URL not found.";
    public static final String FAILED_VALIDATION_MESSAGE = "Validation failed.";
    public static final String UNEXPECTED_ERROR_MESSAGE = "Something went wrong.";
    public static final String PASSWORD_MISMATCH_MESSAGE = "Password mismatch.";
    public static final String TOKEN_INVALID_MESSAGE = "Invalid token.";
}

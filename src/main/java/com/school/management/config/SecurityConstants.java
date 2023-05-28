package com.school.management.config;

public interface SecurityConstants {

    String SECRET = "gjhghkewqjhrkvjqhrkjwhrw5rhkjrh";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String SIGN_UP_URL = "/users/sign-up";
    long TOKEN_EXPIRATION_TIME = 3_000_000;
    long REFRESH_TOKEN_EXPIRATION_TIME = 432_000_000;
}

package com.doth.app.ws.security;

import com.doth.app.ws.SpringApplicationContext;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    //public static final String TOKEN_SECRET = "juddh8476320fmcfnvdfb";

    public static String getTokenSecret(){
        AppProperty appProperty = (AppProperty) SpringApplicationContext.getBean("appProperty");
        return  appProperty.getTokenSecret();
    }
}

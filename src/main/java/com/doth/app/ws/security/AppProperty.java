package com.doth.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperty {
    @Autowired
    private Environment env;

    public String getTokenSecret(){
        return  env.getProperty("tokenSecret");
    }

}

package com.example.demo1.openFiegn;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

 @Component
public class FeignClientInterceptor implements RequestInterceptor {

    @SuppressWarnings("null")
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String role = request.getHeader("X-User-Role");
        String userId = request.getHeader("X-User-Id");

        if (role != null) template.header("X-User-Role", role);
        if (userId != null) template.header("X-User-Id", userId);
    }
} 
    


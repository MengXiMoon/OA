package com.oa.common;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Claims getCurrentClaims() {
        return (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentClaims().get("userId", Long.class);
    }

    public static String getCurrentUsername() {
        return getCurrentClaims().getSubject();
    }

    public static String getCurrentRole() {
        return getCurrentClaims().get("role", String.class);
    }
}

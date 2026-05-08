package com.krishanu.security;

public class RequestContext {

    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> role = new ThreadLocal<>();
    private static final ThreadLocal<String> username = new ThreadLocal<>();
    private static final ThreadLocal<String> secretKey = new ThreadLocal<>();

    public static void setUserId(String id) {
        userId.set(id);
    }

    public static String getUserId() {
        return userId.get();
    }

    public static void setRole(String r) {
        role.set(r);
    }

    public static String getRole() {
        return role.get();
    }

    public static void setUsername(String name) {
        username.set(name);
    }

    public static String getUsername() {
        return username.get();
    }

     public static void setSecretKey(String key) {
        secretKey.set(key);
    }

    public static String getSecretKey() {
        return secretKey.get();
    }

    public static void clear() {
        userId.remove();
        role.remove();
        username.remove();
        secretKey.remove();
    }
}
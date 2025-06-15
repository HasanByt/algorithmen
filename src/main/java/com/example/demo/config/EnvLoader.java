package com.example.demo.config;

public class EnvLoader {
    public static String getEnv(String key) {
        return System.getenv(key);
    }
}

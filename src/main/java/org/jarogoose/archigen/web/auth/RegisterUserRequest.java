package org.jarogoose.archigen.web.auth;

public record RegisterUserRequest(
        String username,
        String password,
        String role) {
}

package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class Credentials {

    @NonNull
    private final String username;

    @NonNull
    private final String token;

    public Credentials(@NonNull String username, @NonNull String token) {
        this.username = username;
        this.token = token;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getToken() {
        return token;
    }
}

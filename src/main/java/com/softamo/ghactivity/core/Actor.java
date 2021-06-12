package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class Actor {

    @NonNull
    @NotBlank
    private final String login;

    public Actor(@NonNull @NotBlank String login) {
        this.login = login;
    }

    @NonNull
    public String getLogin() {
        return login;
    }
}

package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class Issue {

    @NonNull
    @NotNull
    private final Integer number;

    @NonNull
    @NotBlank
    private final String title;

    public Issue(@NonNull @NotNull Integer number, @NonNull @NotBlank String title) {
        this.number = number;
        this.title = title;
    }

    @NonNull
    public Integer getNumber() {
        return number;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}

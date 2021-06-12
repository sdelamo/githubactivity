package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class Event {

    @NonNull
    @NotBlank
    private final String type;

    @NonNull
    @NotNull
    private final Actor actor;

    @NonNull
    @NotBlank
    private final String created_at;

    @NonNull
    @NotNull
    private final Payload payload;

    public Event(@NonNull @NotBlank String type,
                 @NonNull @NotNull Actor actor,
                 @NonNull @NotBlank String created_at,
                 @NonNull @NotNull Payload payload) {
        this.type = type;
        this.actor = actor;
        this.created_at = created_at;
        this.payload = payload;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public Actor getActor() {
        return actor;
    }

    @NonNull
    public String getCreated_at() {
        return created_at;
    }

    @NonNull
    public Payload getPayload() {
        return payload;
    }
}

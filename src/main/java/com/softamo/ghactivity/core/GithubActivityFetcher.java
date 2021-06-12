package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface GithubActivityFetcher {

    Set<DayActivity> fetchActivity(
            @NotNull @NotBlank String user,
            @NonNull @NotBlank String organization,
            @NonNull @NotBlank String type,
            @NotNull @NotNull Integer maxDays,
            @Nullable Credentials credentials);
}

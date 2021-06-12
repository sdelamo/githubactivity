package com.softamo.ghactivity.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

@Introspected
public class Payload {

    @Nullable
    private Issue issue;

    @JsonProperty("pull_request")
    @Nullable
    private PullRequest pullRequest;

    @Nullable
    public Issue getIssue() {
        return issue;
    }

    @Nullable
    public PullRequest getPullRequest() {
        return pullRequest;
    }
}

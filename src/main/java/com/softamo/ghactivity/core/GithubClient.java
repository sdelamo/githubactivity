package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;
import java.util.List;

@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")
@Client("https://api.github.com")
public interface GithubClient {

    @Get("/orgs/{organization}/repos")
    HttpResponse<List<Repo>> repositories(@NonNull @PathVariable String organization,
                            @NonNull @NotBlank @QueryValue String type,
                            @NonNull @NotNull @QueryValue Integer per_page,
                            @NonNull @NotNull @QueryValue Integer page,
                            @NonNull @Header(HttpHeaders.AUTHORIZATION) String authorization);

    @Get("/orgs/{organization}/repos")
    HttpResponse<List<Repo>> repositories(@NonNull @PathVariable String organization,
                                          @NonNull @NotBlank @QueryValue String type,
                                          @NonNull @NotNull @QueryValue Integer per_page,
                                          @NonNull @NotNull @QueryValue Integer page);

    @Get("/orgs/{organization}/repos")
    default HttpResponse<List<Repo>> repositories(@PathVariable String organization,
                                    @NonNull @NotBlank @QueryValue String type,
                                    @NonNull @NotNull @QueryValue Integer per_page,
                                    @NonNull @NotNull @QueryValue Integer page,
                                    @NonNull Credentials credentials) {
        return  repositories(organization, type, per_page, page, BasicAuthUtils.basicAuth(credentials.getUsername(), credentials.getToken()));
    }

    @Get("/networks/{owner}/{repo}/events")
    HttpResponse<List<Event>> events(@PathVariable String owner,
                       @PathVariable String repo,
                       @QueryValue Integer per_page,
                       @QueryValue Integer page);

    @Get("/networks/{owner}/{repo}/events")
    HttpResponse<List<Event>> events(@NonNull @PathVariable String owner,
                       @NonNull @PathVariable String repo,
                       @NonNull @QueryValue Integer per_page,
                       @NonNull @QueryValue Integer page,
                       @NonNull @Header(HttpHeaders.AUTHORIZATION) String authorization);

    @Get("/networks/{owner}/{repo}/events")
    default HttpResponse<List<Event>> events(@NonNull @PathVariable String owner,
                                             @NonNull @PathVariable String repo,
                                             @NonNull @QueryValue Integer per_page,
                                             @NonNull @QueryValue Integer page,
                                             @NonNull Credentials credentials) {
        return  events(owner, repo, per_page, page, BasicAuthUtils.basicAuth(credentials.getUsername(), credentials.getToken()));
    }
}

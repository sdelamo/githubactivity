package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class GithubActivityFetcherImpl implements GithubActivityFetcher {
    private static final Logger LOG = LoggerFactory.getLogger(GithubActivityFetcherImpl.class);
    private final static int PER_PAGE = 100;
    private final static int FIRST_PAGE = 1;
    private final static String REL_NEXT = ">; rel=\"next\"";
    private final static String PAGE_QUERY_VALUE = "&page=";

    private final GithubClient githubClient;

    public GithubActivityFetcherImpl(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public Set<DayActivity> fetchActivity(
            @NonNull @NotBlank String user,
            @NonNull @NotBlank String organization,
            @NonNull @NotBlank String type,
            @NonNull @NotNull Integer maxDays,
            @Nullable Credentials credentials) {
        Set<DayActivity> activities = new HashSet<>();
        Set<Repo> repos = new HashSet<>(parseRepositories(organization, type, FIRST_PAGE, credentials));
        for (Repo repo : repos) {
            if (LOG.isInfoEnabled()) {
                LOG.info("repo: {}", repo.getName());
            }
            activities.addAll(parseActivities(repo, organization, FIRST_PAGE, user, maxDays, credentials));
        }
        return activities;
    }

    private Set<Repo> parseRepositories(@NonNull String organization,
                                        @NonNull String type,
                                        @NonNull Integer page,
                                        @Nullable Credentials credentials) {
        Set<Repo> repos = new HashSet<>();
        HttpResponse<List<Repo>> repositoriesResponse = credentials != null ?
                githubClient.repositories(organization, type, PER_PAGE, page, credentials) :
                githubClient.repositories(organization, type, PER_PAGE, page);
        repositoriesResponse.getBody().ifPresent(repos::addAll);
        Optional<Integer> nextOptional = parseNext(repositoriesResponse);
        nextOptional.ifPresent(next -> repos.addAll(parseRepositories(organization, type, next, credentials)));
        return repos;
    }

    Set<DayActivity> parseActivities(@NonNull Repo repo,
                                     @NonNull String organization,
                                     @NonNull Integer page,
                                     @NonNull String user,
                                     @NonNull Integer maxDays,
                                     @Nullable Credentials credentials) {
        Set<DayActivity> activities = new HashSet<>();
        HttpResponse<List<Event>> responseEvents = credentials != null ?
                githubClient.events(organization, repo.getName(), PER_PAGE, page, credentials) :
                githubClient.events(organization, repo.getName(), PER_PAGE, page);


        Optional<List<Event>> eventsOptional = responseEvents.getBody();
        if (eventsOptional.isPresent()) {
            LocalDate pastDate = LocalDate.now().minusDays(maxDays);
            for (Event event : eventsOptional.get()) {
                LocalDate day = LocalDate.parse(event.getCreated_at().substring(0, "2021-06-11".length()));
                if (day.isBefore(pastDate)) {
                    return activities;
                }
                if (LOG.isTraceEnabled()) {
                    LOG.trace("{} {} {}/{}", event.getCreated_at(), event.getType(), organization, repo.getName());
                }
                Optional<DayActivity> optionalDayActivity = parseDayActivity(event, repo.getName(), user);
                optionalDayActivity.ifPresent(activities::add);
            }
            Optional<Integer> nextOptional = parseNext(responseEvents);
            nextOptional.ifPresent(next -> activities.addAll(parseActivities(repo, organization, next, user, maxDays, credentials)));
        }
        return activities;
    }

    private Optional<DayActivity> parseDayActivity(Event event, String repoName, String user) {
        if (!event.getActor().getLogin().equals(user)) {
            return Optional.empty();
        }
        LocalDate day = LocalDate.parse(event.getCreated_at().substring(0, "2021-06-11".length()));
        if (event.getPayload().getIssue() != null) {
            return Optional.of(new DayActivity(event.getType(), day, repoName, event.getPayload().getIssue().getNumber(), event.getPayload().getIssue().getTitle()));
        } else if (event.getPayload().getPullRequest() != null) {
            return Optional.of(new DayActivity(event.getType(), day, repoName, event.getPayload().getPullRequest().getNumber(), event.getPayload().getPullRequest().getTitle()));
        } else {
            return Optional.of(new DayActivity(event.getType(), day, repoName));
        }
    }

    private Optional<Integer> parseNext(HttpResponse<?> response) {
        Optional<String> linkHeaderOptional = response.getHeaders().get(HttpHeaders.LINK, String.class);
        if (linkHeaderOptional.isPresent()) {
            String linkHeader = linkHeaderOptional.get();
            if (linkHeader.contains(REL_NEXT)) {
                linkHeader = linkHeader.substring(0, linkHeader.indexOf(REL_NEXT));
                if (linkHeader.contains(PAGE_QUERY_VALUE)) {
                    try {
                        return Optional.of(Integer.parseInt(linkHeader.substring(linkHeader.indexOf(PAGE_QUERY_VALUE) + PAGE_QUERY_VALUE.length())));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        return Optional.empty();
    }
}

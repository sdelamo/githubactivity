package com.softamo.ghactivity.cli;

import com.softamo.ghactivity.core.Credentials;
import com.softamo.ghactivity.core.DayActivity;
import com.softamo.ghactivity.core.GithubActivityFetcher;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Command(name = "ghactivity", description = "...",
        mixinStandardHelpOptions = true)
public class GhActivityCommand implements Runnable {
    @Option(names = {"-t", "--token"}, description = "Github personal Token", required = true, interactive = true)
    String token;

    @Option(names = {"-o", "--org", "--organization"}, description = "Github organization", defaultValue = "micronaut-projects", required = true)
    String organization;

    @Option(names = {"-u", "--user"}, description = "Github's username", defaultValue = "sdelamo", required = true)
    String user;

    @Option(names = {"--type"}, description = "Github's repository type", defaultValue = "public", required = true)
    String type;

    @Option(names = {"-d", "--days"}, description = "number of days for which to fetch events", defaultValue = "7", required = true)
    Integer days;

    @Inject
    private GithubActivityFetcher githubActivityFetcher;

    public static void main(String[] args) {
        PicocliRunner.run(GhActivityCommand.class, args);
    }

    public void run() {
        Credentials credentials = user != null && token != null ? new Credentials(user, token) : null;
        List<DayActivity> l = new ArrayList<>(githubActivityFetcher.fetchActivity(user, organization, type, days, credentials));
        Collections.sort(l);
        Set<LocalDate> outputDays = new HashSet<>();
        Set<String> repos = new HashSet<>();
        for (DayActivity activity : l) {
            if (!outputDays.contains(activity.getDay())) {
                outputDays.add(activity.getDay());
                System.out.println("# " + activity.getDay());
                repos = new HashSet<>();
            }
            if (!repos.contains(activity.getRepo())) {
                repos.add(activity.getRepo());
                System.out.println("## " + activity.getRepo());
            }
            System.out.println(activity.getType() + " " + (activity.getNumber() != null ? activity.getNumber() : "") +
                    " " +
                            (activity.getTitle() != null ? activity.getTitle() : ""));
        }
    }



}

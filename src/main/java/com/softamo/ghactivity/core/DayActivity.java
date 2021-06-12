package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Introspected
public class DayActivity implements Comparable<DayActivity> {

    @NonNull
    @NotBlank
    private final String type;

    @NonNull
    @NotNull
    private final LocalDate day;

    @NonNull
    @NotBlank
    private final String repo;

    @Nullable
    private final Integer number;

    @Nullable
    private final String title;

    public DayActivity(@NonNull @NotBlank String type,
                       @NonNull @NotNull LocalDate day,
                       @NonNull @NotBlank String repo) {
        this(type, day, repo, null, null);
    }

    public DayActivity(@NonNull @NotBlank String type,
                       @NonNull @NotNull LocalDate day,
                       @NonNull @NotBlank String repo,
                       @Nullable Integer number,
                       @Nullable String title) {
        this.type = type;
        this.day = day;
        this.repo = repo;
        this.number = number;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayActivity activity = (DayActivity) o;

        if (!type.equals(activity.type)) return false;
        if (!day.equals(activity.day)) return false;
        if (!repo.equals(activity.repo)) return false;
        if (number != null ? !number.equals(activity.number) : activity.number != null) return false;
        return title != null ? title.equals(activity.title) : activity.title == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + repo.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public LocalDate getDay() {
        return day;
    }

    @NonNull
    public String getRepo() {
        return repo;
    }

    @Nullable
    public Integer getNumber() {
        return number;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(DayActivity o) {
        int compare = o.getDay().compareTo(day);
        if (compare != 0) {
            return compare;
        }
        compare = o.getRepo().compareTo(repo);
        if (compare != 0) {
            return compare;
        }

        if (o.getNumber() != null && number != null) {
            compare = o.getNumber().compareTo(number);
            if (compare != 0) {
                return compare;
            }
        }

        return o.getType().compareTo(type);
    }

    @Override
    public String toString() {
        String output = day + " " + type + " " + repo;
        if (number != null) {
            output += " #" + number;
        }
        if (title != null) {
            output += " " + title;
        }
        return output;
    }
}

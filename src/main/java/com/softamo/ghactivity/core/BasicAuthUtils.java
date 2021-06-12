package com.softamo.ghactivity.core;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpHeaderValues;
import java.util.Base64;

public final class BasicAuthUtils {

    @NonNull
    public static String basicAuth(@NonNull CharSequence username, @NonNull CharSequence password) {
        return HttpHeaderValues.AUTHORIZATION_PREFIX_BASIC + " " +
                new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
    }
}

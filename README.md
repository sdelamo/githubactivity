# Github Activity Command line

## How to get the Github Activity

```
./gradlew build
java -jar build/libs/githubactivity-0.1-all.jar 
Missing required option: '--token'
Usage: ghactivity [-hV] -t [-d=<days>] [-o=<organization>] [--type=<type>]
                  [-u=<user>]
...
  -d, --days=<days>   number of days for which to fetch events
  -h, --help          Show this help message and exit.
  -o, --org, --organization=<organization>
                      Github organization
  -t, --token         Github personal Token
      --type=<type>   Github's repository type
  -u, --user=<user>   Github's username
  -V, --version       Print version information and exit.
java -jar build/libs/githubactivity-0.1-all.jar --token=XXX
```

## Dependencies

- [Shadow Jar Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow) - A Gradle plugin for collapsing all dependencies and project code into a single Jar file.
- [Micronaut Application Gradle Plugin](https://plugins.gradle.org/plugin/io.micronaut.application)



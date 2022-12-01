package com.duckelekuuk.aoc.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@UtilityClass
public class InputFetcher {

    private static final String INPUT_URL = "https://adventofcode.com/%d/day/%d/input";
    private static final String CACHE_FILE = "input-day-%d.txt";

    // fetches the input for the given day and year
    public static String getInput(Path cachePath, boolean autoFetch, String session, int day, int year) throws IOException, InterruptedException {
        // Check if cached
        log.debug("Fetching input for day {} and year {}", day, year);
        log.debug("Checking if input is cached");
        if (isCached(cachePath, day)) {
            log.debug("Input is cached, reading from cache");
            return getFromCache(cachePath, day);
        }

        if (!autoFetch) {
            throw new IllegalStateException("Input not found and auto fetch turned off");
        }

        // Fetch input
        log.debug("Input is not cached, fetching from AoC");
        String url = String.format(INPUT_URL, year, day);

        if (session == null) {
            log.error("Session variable is not set");
            throw new IllegalStateException("Session variable is not set");
        }

        String input = fetchFromUrl(url, session);
        log.debug("Fetched input, caching");
        cacheInput(cachePath, day, input);
        return input;
    }

    private static void cacheInput(Path cachePath, int day, String input) throws IOException {
        Files.writeString(cachePath.resolve(String.format(CACHE_FILE, day)), input);
    }

    private static String getFromCache(Path cachePath, int day) throws IOException {
        return Files.readString(cachePath.resolve(String.format(CACHE_FILE, day)));
    }

    private static boolean isCached(Path cachePath, int day) {
        return cachePath.resolve(String.format(CACHE_FILE, day)).toFile().exists();
    }

    private static String fetchFromUrl(String url, String aocSession) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", "session=" + aocSession)
                .header("User-Agent", "AOC-Framework-Java (Duckelekuuk)")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Invalid request");
        }
        return response.body();
    }
}

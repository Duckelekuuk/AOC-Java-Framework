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
    private static final Path CACHE_FOLDER = Path.of("src/main/resources");

    // fetches the input for the given day and year
    public static List<String> getInput(String session, int day, int year) throws IOException, InterruptedException {
        // Check if cached
        log.debug("Fetching input for day {} and year {}", day, year);
        log.debug("Checking if input is cached");
        if (isCached(day)) {
            log.debug("Input is cached, reading from cache");
            return getFromCache(day);
        }

        // Fetch input
        log.debug("Input is not cached, fetching from AoC");
        String url = String.format(INPUT_URL, year, day);

        if (session == null) {
            log.error("AOC_SESSION environment variable is not set");
            throw new IllegalStateException("AOC_SESSION environment variable is not set");
        }

        List<String> input = fetchFromUrl(url, session);
        log.debug("Fetched input, caching");
        cacheInput(day, input);
        return input;
    }

    private static void cacheInput(int day, List<String> input) throws IOException {
        Files.write(CACHE_FOLDER.resolve(String.format(CACHE_FILE, day)), input);
    }

    private static List<String> getFromCache(int day) throws IOException {
        return Files.readAllLines(CACHE_FOLDER.resolve(String.format(CACHE_FILE, day)));
    }

    private static boolean isCached(int day) {
        return CACHE_FOLDER.resolve(String.format(CACHE_FILE, day)).toFile().exists();
    }

    private static List<String> fetchFromUrl(String url, String aocSession) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", "session=" + aocSession)
                .header("User-Agent", "AOC-Framework-Java (Duckelekuuk)")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Arrays.stream(response.body().split("\n")).collect(Collectors.toList());
    }
}

package com.duckelekuuk.aoc;

import com.duckelekuuk.aoc.annotations.AOCProject;
import com.duckelekuuk.aoc.cli.ArgumentOptions;
import com.duckelekuuk.aoc.scanner.ChallengeScanner;
import com.duckelekuuk.aoc.utils.InputFetcher;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Log4j2
public class AdventOfCode {

    private final Class<?> mainClass;
    @Setter
    private String session;
    @Setter
    private Integer day;
    @Setter
    private Integer year;
    @Setter
    private Path inputPath = Path.of("src/main/resources");
    @Setter
    private boolean autoFetch = true;

    public AdventOfCode(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Starts the advent of code puzzle solver
     *
     * @throws Exception any exception that happens during setup or challenge
     */
    public void start() throws Exception {
        AOCProject projectAnnotation = mainClass.getDeclaredAnnotation(AOCProject.class);
        if (projectAnnotation == null) {
            throw new IllegalArgumentException("No AOCProject annotation found on class " + mainClass.getName());
        }

        if (this.session == null) {
            this.session = System.getenv().get("AOC_SESSION");
        }

        if (this.session == null) {
            log.error("AOC_SESSION environment variable is not set");
            throw new IllegalStateException("AOC_SESSION environment variable is not set");
        }

        Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        if (projectAnnotation.year() != -1) {
            this.year = projectAnnotation.year();
        } else if (this.year == null) {
            this.year = instance.get(Calendar.YEAR);
        }

        if (this.day == null) {
            if (instance.get(Calendar.MONTH) == Calendar.DECEMBER) {
                this.day = instance.get(Calendar.DAY_OF_MONTH);
            } else {
                throw new IllegalArgumentException("Please specify a date");
            }
        }

        log.info("Starting Advent of Code {} Day {}", this.year, this.day);

        String input = InputFetcher.getInput(inputPath, autoFetch, this.session, day, year);

        // Scan for challenges
        ChallengeScanner challengeScanner = new ChallengeScanner(mainClass);
        challengeScanner.scan();

        AOCChallenge challenge = challengeScanner.constructChallenge(day);
        challenge.setInput(input);

        String resultPartOne = challenge.runPartOne();
        log.info("Part 1: {}", resultPartOne);

        if (challenge.hasPartTwo()) {
            challenge.resetInput();
            String resultPartTwo = challenge.runPartTwo();

            log.info("Part 2: {}", resultPartTwo);
        }
    }

    public static void start(Class<?> mainClass, String[] args) throws Exception {
        ArgumentOptions argumentOptions = new ArgumentOptions(args);
        AdventOfCode adventOfCode = new AdventOfCode(mainClass);

        // If no day is specified, use the current day
        if (argumentOptions.containsKey("--day")) {
            adventOfCode.setDay(Integer.parseInt(argumentOptions.get("--day")));
        }

        if (argumentOptions.containsKey("--session")) {
            adventOfCode.setSession(argumentOptions.get("--session"));
        }


        adventOfCode.start();
    }
}

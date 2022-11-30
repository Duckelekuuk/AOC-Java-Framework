package com.duckelekuuk.aoc.scanner;

import com.duckelekuuk.aoc.AOCChallenge;
import com.duckelekuuk.aoc.annotations.AOCDay;
import com.duckelekuuk.aoc.annotations.AOCInput;
import com.duckelekuuk.aoc.annotations.AOCPartOne;
import com.duckelekuuk.aoc.annotations.AOCPartTwo;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ChallengeScanner {

    private final Class<?> startClass;
    private final Reflections reflections;
    private final Map<Integer, Class<?>> foundChallenges;

    public ChallengeScanner(Class<?> startClass) {
        this.startClass = startClass;
        this.foundChallenges = new HashMap<>();
        this.reflections = new Reflections(startClass);
    }

    public void scan() {
        reflections.getTypesAnnotatedWith(AOCDay.class).forEach(day -> {
            AOCDay annotation = day.getAnnotation(AOCDay.class);
            if (annotation != null) {
                foundChallenges.put(annotation.day(), day);
            }
        });
    }

    public AOCChallenge constructChallenge(int day) {
        Class<?> challengeClass = foundChallenges.get(day);
        if (challengeClass == null) {
            throw new IllegalArgumentException("No challenge found for day " + day);
        }

        Field inputField = null;
        Method partOneMethod = null;
        Method partTwoMethod = null;

        Constructor<?> constructor = challengeClass.getConstructors()[0];
        try {
            Object instance = constructor.newInstance();
            for (Field declaredField : instance.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(AOCInput.class) == null) continue;
                declaredField.setAccessible(true);
                inputField = declaredField;
            }

            for (Method declaredMethod : instance.getClass().getDeclaredMethods()) {
                if (declaredMethod.getDeclaredAnnotation(AOCPartOne.class) != null) {
                    partOneMethod = declaredMethod;
                } else if (declaredMethod.getDeclaredAnnotation(AOCPartTwo.class) != null) {
                    partTwoMethod = declaredMethod;
                }
            }

            if (partOneMethod == null) {
                throw new IllegalStateException("Part one is required");
            }

            return new AOCChallenge(instance, partOneMethod, partTwoMethod, inputField);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Failed to construct challenge for day " + day, e);
        }
    }
}

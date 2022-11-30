package com.duckelekuuk.aoc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class AOCChallenge {

    private final Object instance;
    private final Method partOne;
    private final Method partTwo;
    private final Field inputField;
    private List<String> input;

    public AOCChallenge(Object instance, Method partOne, Method partTwo, Field inputField) {
        this.instance = instance;
        this.partOne = partOne;
        this.partTwo = partTwo;
        this.inputField = inputField;

    }
    /**
     * Executes part one
     *
     * @return Result of part one
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String runPartOne() throws InvocationTargetException, IllegalAccessException {
        return invoke(partOne);
    }

    /**
     * Executes part two
     *
     * @return Result of part two
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String runPartTwo() throws InvocationTargetException, IllegalAccessException {
        return invoke(partTwo);
    }

    private String invoke(Method method) throws InvocationTargetException, IllegalAccessException {
        return (String) method.invoke(instance);
    }

    public void setInput(List<String> input) throws IllegalAccessException {
        this.input = input;
        resetInput();
    }

    public void resetInput() throws IllegalAccessException {
        if (inputField == null) {
            throw new IllegalStateException("No input field found");
        }
        inputField.set(instance, new ArrayList<>(input));
    }

    public boolean hasPartTwo() {
        return partTwo != null;
    }
}

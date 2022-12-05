package com.duckelekuuk.aoc;

import com.duckelekuuk.aoc.mapper.InputMapper;
import com.duckelekuuk.aoc.mapper.types.AOCMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class AOCChallenge {

    private static final InputMapper INPUT_MAPPER = new InputMapper();
    private final Object instance;
    private final Method partOne;
    private final Method partTwo;
    private final Field inputField;
    private String input;

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
        return String.valueOf(method.invoke(instance));
    }

    public void setInput(String input) throws IllegalAccessException {
        this.input = input;
        resetInput();
    }

    public void resetInput() throws IllegalAccessException {
        if (inputField == null) {
            throw new IllegalStateException("No input field found");
        }
        AOCMapper<?> mappingForType = INPUT_MAPPER.getMappingForType(inputField.getType());
        inputField.set(instance, mappingForType.map(input));
    }

    public boolean hasPartTwo() {
        return partTwo != null;
    }
}

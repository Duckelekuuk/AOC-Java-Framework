package com.duckelekuuk.aoc;

import com.duckelekuuk.aoc.annotations.AOCPartOne;
import com.duckelekuuk.aoc.annotations.AOCPartTwo;
import com.duckelekuuk.aoc.annotations.AOCSample;
import com.duckelekuuk.aoc.mapper.InputMapper;
import com.duckelekuuk.aoc.mapper.types.AOCMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
        AOCPartOne partAnnotation = partOne.getDeclaredAnnotation(AOCPartOne.class);
        return invoke(partOne, partAnnotation.useSample());
    }

    /**
     * Executes part two
     *
     * @return Result of part two
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String runPartTwo() throws InvocationTargetException, IllegalAccessException {
        AOCPartTwo partAnnotation = partTwo.getDeclaredAnnotation(AOCPartTwo.class);
        return invoke(partTwo, partAnnotation.useSample());
    }

    private String invoke(Method method, boolean useSample) throws InvocationTargetException, IllegalAccessException {

        String oldInput = this.input;
        if (useSample) {
            AOCSample sampleAnnotation = method.getDeclaredAnnotation(AOCSample.class);
            setInput(sampleAnnotation.value());
        }

        String result = String.valueOf(method.invoke(instance));

        if (useSample) {
            this.input = oldInput;
        }

        return result;
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

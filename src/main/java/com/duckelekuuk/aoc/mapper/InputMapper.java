package com.duckelekuuk.aoc.mapper;

import com.duckelekuuk.aoc.mapper.types.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputMapper {

    private static final Map<Class<?>, AOCMapper<?>> mappings = Map.of(
            String.class, new StringInput(),
            List.class, new ListInput(),
            Scanner.class, new ScannerInput(),
            Stream.class, new StreamInput()
    );

    public AOCMapper<?> getMappingForType(Class<?> type) {
        AOCMapper<?> aocMapper = mappings.get(type);
        if (aocMapper == null) {
            throw new IllegalArgumentException("Unable to find mapper with type: " + type.getSimpleName());
        }

        return aocMapper;
    }
}

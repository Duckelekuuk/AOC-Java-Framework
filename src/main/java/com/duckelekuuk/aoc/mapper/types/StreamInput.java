package com.duckelekuuk.aoc.mapper.types;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamInput implements AOCMapper<Stream<String>> {

    @Override
    public Stream<String> map(String input) {
        return Arrays.stream(input.split("\n")).map(String::strip);
    }
}

package com.duckelekuuk.aoc.mapper.types;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListInput implements AOCMapper<List<String>> {

    @Override
    public List<String> map(String input) {
        return Arrays.stream(input.split("\n")).map(String::strip).collect(Collectors.toList());
    }
}

package com.duckelekuuk.aoc.cli;

import java.util.HashMap;
import java.util.Map;

public class ArgumentOptions {

    private final Map<String, String> argsMap = new HashMap<>();

    public ArgumentOptions(String[] args) {
        for (String arg : args) {
            String[] parts = arg.split("=");
            argsMap.put(parts[0], parts[1]);
        }
    }

    public String get(String key) {
        return argsMap.get(key);
    }

    public boolean containsKey(String key) {
        return argsMap.containsKey(key);
    }
}

package com.duckelekuuk.aoc.mapper.types;

import java.util.Scanner;

public class ScannerInput implements AOCMapper<Scanner> {

    @Override
    public Scanner map(String input) {
        return new Scanner(input);
    }
}

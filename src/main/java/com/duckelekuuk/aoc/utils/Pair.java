package com.duckelekuuk.aoc.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
public class Pair<A,B> {

    private A a;
    private B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public boolean areTheSame() {
        return a.equals(b);
    }

    public Map.Entry<A, B> toEntry() {
        return Map.entry(a, b);
    }
}

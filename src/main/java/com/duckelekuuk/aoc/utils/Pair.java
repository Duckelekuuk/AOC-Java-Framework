package com.duckelekuuk.aoc.utils;

import lombok.Data;

import java.util.Map;

@Data
public class Pair<A,B> {

    private A left;
    private B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public boolean areTheSame() {
        return left.equals(right);
    }

    public Map.Entry<A, B> toEntry() {
        return Map.entry(left, right);
    }
}

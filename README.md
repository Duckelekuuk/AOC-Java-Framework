# AOC-Java-Framework
[![](https://jitpack.io/v/Duckelekuuk/AOC-Java-Framework.svg)](https://jitpack.io/#Duckelekuuk/AOC-Java-Framework)  
Java framework for running Advent of code challenges

## Usage

A quick explanation of how to use the framework, so you can get started with coding your puzzles.
The example is a working version of Year 2021 day 1 challenge.

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml

<dependency>
    <groupId>com.github.Duckelekuuk</groupId>
    <artifactId>AOC-2022</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Setup

❗ **important** ❗  
Set your session token. If you use want to auto fetch you have 3 options. Copy your session from your cookie on AOC.

#### Environment variable
```
AOC_SESSION={YOUR_SESSION}
```

#### Command Argument
```
--session={YOUR_SESSION}
```

#### Inline
```java
AdventOfCode adventOfCode=new AdventOfCode(Main.class);
adventOfCode.setSession("{YOUR SESSION}");
adventOfCode.start();
```

### Main class

The main class is set up like you do with Spring Boot.

```java
@AOCProject(year = 2022)
public class Main {

    public static void main(String[] args) throws Exception {
        AdventOfCode.start(Main.class, args);
    }
}
```
### Puzzle Classes
To setup a puzzle. Annotate the class with ``@AOCDay(day = 2)`` for day 2.  
To setup the input. Annotate ``List<String>`` with ``@AOCInput``.  
Annotate your challenges with ``@AOCPartOne`` and ``@AOCPartOne``.  

**Note**: Only part one is required
```java
package com.duckelekuuk;

import com.duckelekuuk.framework.annotations.AOCDay;
import com.duckelekuuk.framework.annotations.AOCInput;
import com.duckelekuuk.framework.annotations.AOCPartOne;
import com.duckelekuuk.framework.annotations.AOCPartTwo;

import java.util.List;

@AOCDay(day = 1)
public class DayOne {

    @AOCInput
    private List<String> input;

    @AOCPartOne
    public String getPart1() {
        return "Answer part 1";
    }

    @AOCPartTwo
    public String getPart2() {
        return "Answer part 2";
    }
}
```
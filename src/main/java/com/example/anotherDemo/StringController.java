package com.example.anotherDemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RestController
public class StringController {
    String[] wordArray = generateRandomArray(1000, 1000);
    List<String> stringList = generateRandomStringList(wordArray, 500, 500, 100000);
    long startTime;
    long endTime;
    long beforeUsedMem;
    long afterUsedMem;
    Set<String> resultSet = new HashSet<>();

    BiFunction<String, String, String> replaceFunc = (input, replacement) -> input.replaceAll(replacement, "_");

    Runnable rn = () ->
    {
        resultSet = stringList.parallelStream()
                    .map(url -> Arrays.stream(wordArray).parallel()
                        .reduce(url,
                                    (input, replacement) -> input.replaceAll(replacement, "_")))
                    .collect(Collectors.toSet());

    };

    public void initializer() {
        wordArray = generateRandomArray(1000, 1000);
        stringList = generateRandomStringList(wordArray, 500, 500, 100000);
    }

    @GetMapping("/withoutThread")
    public ResponseEntity<String> funcWithoutThreads() {

        System.gc();
        beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        startTime = System.nanoTime();
        Set<String> anotherResultSet = stringList.stream()
                .map(url -> Arrays.stream(wordArray)
                        .reduce(url, replaceFunc::apply))
                .collect(Collectors.toSet());
        endTime = System.nanoTime();

        System.gc();
        afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("\nMEMORY USED IN FUNCTION WITHOUT THREADS : " + (afterUsedMem - beforeUsedMem));

        System.out.println("EXECUTION TIME WITHOUT THREADS : " + (endTime - startTime) / 1000000 + "ms");

        return ResponseEntity.ok("SUCCESS WITH FUNCTION WITHOUT THREADS");
    }

    @GetMapping("/withoutThread2")
    public ResponseEntity<String> funcWithoutThreads2() {

        System.gc();
        beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        startTime = System.nanoTime();
        Set<String> anotherResultSet = stringList.parallelStream()
                .map(url -> Arrays.stream(wordArray).parallel()
                        .reduce(url, replaceFunc::apply))
                .collect(Collectors.toSet());
        endTime = System.nanoTime();

        System.gc();
        afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("\nMEMORY USED IN FUNCTION WITHOUT THREADS : " + (afterUsedMem - beforeUsedMem));

        System.out.println("EXECUTION TIME WITHOUT THREADS : " + (endTime - startTime) / 1000000 + "ms");

        return ResponseEntity.ok("SUCCESS WITH FUNCTION WITHOUT THREADS");
    }

    @GetMapping("/platform")
    public ResponseEntity<String> funcWithPlatformThreads() {

        System.gc();
        beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        startTime = System.nanoTime();
        Thread.ofPlatform().start(rn);
        endTime = System.nanoTime();

        System.gc();
        afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("\nMEMORY USED IN FUNCTION WITH PLATFORM THREADS : " + (afterUsedMem - beforeUsedMem));

        System.out.println("EXECUTION TIME WITH PLATFORM THREADS : " + (endTime - startTime) / 1000000 + "ms");

        return ResponseEntity.ok("SUCCESS WITH FUNCTION WITH PLATFORM THREADS");
    }

    @GetMapping("/virtual")
    public ResponseEntity<String> funcWithVirtualThreads() {

        System.gc();
        beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        startTime = System.nanoTime();
        Thread.ofVirtual().start(rn);
        endTime = System.nanoTime();

        System.gc();
        afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("\nMEMORY USED IN FUNCTION WITH VIRTUAL THREADS : " + (afterUsedMem - beforeUsedMem));

        System.out.println("EXECUTION TIME WITH VIRTUAL THREADS : " + (endTime - startTime) / 1000000 + "ms");

        return ResponseEntity.ok("SUCCESS WITH FUNCTION WITH VIRTUAL THREADS");

    }

    @GetMapping("getAllFuncResults")
    public ResponseEntity<String> func() {

        System.out.println("FIRST VIRTUAL THEN PLATFORM : ");
        funcWithVirtualThreads();
        funcWithPlatformThreads();

        System.out.println();

        System.out.println("FIRST PLATFORM THEN VIRTUAL : ");
        funcWithPlatformThreads();
        funcWithVirtualThreads();

        return ResponseEntity.ok("SUCCESS WITH ALL FUNC RESULTS");

    }

    public void printResultSet(Set<String> resultSet) {
        resultSet.forEach(System.out::println);
    }

    private static String[] generateRandomArray(int minLength, int maxLength) {
        Random random = new Random();
        int arrayLength = random.nextInt(maxLength - minLength + 1) + minLength;

        String[] wordArray = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            // Generating random words with length between 3 and 10 characters
            int wordLength = random.nextInt(8) + 3;
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < wordLength; j++) {
                char randomChar = (char) (random.nextInt(26) + 'a');
                word.append(randomChar);
            }
            wordArray[i] = "<" + word.toString() + ">";
        }

        return wordArray;
    }

    private static List<String> generateRandomStringList(String[] wordArray, int minLength, int maxLength, int listSize) {
        List<String> stringList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < listSize; i++) {
            int stringLength = random.nextInt(maxLength - minLength + 1) + minLength;
            StringBuilder randomString = new StringBuilder();

            for (int j = 0; j < stringLength; j++) {
                if (random.nextBoolean() && wordArray.length > 0) {
                    // Add a random word from the array
                    randomString.append(wordArray[random.nextInt(wordArray.length)]);
                } else {
                    // Add a random word with length between 3 and 10 characters
                    int wordLength = random.nextInt(8) + 3;
                    StringBuilder word = new StringBuilder();
                    for (int k = 0; k < wordLength; k++) {
                        char randomChar = (char) (random.nextInt(26) + 'a');
                        word.append(randomChar);
                    }
                    randomString.append(word.toString());
                }

                if (j < stringLength - 1) {
                    randomString.append(" ");
                }
            }

            stringList.add(randomString.toString());
        }

        return stringList;
    }
}

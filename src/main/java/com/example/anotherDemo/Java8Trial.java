package com.example.anotherDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Java8Trial {

    public interface perform {
        int mul(int ... args);
    }

    public static void main(String[] arg) {

        List<Integer> myList = new ArrayList<Integer>();
        for(int i=0; i<10; i++) myList.add(i);

        //FUNCTIONAL INTERFACE EXAMPLE
//        perform p = (int ... args) -> {
//            int ret = 1;
//            ret = Arrays.stream(args).reduce(ret,(curr,num) -> curr*num);
//            return ret;
//        };
//        System.out.println(p.mul(2,3,5,6,7,8));


        //FOR EACH EXAMPLE
//        Consumer<Integer> c = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//                System.out.println(integer + integer);
//            }
//        };
//        myList.forEach(c);

        //FILTER EXAMPLE
//        List<Integer> res = myList.stream().filter((num) -> num>=5).toList();
//        res.forEach(System.out::println);

        //FIBONACCI USING CONSUMER / BI FUNCTION
//        Consumer<Integer> fib = (Integer n) -> {
//            int a=0,b=1;
//            while(n-->0) {
//                System.out.println(a);
//                int c = a + b;
//                a = b;
//                b = c;
//            }
//        };
//        fib.accept(10);

        //PREDICATE EXAMPLE
//        List<String> names = Arrays.asList(
//                "Geek", "GeeksQuiz", "g1", "QA", "Geek2");
//        Predicate<String> p = (s) -> s.startsWith("G");
//        names.stream().filter(p);

        //CONVERSION FROM LIST TO MAP USING METHOD REFERENCE AND STREAM
//        ArrayList<String> fruits = new ArrayList<>();
//        fruits.add("Banana");
//        fruits.add("Guava");
//        fruits.add("Pineapple");
//        fruits.add("Apple");
//
//        HashMap<String, Integer> res = fruits.stream().collect(Collectors.
//                toMap(Function.identity(), String::length,(e1, e2) -> e1, HashMap::new));
//
//        res.forEach((key,val) -> System.out.println(key + " : " + val));

    }



}

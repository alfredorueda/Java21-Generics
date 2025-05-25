package com.ocp.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 * Demonstrates the PECS principle (Producer Extends, Consumer Super) in Java Generics.
 * 
 * PECS is a mnemonic that helps remember when to use which type of wildcard:
 * - Use "extends" (? extends T) when you only GET values from a structure (Producer)
 * - Use "super" (? super T) when you only PUT values into a structure (Consumer)
 * - Use exact type (T) when you both GET and PUT values
 * 
 * Key points for OCP Java 21:
 * - Understanding PECS is crucial for designing flexible and type-safe APIs
 * - PECS helps avoid unnecessary restrictions while maintaining type safety
 * - PECS aligns with the Get and Put Principle (in/out principle)
 */
public class Main_PecsRule {
    
    public static void main(String[] args) {
        System.out.println("=== PECS Rule (Producer Extends, Consumer Super) Examples ===\n");
        
        // Create sample lists
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        
        // 1. PRODUCER EXTENDS example (copying from source)
        System.out.println("1. PRODUCER EXTENDS example:");
        System.out.println("Original integers: " + integers);
        System.out.println("Original doubles: " + doubles);
        
        // We use ? extends Number because the source PRODUCES values for us to read
        List<Number> numberTarget = new ArrayList<>();
        copyFromSource(integers, numberTarget); // Integers are Numbers
        copyFromSource(doubles, numberTarget);  // Doubles are Numbers
        System.out.println("Target after copying from producers: " + numberTarget);
        
        // 2. CONSUMER SUPER example (copying to destination)
        System.out.println("\n2. CONSUMER SUPER example:");
        
        // We use ? super Integer because the destination CONSUMES Integer values
        copyToDestination(integers, numbers);  // Numbers can hold Integers
        copyToDestination(integers, objects);  // Objects can hold Integers
        // copyToDestination(integers, doubles); // Error: List<Double> is not a List<? super Integer>
        
        System.out.println("Numbers after consuming integers: " + numbers);
        System.out.println("Objects after consuming integers: " + objects);
        
        // 3. Combined PECS example (maximum flexibility)
        System.out.println("\n3. Combined PECS example:");
        
        // Copy from any number list to any list that can hold Numbers
        List<Number> results = new ArrayList<>();
        copyNumbers(integers, results); // Copy from Integer to Number
        copyNumbers(doubles, results);  // Copy from Double to Number
        System.out.println("Results after flexible copying: " + results);
        
        // 4. Real-world example: Comparator
        System.out.println("\n4. Real-world example: Comparator with PECS:");
        
        // Create a list of persons
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        
        System.out.println("People before sorting: " + people);
        
        // Sort using a Comparator<? super Person>
        // The comparator CONSUMES Person objects, so we use super
        Collections.sort(people, new AgeComparator());
        
        System.out.println("People after sorting: " + people);
        
        // Educational explanation
        System.out.println("\n*** WHY PECS MATTERS ***");
        System.out.println("1. Maximum flexibility: APIs can work with more types");
        System.out.println("2. Type safety: Prevents operations that could cause runtime errors");
        System.out.println("3. Clarity: Communicates intent (read-only vs. write-only vs. both)");
        System.out.println("4. Java Collections Framework: Uses PECS extensively");
        
        // Common mistakes
        System.out.println("\n*** COMMON MISTAKES WITH PECS ***");
        System.out.println("1. Using extends when super is needed (trying to add to a producer)");
        System.out.println("2. Using super when extends is needed (trying to get specific types from a consumer)");
        System.out.println("3. Using exact types when wildcards would provide more flexibility");
        System.out.println("4. Forgetting that List<? extends T> cannot add elements of type T");
    }
    
    /**
     * PRODUCER EXTENDS example:
     * The source list PRODUCES values that we read, so we use ? extends Number.
     * This allows us to read from any list of Number subtypes.
     * 
     * @param source A list of Number or any subtype of Number
     * @param destination A list of Number
     */
    public static void copyFromSource(List<? extends Number> source, List<Number> destination) {
        for (Number number : source) {
            destination.add(number);
        }
        // source.add(Integer.valueOf(1)); // Error! Cannot add to a producer (? extends)
    }
    
    /**
     * CONSUMER SUPER example:
     * The destination list CONSUMES values that we write, so we use ? super Integer.
     * This allows us to write Integers to any list that can hold Integers.
     * 
     * @param source A list of Integer
     * @param destination A list of Integer or any supertype of Integer
     */
    public static void copyToDestination(List<Integer> source, List<? super Integer> destination) {
        for (Integer number : source) {
            destination.add(number);
        }
        // Integer first = destination.get(0); // Error! Cannot read specific types from consumer (? super)
    }
    
    /**
     * Combined PECS example:
     * - Source PRODUCES values (? extends Number)
     * - Destination CONSUMES values (? super Number)
     * 
     * This method demonstrates maximum flexibility.
     * 
     * @param source A list of Number or any subtype of Number
     * @param destination A list of Number or any supertype of Number
     */
    public static void copyNumbers(List<? extends Number> source, List<? super Number> destination) {
        for (Number number : source) {
            destination.add(number);
        }
    }
    
    /**
     * Simple Person class for the Comparator example.
     */
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    /**
     * A Comparator that compares Person objects by age.
     * Note that it's declared as Comparator<Person>, but in methods like
     * Collections.sort(), it's used as Comparator<? super Person>.
     */
    static class AgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return Integer.compare(p1.getAge(), p2.getAge());
        }
    }
}

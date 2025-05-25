package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the PECS rule (Producer Extends, Consumer Super) in Java Generics.
 * 
 * The PECS rule, also known as the "Get and Put Principle", states:
 * - Use "extends" (? extends T) when you only GET values (Producer)
 * - Use "super" (? super T) when you only PUT values (Consumer)
 * - Use exact type (T) when you both GET and PUT values
 * 
 * This rule helps create more flexible and type-safe APIs.
 */
public class Main_PecsRule {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating PECS Rule ===");
        
        // Sample lists
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        
        // PRODUCER example: copy from a source (producer)
        System.out.println("\n1. PRODUCER EXTENDS example:");
        System.out.println("Original integers: " + integers);
        System.out.println("Original doubles: " + doubles);
        
        // We use ? extends Number because the source PRODUCES values for us to read
        List<Number> targetNumbers = new ArrayList<>();
        copyFromSource(integers, targetNumbers); // Source produces Integers
        copyFromSource(doubles, targetNumbers);  // Source produces Doubles
        System.out.println("Target numbers after copying: " + targetNumbers);
        
        // CONSUMER example: copy to a destination (consumer)
        System.out.println("\n2. CONSUMER SUPER example:");
        
        // We use ? super Integer because the destination CONSUMES Integer values
        copyToDestination(integers, numbers);  // Destination consumes Numbers
        copyToDestination(integers, objects);  // Destination consumes Objects
        // copyToDestination(integers, doubles); // Compile error! Double is not a supertype of Integer
        
        System.out.println("Numbers after copying integers: " + numbers);
        System.out.println("Objects after copying integers: " + objects);
        
        // Combined example: Using PECS for maximum flexibility
        System.out.println("\n3. Combined PECS example:");
        
        // Copy from any number list to any list that can hold Numbers
        List<Number> results = new ArrayList<>();
        copyNumbers(integers, results);
        copyNumbers(doubles, results);
        System.out.println("Results after copying: " + results);
        
        // Practical example: Sorting with comparator
        System.out.println("\n4. Practical PECS with Comparator example:");
        
        // Setup a list of persons
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        
        System.out.println("Before sorting: " + people);
        
        // Sort by age using a Comparator<? super Person>
        // The comparator CONSUMES Person objects, so we use super
        sortByAge(people);
        
        System.out.println("After sorting: " + people);
        
        // Educational explanation
        System.out.println("\n*** WHY PECS IS IMPORTANT ***");
        System.out.println("1. Flexibility: APIs can work with more types");
        System.out.println("2. Type Safety: Prevents operations that could cause runtime errors");
        System.out.println("3. Clarity: Communicates intent (read-only vs. write-only vs. both)");
        System.out.println("4. Efficiency: Allows methods to be reused for multiple type hierarchies");
    }
    
    /**
     * PRODUCER EXTENDS example:
     * The source list PRODUCES values that we read, so we use ? extends Number.
     * This allows us to read from any list of Number subtypes.
     */
    public static void copyFromSource(List<? extends Number> source, List<Number> destination) {
        for (Number number : source) {
            destination.add(number);
        }
        // source.add(1); // Compile error! Can't add to a producer (? extends)
    }
    
    /**
     * CONSUMER SUPER example:
     * The destination list CONSUMES values that we write, so we use ? super Integer.
     * This allows us to write Integers to any list that can hold Integers.
     */
    public static void copyToDestination(List<Integer> source, List<? super Integer> destination) {
        for (Integer number : source) {
            destination.add(number);
        }
        // Integer first = destination.get(0); // Compile error! Can't read specific types from a consumer (? super)
    }
    
    /**
     * Combined PECS example:
     * - Source PRODUCES values (? extends Number)
     * - Destination CONSUMES values (? super Number)
     * 
     * This method can copy from any list of Numbers or Number subtypes
     * to any list that can hold Numbers or Number supertypes.
     */
    public static void copyNumbers(List<? extends Number> source, List<? super Number> destination) {
        for (Number number : source) {
            destination.add(number);
        }
    }
    
    /**
     * Practical example using PECS with Comparator.
     * The comparator CONSUMES Person objects to compare them, so we use ? super Person.
     */
    public static void sortByAge(List<Person> people) {
        // The comparator consumes Person objects, so we use ? super Person
        people.sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
    }
    
    /**
     * Simple Person class for demonstration.
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
}

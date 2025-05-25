package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the PECS rule (Producer Extends, Consumer Super) in Java Generics for OCP Java 21 preparation.
 * 
 * The PECS rule, coined by Joshua Bloch and formalized in Effective Java, is a fundamental 
 * principle for designing flexible, type-safe generics APIs in Java. Also known as the 
 * "Get and Put Principle" or "Wildcards Rule", it states:
 * 
 * - Use "extends" (? extends T) when you only GET values (Producer)
 *   → The collection PRODUCES values that your code READS
 * - Use "super" (? super T) when you only PUT values (Consumer)
 *   → The collection CONSUMES values that your code WRITES
 * - Use exact type (T) when you both GET and PUT values with the same type
 * 
 * MENTAL MODEL:
 * Think of data flow direction - where are values flowing?
 * - From collection to your code? Use extends (Producer Extends)
 * - From your code to collection? Use super (Consumer Super)
 * 
 * REAL-WORLD ANALOGY:
 * Consider a water supply system:
 * - Water sources (producers) extend from a basic water source interface (? extends WaterSource)
 *   → You take water OUT from sources (reading)
 * - Water tanks (consumers) can be any container that can hold water (? super Water)
 *   → You put water IN to tanks (writing)
 * 
 * OCP EXAM TIP:
 * The exam often tests understanding of PECS in various contexts:
 * - Method parameters with wildcards
 * - Collection copying operations
 * - Understanding variance in generics
 * - API design implications
 * 
 * This rule directly addresses the invariance of Java generics (e.g., List<Integer> is not a subtype of List<Number>),
 * allowing your code to work with multiple related types safely.
 */
public class Main_PecsRule {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating PECS Rule ===");
        
        // Sample lists of different but related types
        // This hierarchy will help demonstrate both "extends" and "super" wildcards
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        
        // PRODUCER example: copy from a source (producer)
        System.out.println("\n1. PRODUCER EXTENDS example:");
        System.out.println("Original integers: " + integers);
        System.out.println("Original doubles: " + doubles);
        
        // We use ? extends Number because the source PRODUCES values for us to read
        // This follows the "Producer Extends" part of PECS
        List<Number> targetNumbers = new ArrayList<>();
        copyFromSource(integers, targetNumbers); // Source produces Integers (a subtype of Number)
        copyFromSource(doubles, targetNumbers);  // Source produces Doubles (a subtype of Number)
        System.out.println("Target numbers after copying: " + targetNumbers);
        
        // CONSUMER example: copy to a destination (consumer)
        System.out.println("\n2. CONSUMER SUPER example:");
        
        // We use ? super Integer because the destination CONSUMES Integer values
        // This follows the "Consumer Super" part of PECS
        copyToDestination(integers, numbers);  // Destination consumes Numbers (a supertype of Integer)
        copyToDestination(integers, objects);  // Destination consumes Objects (a supertype of Integer)
        // The commented line below would cause a compile error:
        // copyToDestination(integers, doubles); // Compile error! Double is not a supertype of Integer
        
        System.out.println("Numbers after copying integers: " + numbers);
        System.out.println("Objects after copying integers: " + objects);
        
        // Combined example: Using PECS for maximum flexibility
        System.out.println("\n3. Combined PECS example:");
        
        // Copy from any number list to any list that can hold Numbers
        // This shows how to apply both principles together for maximum flexibility
        List<Number> results = new ArrayList<>();
        copyNumbers(integers, results); // From List<Integer> to List<Number>
        copyNumbers(doubles, results);  // From List<Double> to List<Number>
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
        // This is why in the Java Collections API, methods like sort() use Comparator<? super T>
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
     * 
     * PECS PRINCIPLE: "Producer Extends"
     * - We are only READING from source (getting values)
     * - Source is a PRODUCER of Number values
     * - So we use ? extends Number
     * 
     * BENEFITS:
     * - Flexibility: Can accept any List of Number subtypes (Integer, Double, etc.)
     * - Type Safety: Guarantees we only get Number objects or its subtypes
     * 
     * LIMITATIONS:
     * - Cannot add elements to source (compile-time safety mechanism)
     * 
     * OCP EXAM TIP:
     * The exam might test if you understand why you can't add to a List<? extends T>.
     * The reason is that the compiler cannot guarantee type safety, as the actual
     * list might be of any specific subtype.
     * 
     * @param source The source list which produces Number values (or subtypes)
     * @param destination The destination list which will store the copied Number values
     */
    public static void copyFromSource(List<? extends Number> source, List<Number> destination) {
        for (Number number : source) {
            destination.add(number); // Safe to read as Number and add to a Number list
        }
        // The commented line below would cause a compile error:
        // source.add(1); // Compile error! Can't add to a producer (? extends)
    }
    
    /**
     * CONSUMER SUPER example:
     * The destination list CONSUMES values that we write, so we use ? super Integer.
     * 
     * PECS PRINCIPLE: "Consumer Super"
     * - We are only WRITING to destination (putting values)
     * - Destination is a CONSUMER of Integer values
     * - So we use ? super Integer
     * 
     * BENEFITS:
     * - Flexibility: Can write to any List that can hold Integers (Integer, Number, Object)
     * - Type Safety: Guarantees we only add Integer objects
     * 
     * LIMITATIONS:
     * - Cannot read specific types from destination (only as Object)
     * 
     * OCP EXAM TIP:
     * The exam might test if you understand why you can read from a List<? super T>
     * but only as Object references. The reason is that the compiler doesn't know
     * which specific supertype the list is actually using.
     * 
     * @param source The source list of Integer values
     * @param destination The destination list which consumes Integer values
     */
    public static void copyToDestination(List<Integer> source, List<? super Integer> destination) {
        for (Integer number : source) {
            destination.add(number); // Safe to add Integer to any List<? super Integer>
        }
        // The commented line below would cause a compile error:
        // Integer first = destination.get(0); // Compile error! Can't read specific types from a consumer (? super)
    }
    
    /**
     * Combined PECS example:
     * - Source PRODUCES values (? extends Number)
     * - Destination CONSUMES values (? super Number)
     * 
     * This method represents the ideal application of PECS:
     * - It can copy from any list of Numbers or Number subtypes
     * - It can copy to any list that can hold Numbers or Number supertypes
     * 
     * REAL-WORLD PARALLEL:
     * This pattern is exactly what's used in the Java Collections API for methods like:
     * Collections.copy(dest, src) where:
     * - src is declared as List<? extends T> (producer)
     * - dest is declared as List<? super T> (consumer)
     * 
     * OCP EXAM TIP:
     * The exam often tests this pattern with questions about Collections utility methods
     * or asks you to identify the most flexible signature for a method that transfers elements.
     * 
     * @param source The source list which produces Number values
     * @param destination The destination list which consumes Number values
     */
    public static void copyNumbers(List<? extends Number> source, List<? super Number> destination) {
        for (Number number : source) {
            destination.add(number); // Safe to read as Number and add to any Number-compatible list
        }
    }
    
    /**
     * Practical example using PECS with Comparator.
     * 
     * PECS PRINCIPLE: "Consumer Super"
     * - The comparator CONSUMES Person objects to compare them
     * - So we use Comparator<? super Person>
     * 
     * This matches how the standard Java Collections.sort() and List.sort() methods
     * are defined in the JDK, using Comparator<? super E>.
     * 
     * WHY SUPER HERE?
     * - It allows comparators that can compare broader types to be used
     * - For example, if Person extends Human, a Comparator<Human> could be used
     * 
     * PRACTICAL USAGE:
     * This pattern is seen throughout the Java Collections API, especially in
     * sorting and comparing operations.
     * 
     * @param people The list of Person objects to sort
     */
    public static void sortByAge(List<Person> people) {
        // The comparator consumes Person objects, so we use ? super Person
        // This allows us to use not just Comparator<Person> but also Comparator<Object>
        people.sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
    }
    
    /**
     * Simple Person class for demonstration.
     * 
     * Used to demonstrate practical application of PECS rule, particularly
     * with comparators and sorting operations.
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

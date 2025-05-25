package com.ocp.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the unbounded wildcard (?) in Java Generics.
 * 
 * The unbounded wildcard represents "any type" and is used when:
 * 1. You need to work with unknown types
 * 2. You only care about methods from Object class
 * 3. You want to use methods that don't depend on the type parameter
 *
 * Key points for OCP Java 21:
 * - List<?> can accept any generic List type (e.g., List<String>, List<Integer>)
 * - You can read from List<?> (elements come out as Object)
 * - You cannot add elements (except null) to a List<?>
 */
public class Main_UnboundedWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Unbounded Wildcard (?) Examples ===\n");
        
        // Create different types of lists
        List<String> stringList = new ArrayList<>(Arrays.asList("Java", "Generics", "Wildcards"));
        List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3));
        
        // Demonstrate passing different lists to a method with unbounded wildcard
        System.out.println("Printing different list types with the same method:");
        printList(stringList);
        printList(intList);
        
        // Working with a reference of List<?>
        List<?> unknownList = stringList;
        
        // Reading is allowed - elements come out as Objects
        System.out.println("\nReading from List<?>:");
        Object firstElement = unknownList.get(0);
        System.out.println("First element: " + firstElement);
        
        // RESTRICTION: Cannot add elements (except null)
        System.out.println("\nRestrictions with List<?>:");
        // unknownList.add("New String"); // Compilation error!
        // Uncomment the line above to see the error: "add(capture<?>) in List cannot be applied to (String)"
        
        // Only null can be added to List<?>
        unknownList.add(null);
        System.out.println("After adding null: " + unknownList);
        
        // Demonstrate why this restriction exists
        System.out.println("\n*** WHY YOU CANNOT ADD ELEMENTS TO List<?> ***");
        System.out.println("The compiler doesn't know what type the list actually holds.");
        System.out.println("If we could add a String to List<?> that was actually a List<Integer>,");
        System.out.println("it would break type safety and cause runtime ClassCastExceptions.");
        System.out.println("The wildcard capture prevents this potential type safety violation.");
        
        // Common mistakes
        System.out.println("\n*** COMMON MISTAKES WITH UNBOUNDED WILDCARDS ***");
        System.out.println("1. Trying to add elements to a List<?>");
        System.out.println("2. Trying to create a collection using wildcard: new ArrayList<?>() // Wrong!");
        System.out.println("3. Assuming List<?> is equivalent to List<Object> (it's not)");
        
        // When to use unbounded wildcards
        System.out.println("\n*** WHEN TO USE UNBOUNDED WILDCARDS ***");
        System.out.println("1. When writing methods that only read from a collection and don't care about the type");
        System.out.println("2. When the code only uses methods in the Object class");
        System.out.println("3. When working with multiple generic types in a type-safe way");
    }
    
    /**
     * This method can accept ANY type of List thanks to the unbounded wildcard.
     * Note that we can only read from the list as Objects, not add to it.
     * 
     * @param list Any type of List
     */
    public static void printList(List<?> list) {
        System.out.println("\nList contents (" + 
                         (list.isEmpty() ? "empty" : list.get(0).getClass().getSimpleName()) + 
                         " list):");
        for (Object item : list) {
            System.out.println("  - " + item);
        }
        
        // We cannot add elements (except null)
        // list.add("something"); // Compilation error!
    }
}

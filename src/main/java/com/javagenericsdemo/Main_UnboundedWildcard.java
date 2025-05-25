package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates unbounded wildcards (List<?>) in Java Generics.
 * 
 * Unbounded wildcards (?) represent "any type" and are used when:
 * 1. The code only depends on methods in Object
 * 2. The method is using functionality not dependent on the generic parameter
 * 3. You want to work with multiple generic types in a type-safe way
 */
public class Main_UnboundedWildcard {
    
    public static void main(String[] args) {
        // Creating different types of lists
        List<String> strings = new ArrayList<>(Arrays.asList("Java", "Generics", "Wildcards"));
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        
        System.out.println("=== Demonstrating Unbounded Wildcard (?) ===");
        
        // We can pass any of these lists to a method with unbounded wildcard
        printList(strings);
        printList(integers);
        printList(doubles);
        
        // We can assign any generic list to a List<?> reference
        List<?> unknownList = strings;
        System.out.println("\nAccessing List<?> elements:");
        
        // READING is allowed - elements come out as Objects
        Object firstElement = unknownList.get(0);
        System.out.println("First element: " + firstElement);
        
        // WRITING is NOT allowed (except null)
        // unknownList.add("New String"); // Compile error!
        unknownList.add(null); // Only null is allowed
        
        // Why can't we add elements to a List<?>?
        System.out.println("\n*** WHY? The Unbounded Wildcard Limitation ***");
        System.out.println("The compiler doesn't know what type the list actually holds.");
        System.out.println("If we could add a String to a List<?> that was actually a List<Integer>,");
        System.out.println("it would break type safety. So Java prevents any additions except null.");
        
        // Demonstrating what would happen if it were allowed (which it isn't)
        List<?> mysteryList = integers; // A List<Integer> assigned to List<?>
        // If this were allowed (it's not):
        // mysteryList.add("String"); // Would add a String to a List<Integer>!
        // When retrieving: Integer x = integers.get(3); // ClassCastException at runtime!
        
        // Common mistake: attempting to use wildcards when creating a collection
        // List<?> emptyList = new ArrayList<?>(); // Compile error!
        // Correct way:
        List<?> emptyList = new ArrayList<>(); // Diamond operator uses type inference
    }
    
    /**
     * This method can accept ANY type of List thanks to the unbounded wildcard.
     * Note that we can only read from the list as Objects, not add to it.
     */
    public static void printList(List<?> list) {
        System.out.println("\nPrinting list of type: " + 
                          (list.isEmpty() ? "empty" : list.get(0).getClass().getSimpleName()));
        for (Object item : list) {
            System.out.println(" - " + item);
        }
        
        // Cannot add elements (except null)
        // list.add("something"); // Compile error!
    }
}

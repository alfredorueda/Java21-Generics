package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates unbounded wildcards (List<?>) in Java Generics for OCP Java 21 preparation.
 * 
 * Unbounded wildcards (?) represent "any type whatsoever" and are the most 
 * flexible but also most restrictive form of wildcard in Java's generic system.
 * 
 * WHEN TO USE UNBOUNDED WILDCARDS (?):
 * 1. When the code only depends on methods available in the Object class
 * 2. When implementing methods that operate on the structure of the collection
 *    rather than the specific element type (e.g., size(), isEmpty(), clear())
 * 3. When you need to work with heterogeneous collections in a type-safe way
 * 4. When the type parameter is completely irrelevant to the operation
 * 
 * MENTAL MODEL:
 * Think of List<?> as a "completely generic container" - you know it contains
 * objects of some consistent type, but you have no information about what that type is.
 * You can take items out (as Object references), but you cannot put anything in
 * (except null) because you don't know what type is acceptable.
 * 
 * REAL-WORLD ANALOGY:
 * Imagine a sealed box that came from an unknown department in a company.
 * You can open it and look at the items (they're all company items), but
 * you can't add new items because you don't know which department it's
 * supposed to go back to and what standards they require.
 * 
 * OCP EXAM TIP:
 * The exam may test your understanding of the differences between:
 * - Raw types (List) - legacy, unsafe, avoid using
 * - Unbounded wildcards (List<?>) - type-safe but restrictive
 * - Specific generics (List<String>) - type-safe and specific
 * 
 * List<?> is often confused with raw types (List), but they're fundamentally different:
 * - List<?> is type-safe and enforces that it contains a single consistent type
 * - List (raw) bypasses the generic type system and is not type-safe
 */
public class Main_UnboundedWildcard {
    
    public static void main(String[] args) {
        // Creating different types of lists to demonstrate unbounded wildcard flexibility
        List<String> strings = new ArrayList<>(Arrays.asList("Java", "Generics", "Wildcards"));
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        
        System.out.println("=== Demonstrating Unbounded Wildcard (?) ===");
        
        // We can pass any of these lists to a method with unbounded wildcard
        // This demonstrates the extreme flexibility of unbounded wildcards for reading
        printList(strings);  // Works with List<String>
        printList(integers); // Works with List<Integer>
        printList(doubles);  // Works with List<Double>
        
        // We can assign any generic list to a List<?> reference
        // This is safe because we're just changing the view, not the underlying list type
        List<?> unknownList = strings;
        System.out.println("\nAccessing List<?> elements:");
        
        // READING is allowed - elements come out as Objects
        // When using List<?>, you can only read elements as Objects
        Object firstElement = unknownList.get(0);
        System.out.println("First element: " + firstElement);
        
        // WRITING is NOT allowed (except null)
        // The commented line below would cause a compile error:
        // unknownList.add("New String"); // Compile error!
        unknownList.add(null); // Only null is allowed, as it's compatible with any reference type
        
        // Why can't we add elements to a List<?>?
        // This section explains the key conceptual limitation
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
        // The commented line below would cause a compile error:
        // List<?> emptyList = new ArrayList<?>(); // Compile error!
        
        // Correct way to create a collection with unbounded wildcard type:
        List<?> emptyList = new ArrayList<>(); // Diamond operator uses type inference
        System.out.println("\nEmpty list size: " + emptyList.size());
    }
    
    /**
     * This method can accept ANY type of List thanks to the unbounded wildcard.
     * 
     * PRACTICAL USAGE:
     * This pattern is commonly used when:
     * 1. You only need to read from the collection and don't care about the specific type
     * 2. You're only using Object methods or collection structure methods
     * 3. You want maximum flexibility in what types of collections the method accepts
     * 
     * KEY LIMITATIONS:
     * 1. Cannot add elements to the list (except null)
     * 2. Can only read elements as Object references
     * 3. Cannot use type-specific methods on elements without casting
     * 
     * COMPARISON WITH OTHER WILDCARDS:
     * - List<?>: most flexible for input, most restrictive for operations
     * - List<? extends X>: allows reading as type X, no adding
     * - List<? super Y>: allows adding objects of type Y, reading as Object
     * 
     * OCP EXAM TIP:
     * The List<?> unbounded wildcard is the most restrictive for operations but
     * the most flexible for input types. It's often used in utility methods that
     * don't need to know the specific element type.
     * 
     * @param list A list of any type whatsoever
     */
    public static void printList(List<?> list) {
        System.out.println("\nPrinting list of type: " + 
                          (list.isEmpty() ? "empty" : list.get(0).getClass().getSimpleName()));
        
        // We can iterate through the list safely, treating elements as Objects
        for (Object item : list) {
            System.out.println(" - " + item);
            
            // We can use any method from Object class
            System.out.println("   Object hashCode: " + item.hashCode());
            
            // But we can't use type-specific methods without casting
            // For example, if we knew an element was a String:
            if (item instanceof String) {
                String s = (String) item;
                System.out.println("   String length: " + s.length());
            }
        }
        
        // Cannot add elements (except null) - the commented line below would cause a compile error
        // list.add("something"); // Compile error!
        
        // We can still use methods that operate on the collection structure
        System.out.println(" List size: " + list.size());
    }
}

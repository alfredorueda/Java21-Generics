package com.ocp.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates type inference in Java Generics.
 * 
 * Type inference is the ability of the Java compiler to determine the
 * appropriate type arguments for generic method calls and instance creation
 * expressions based on the context in which they appear.
 * 
 * Key points for OCP Java 21:
 * - Diamond operator (<>) allows type inference for constructors
 * - Method type inference allows generic methods to infer types from arguments
 * - Target typing allows inference based on the context (e.g., assignment)
 */
public class Main_TypeInference {
    
    public static void main(String[] args) {
        System.out.println("=== Type Inference in Generics Examples ===\n");
        
        // 1. Diamond Operator (<>)
        System.out.println("1. Diamond Operator examples:");
        
        // Before Java 7 (explicit type arguments)
        List<String> oldStyle = new ArrayList<String>();
        
        // With diamond operator (Java 7+)
        List<String> withDiamond = new ArrayList<>(); // Type inferred as ArrayList<String>
        
        // The compiler infers the type based on the variable declaration
        withDiamond.add("Java");
        withDiamond.add("Generics");
        System.out.println("Diamond operator list: " + withDiamond);
        
        // Complex example with nested generics
        Map<String, List<Integer>> complexMap = new HashMap<>(); // Instead of new HashMap<String, List<Integer>>()
        complexMap.put("numbers", new ArrayList<>());  // Type inferred as ArrayList<Integer>
        
        // 2. Method Type Inference
        System.out.println("\n2. Method Type Inference examples:");
        
        // The compiler infers the type parameter <String> based on the arguments
        List<String> names = createList("Alice", "Bob", "Charlie");
        System.out.println("Inferred string list: " + names);
        
        // The compiler infers <Integer> here
        List<Integer> numbers = createList(1, 2, 3);
        System.out.println("Inferred integer list: " + numbers);
        
        // Type inference for multiple type parameters
        String result = concatenate("Hello", " World");
        System.out.println("Inferred string concatenation: " + result);
        
        Integer sum = concatenate(5, 10); // Infers Integer for both T and U
        System.out.println("Inferred integer concatenation: " + sum);
        
        // 3. Target Typing
        System.out.println("\n3. Target Typing examples:");
        
        // Type inference based on the target type (variable declaration)
        List<String> targetTyped = createEmptyList(); // Infers <String> from the variable type
        targetTyped.add("Target typed");
        System.out.println("Target typed list: " + targetTyped);
        
        // 4. Type Witness (explicit type arguments)
        System.out.println("\n4. Type Witness examples:");
        
        // Sometimes type inference needs help
        List<String> explicitType = Main_TypeInference.<String>createEmptyList();
        explicitType.add("Explicit type");
        System.out.println("Explicit type list: " + explicitType);
        
        // Educational explanation
        System.out.println("\n*** WHY TYPE INFERENCE MATTERS ***");
        System.out.println("1. Readability: Code is cleaner and less verbose");
        System.out.println("2. Maintainability: Fewer types to update if changes are needed");
        System.out.println("3. Flexibility: Compiler often infers more specific types than you might write");
        
        // Common mistakes and gotchas
        System.out.println("\n*** COMMON MISTAKES WITH TYPE INFERENCE ***");
        System.out.println("1. Relying on inference when creating anonymous inner classes before Java 9");
        System.out.println("2. Assuming type inference works in all contexts (it has limitations)");
        System.out.println("3. Not using explicit type arguments when inference is ambiguous");
        System.out.println("4. Forgetting that var is local variable type inference, not related to generics");
        
        // Java 10+ var example
        System.out.println("\nBonus: Using var with generics (Java 10+):");
        var stringList = new ArrayList<String>();
        stringList.add("Using var");
        // stringList.add(42); // Error: var still preserves type safety!
        System.out.println("var with generics: " + stringList);
    }
    
    /**
     * Creates a List containing the provided elements.
     * Type parameter T is inferred from the arguments.
     * 
     * @param <T> The type of elements in the list
     * @param elements The elements to add to the list
     * @return A List containing the elements
     */
    @SafeVarargs
    public static <T> List<T> createList(T... elements) {
        List<T> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element);
        }
        return result;
    }
    
    /**
     * Combines two elements of the same type.
     * Type parameter T is inferred from the arguments.
     * 
     * @param <T> The type of elements to combine
     * @param first The first element
     * @param second The second element
     * @return The combined result
     */
    @SuppressWarnings("unchecked")
    public static <T> T concatenate(T first, T second) {
        if (first instanceof String) {
            // String concatenation
            return (T) (first.toString() + second.toString());
        } else if (first instanceof Integer) {
            // Integer addition
            return (T) Integer.valueOf(((Integer) first) + ((Integer) second));
        }
        // Default to string concatenation
        return (T) (first.toString() + second.toString());
    }
    
    /**
     * Creates an empty list.
     * The type parameter T is inferred from the assignment context.
     * 
     * @param <T> The type of elements in the list
     * @return An empty list
     */
    public static <T> List<T> createEmptyList() {
        return new ArrayList<>();
    }
}

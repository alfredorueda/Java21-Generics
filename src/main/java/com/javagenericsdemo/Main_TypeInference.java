package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates type inference in Java Generics for OCP Java 21 preparation.
 * 
 * Type inference is the ability of the Java compiler to determine the appropriate
 * type arguments for generic method calls and instance creation expressions based on 
 * the context in which they appear, without requiring explicit type parameters.
 * 
 * EVOLUTION OF TYPE INFERENCE IN JAVA:
 * - Java 5: Limited inference for generic methods
 * - Java 7: Diamond operator (<>) for constructor calls
 * - Java 8: Improved method type inference
 * - Java 10: Local variable type inference (var)
 * - Java 21: Further improvements in pattern matching
 * 
 * KEY CONCEPTS:
 * 1. Diamond operator (<>) - Infers type arguments for constructors
 * 2. Target typing - Infers types based on the context where the expression appears
 * 3. Method type inference - Infers type parameters for generic methods
 * 4. Local variable type inference (var) - Infers the type of local variables
 * 
 * MENTAL MODEL:
 * Think of the compiler as a detective that uses context clues to determine types.
 * When you provide a generic method or constructor with incomplete type information,
 * the compiler examines the surrounding code to "fill in the blanks."
 * 
 * REAL-WORLD ANALOGY:
 * Imagine ordering at a coffee shop. If you say "I'll have a large," the barista
 * infers you want a large coffee (the default) based on context. But if you're at an
 * ice cream shop with the same statement, they'll infer you want a large ice cream.
 * Different contexts lead to different inferences.
 * 
 * OCP EXAM TIP:
 * Type inference questions often involve determining what will compile successfully
 * without explicit type parameters, and what types are inferred in complex scenarios.
 * Pay special attention to var with generics, diamond operator limitations, and 
 * method reference type inference.
 */
public class Main_TypeInference {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Type Inference in Generics ===");
        
        // 1. Diamond Operator (<>)
        // This feature lets the compiler infer the type arguments for constructor calls
        System.out.println("\n1. Diamond Operator examples:");
        
        // Before Java 7 (explicit type arguments)
        // This approach requires repeating the type arguments on both sides
        List<String> oldStyle = new ArrayList<String>();
        
        // With diamond operator (Java 7+)
        // The compiler infers ArrayList<String> based on the variable type declaration
        List<String> withDiamond = new ArrayList<>(); // Type inferred from the left side
        
        // The compiler ensures type safety is maintained despite type inference
        withDiamond.add("Type");
        withDiamond.add("Inference");
        System.out.println("Diamond operator list: " + withDiamond);
        
        // More complex example with nested generics
        // Without the diamond operator, this would be much more verbose:
        // Map<String, List<Integer>> complexMap = new HashMap<String, List<Integer>>();
        Map<String, List<Integer>> complexMap = new HashMap<>(); 
        
        // 2. Target Typing for Method Arguments
        // The compiler uses the context to determine generic types
        System.out.println("\n2. Target Typing examples:");
        
        // Type inference in method calls
        // The compiler infers String based on the literal arguments
        List<String> names = createList("Alice", "Bob", "Charlie");
        System.out.println("Inferred list type: " + names.getClass().getSimpleName() + 
                          "<" + names.get(0).getClass().getSimpleName() + ">");
        
        // Compiler infers Integer based on the numeric literal arguments
        List<Integer> numbers = createList(1, 2, 3);
        System.out.println("Inferred list type: " + numbers.getClass().getSimpleName() + 
                          "<" + numbers.get(0).getClass().getSimpleName() + ">");
        
        // 3. Method Type Inference
        // The compiler infers type parameters for generic methods
        System.out.println("\n3. Method Type Inference examples:");
        
        // Type is inferred from the method arguments as String
        String result1 = concatenate("Hello", "World");
        System.out.println("Inferred String concatenation: " + result1);
        
        // Type is inferred as Integer for both T and U
        Integer result2 = concatenate(5, 10);
        System.out.println("Inferred Integer concatenation: " + result2);
        
        // Different types can be inferred for T and U
        // T is inferred as String, U as Integer
        String result3 = concatenateWithSeparator("Count", 5, " is: ");
        System.out.println("Mixed type concatenation: " + result3);
        
        // 4. Explicit Type Arguments (when inference fails)
        // Sometimes the compiler needs extra help
        System.out.println("\n4. Explicit Type Arguments examples:");
        
        // Sometimes type inference needs help
        // This will return an empty list of Strings
        List<String> emptyStrings = Main_TypeInference.<String>emptyList();
        
        // Without explicit type, compiler can't infer the type parameter
        // The commented line below would cause a compile error or warning:
        // List<String> emptyStrings2 = emptyList(); // Compile error or warning
        
        System.out.println("Empty list type: " + emptyStrings.getClass().getSimpleName());
        
        // 5. Var with Generics (Java 10+)
        // Local variable type inference works with generics
        System.out.println("\n5. Var with Generics (Java 10+):");
        
        var stringList = new ArrayList<String>();
        stringList.add("Using var");
        // The commented line below would cause a compile error:
        // stringList.add(42); // Compile error - type is still enforced!
        
        System.out.println("Using var with generics: " + stringList);
        
        // Educational section with deeper insights
        System.out.println("\n*** WHY TYPE INFERENCE MATTERS ***");
        System.out.println("1. Readability: Code is cleaner and less verbose");
        System.out.println("2. Maintainability: Less redundancy means fewer places to update types");
        System.out.println("3. Flexibility: The compiler can often determine more specific types than written");
        System.out.println("4. Safety: Type safety is preserved even with inferred types");
        
        System.out.println("\n*** COMMON PITFALLS ***");
        System.out.println("1. Cannot use diamond operator with anonymous inner classes before Java 9");
        System.out.println("2. Type inference sometimes needs explicit help for complex nested generics");
        System.out.println("3. Type inference works differently in method invocation contexts vs. assignment contexts");
    }
    
    /**
     * Creates a list with the provided elements.
     * 
     * INFERENCE MECHANISM:
     * The type parameter T is inferred from the arguments passed to this method.
     * If all arguments are Strings, T will be String; if all are Integers, T will be Integer.
     * 
     * PRACTICAL USAGE:
     * This pattern is commonly used in utility methods that need to work with various types
     * without requiring explicit type parameters at the call site.
     * 
     * INTERESTING FACT:
     * This pattern is used extensively in the Java Collections API, such as in 
     * java.util.Arrays.asList() and java.util.List.of() methods.
     * 
     * OCP EXAM TIP:
     * Pay attention to how varargs (...) interact with generics. The @SafeVarargs
     * annotation suppresses unchecked warnings related to variable argument usage
     * with generics.
     * 
     * @param <T> The inferred type of the list elements
     * @param elements Variable number of elements to add to the list
     * @return A list containing all the provided elements
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
     * Concatenates two elements of the same type.
     * 
     * INFERENCE MECHANISM:
     * The type parameter T is inferred from the arguments, and both arguments must
     * be of the same type (or compatible types).
     * 
     * TYPE SPECIALIZATION:
     * This method behaves differently based on the inferred type:
     * - For Strings: Performs string concatenation
     * - For Integers: Performs addition
     * - For other types: Performs toString() concatenation
     * 
     * PRACTICAL USAGE:
     * This demonstrates how a generic method can provide specialized behavior
     * based on the actual runtime type, despite type erasure.
     * 
     * OCP EXAM TIP:
     * Look for questions about type specialization with generics. Understanding
     * how to handle different types in generic methods is important, especially
     * when combined with inheritance and polymorphism.
     * 
     * @param <T> The type of both elements
     * @param first The first element
     * @param second The second element
     * @return The result of concatenating or combining the elements
     */
    public static <T> T concatenate(T first, T second) {
        if (first instanceof String) {
            // String concatenation for String type
            @SuppressWarnings("unchecked")
            T result = (T) (first.toString() + second.toString());
            return result;
        } else if (first instanceof Integer) {
            // Addition for Integer type
            @SuppressWarnings("unchecked")
            T result = (T) Integer.valueOf(((Integer) first) + ((Integer) second));
            return result;
        }
        // Default to string concatenation for other types
        @SuppressWarnings("unchecked")
        T result = (T) (first.toString() + second.toString());
        return result;
    }
    
    /**
     * Concatenates elements of different types with a separator.
     * 
     * INFERENCE MECHANISM:
     * This method demonstrates multi-parameter type inference, where
     * the compiler infers different type parameters (T and U) from different arguments.
     * 
     * PRACTICAL USAGE:
     * This pattern is useful when you need to work with heterogeneous data
     * but still want the benefits of compile-time type checking.
     * 
     * OCP EXAM TIP:
     * Multi-parameter type inference is more complex. The compiler uses the 
     * "most specific applicable method" rule to determine the best match when
     * multiple methods could apply.
     * 
     * @param <T> The type of the first element
     * @param <U> The type of the second element
     * @param first The first element
     * @param second The second element
     * @param separator The string to place between the elements
     * @return A string containing the concatenated elements with the separator
     */
    public static <T, U> String concatenateWithSeparator(T first, U second, String separator) {
        return first.toString() + separator + second.toString();
    }
    
    /**
     * Creates an empty list of type T.
     * 
     * INFERENCE LIMITATION:
     * This method demonstrates a limitation of type inference. Since there are
     * no parameters from which to infer the type T, it must be explicitly specified
     * when calling this method.
     * 
     * PRACTICAL USAGE:
     * This pattern is seen in factory methods that create empty collections or
     * default instances when there are no context clues for type inference.
     * 
     * OCP EXAM TIP:
     * Be aware of situations where type inference fails and explicit type arguments
     * are required. This is particularly important with methods that have no parameters
     * or where the parameters don't provide enough information for inference.
     * 
     * @param <T> The type of elements in the list
     * @return An empty list of type T
     */
    public static <T> List<T> emptyList() {
        return new ArrayList<>();
    }
}

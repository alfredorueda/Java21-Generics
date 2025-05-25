package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates type inference in Java Generics.
 * 
 * Type inference is the ability of the Java compiler to determine the appropriate
 * type arguments for generic method calls and instance creation expressions based on 
 * the context in which they appear.
 * 
 * Key features include:
 * 1. Diamond operator (<>)
 * 2. Target typing
 * 3. Method type inference
 */
public class Main_TypeInference {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Type Inference in Generics ===");
        
        // 1. Diamond Operator (<>)
        System.out.println("\n1. Diamond Operator examples:");
        
        // Before Java 7 (explicit type arguments)
        List<String> oldStyle = new ArrayList<String>();
        
        // With diamond operator (Java 7+)
        List<String> withDiamond = new ArrayList<>(); // Type inferred from the left side
        
        // The compiler infers ArrayList<String> based on the variable type
        withDiamond.add("Type");
        withDiamond.add("Inference");
        System.out.println("Diamond operator list: " + withDiamond);
        
        // More complex example with nested generics
        Map<String, List<Integer>> complexMap = new HashMap<>(); // Instead of new HashMap<String, List<Integer>>()
        
        // 2. Target Typing for Method Arguments
        System.out.println("\n2. Target Typing examples:");
        
        // Type inference in method calls
        List<String> names = createList("Alice", "Bob", "Charlie");
        System.out.println("Inferred list type: " + names.getClass().getSimpleName() + 
                          "<" + names.get(0).getClass().getSimpleName() + ">");
        
        // Compiler infers Integer based on the addNumbers method parameter
        List<Integer> numbers = createList(1, 2, 3);
        System.out.println("Inferred list type: " + numbers.getClass().getSimpleName() + 
                          "<" + numbers.get(0).getClass().getSimpleName() + ">");
        
        // 3. Method Type Inference
        System.out.println("\n3. Method Type Inference examples:");
        
        // Type is inferred from the method arguments
        String result1 = concatenate("Hello", "World");
        System.out.println("Inferred String concatenation: " + result1);
        
        // Type is inferred as Integer for both T and U
        Integer result2 = concatenate(5, 10);
        System.out.println("Inferred Integer concatenation: " + result2);
        
        // Different types can be inferred for T and U
        String result3 = concatenateWithSeparator("Count", 5, " is: ");
        System.out.println("Mixed type concatenation: " + result3);
        
        // 4. Explicit Type Arguments (when inference fails)
        System.out.println("\n4. Explicit Type Arguments examples:");
        
        // Sometimes type inference needs help
        // This will return an empty list of Strings
        List<String> emptyStrings = Main_TypeInference.<String>emptyList();
        
        // Without explicit type, compiler can't infer the type parameter
        // List<String> emptyStrings2 = emptyList(); // Compile error or warning
        
        System.out.println("Empty list type: " + emptyStrings.getClass().getSimpleName());
        
        // 5. Var with Generics (Java 10+)
        System.out.println("\n5. Var with Generics (Java 10+):");
        
        var stringList = new ArrayList<String>();
        stringList.add("Using var");
        // stringList.add(42); // Compile error - type is still enforced!
        
        System.out.println("Using var with generics: " + stringList);
        
        // Educational section
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
     * Type parameter T is inferred from the arguments.
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
     * Type parameter T is inferred from the arguments.
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
     * Type parameters T and U are inferred from arguments,
     * with the return type being determined by context.
     */
    public static <T, U> String concatenateWithSeparator(T first, U second, String separator) {
        return first.toString() + separator + second.toString();
    }
    
    /**
     * Creates an empty list of type T.
     * Type parameter T must be explicitly specified when calling this method
     * because there are no parameters from which to infer the type.
     */
    public static <T> List<T> emptyList() {
        return new ArrayList<>();
    }
}

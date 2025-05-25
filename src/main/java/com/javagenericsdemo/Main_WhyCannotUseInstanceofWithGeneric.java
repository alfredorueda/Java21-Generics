package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates why you cannot use instanceof with generic types in Java.
 * 
 * This limitation is directly tied to type erasure, one of the fundamental
 * aspects of Java's implementation of generics.
 */
public class Main_WhyCannotUseInstanceofWithGeneric {
    
    public static void main(String[] args) {
        System.out.println("=== Why You Cannot Use instanceof with Generic Types ===");
        
        // Create various generic objects
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        Map<String, Integer> stringIntMap = new HashMap<>();
        
        // 1. Basic instanceof behavior
        System.out.println("\n1. Basic instanceof behavior:");
        
        System.out.println("stringList instanceof List: " + (stringList instanceof List));
        System.out.println("stringList instanceof ArrayList: " + (stringList instanceof ArrayList));
        System.out.println("stringIntMap instanceof Map: " + (stringIntMap instanceof Map));
        
        // 2. The problem with generic types and instanceof
        System.out.println("\n2. Why instanceof doesn't work with specific generic types:");
        
        // These won't compile:
        // System.out.println(stringList instanceof List<String>); // Compile error!
        // System.out.println(integerList instanceof List<Integer>); // Compile error!
        
        System.out.println("Due to type erasure, at runtime:");
        System.out.println("- List<String> becomes just List");
        System.out.println("- List<Integer> becomes just List");
        System.out.println("- Map<String, Integer> becomes just Map");
        
        // Demonstrating that generic type information is erased
        System.out.println("\nType erasure in action:");
        System.out.println("stringList class: " + stringList.getClass().getName());
        System.out.println("integerList class: " + integerList.getClass().getName());
        System.out.println("Are they the same class? " + (stringList.getClass() == integerList.getClass()));
        
        // 3. Workarounds for checking element types
        System.out.println("\n3. Workarounds for checking element types:");
        
        stringList.add("Hello");
        integerList.add(42);
        
        System.out.println("\nWorkaround 1: Check first element's type");
        System.out.println("First element of stringList is a String: " + 
                         (!stringList.isEmpty() && stringList.get(0) instanceof String));
        System.out.println("First element of integerList is an Integer: " + 
                         (!integerList.isEmpty() && integerList.get(0) instanceof Integer));
        
        System.out.println("\nWorkaround 2: Use a type token class");
        GenericTypeChecker<String> stringChecker = new GenericTypeChecker<>(String.class);
        GenericTypeChecker<Integer> intChecker = new GenericTypeChecker<>(Integer.class);
        
        System.out.println("stringList contains Strings: " + stringChecker.checkListType(stringList));
        System.out.println("integerList contains Strings: " + stringChecker.checkListType(integerList));
        System.out.println("integerList contains Integers: " + intChecker.checkListType(integerList));
        
        // 4. Using instanceof with wildcards (raw types)
        System.out.println("\n4. Using instanceof with wildcards (raw types):");
        
        System.out.println("stringList instanceof List<?> is valid in the code, but...");
        System.out.println("...at runtime it's just checking if stringList instanceof List");
        
        // This is the same as checking "instanceof List" due to erasure
        boolean isListWildcard = stringList instanceof List<?>;
        System.out.println("stringList instanceof List<?>: " + isListWildcard);
        
        // 5. Practical example of the problem
        System.out.println("\n5. Practical example of why this matters:");
        
        Object obj1 = stringList;
        Object obj2 = integerList;
        
        System.out.println("Both objects are Lists: " + (obj1 instanceof List && obj2 instanceof List));
        System.out.println("But we can't tell at runtime if they're List<String> or List<Integer>");
        
        System.out.println("\nWhen writing generic code, we have to rely on other mechanisms");
        System.out.println("to ensure type safety, such as bounded type parameters and wildcards.");
        
        // Educational section
        System.out.println("\n*** WHY THIS LIMITATION EXISTS ***");
        System.out.println("1. Type erasure removes generic type information at compilation");
        System.out.println("2. At runtime, JVM doesn't have access to the generic type parameters");
        System.out.println("3. This was done for backward compatibility with pre-Java 5 code");
        
        System.out.println("\n*** BEST PRACTICES ***");
        System.out.println("1. Use generics properly at compile time for type safety");
        System.out.println("2. If runtime type checking is needed, store type information separately");
        System.out.println("3. Consider using Class<?> tokens for runtime type operations");
        System.out.println("4. Design your API to avoid needing runtime generic type checks");
    }
    
    /**
     * A helper class that demonstrates how to check element types at runtime
     * by storing the Class object (type token).
     */
    static class GenericTypeChecker<T> {
        private Class<T> type;
        
        public GenericTypeChecker(Class<T> type) {
            this.type = type;
        }
        
        public boolean checkListType(List<?> list) {
            if (list.isEmpty()) {
                return false; // Can't determine type of empty list
            }
            
            // Check if the first element is of the expected type
            return type.isInstance(list.get(0));
        }
    }
}

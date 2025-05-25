package com.ocp.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates why you cannot use instanceof with generic types in Java.
 * 
 * This limitation is directly related to type erasure - the process by which
 * generic type information is removed during compilation.
 * 
 * Key points for OCP Java 21:
 * - Cannot use instanceof with generic types: obj instanceof List<String> is illegal
 * - You can only use instanceof with raw types: obj instanceof List is legal
 * - At runtime, List<String> and List<Integer> are indistinguishable due to erasure
 */
public class Main_WhyCannotUseInstanceofWithGeneric {
    
    public static void main(String[] args) {
        System.out.println("=== Why You Cannot Use instanceof with Generic Types ===\n");
        
        // Create various generic objects
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        
        List<Integer> integerList = new ArrayList<>();
        integerList.add(42);
        
        Map<String, Integer> stringIntMap = new HashMap<>();
        stringIntMap.put("answer", 42);
        
        // 1. Basic instanceof behavior (with raw types)
        System.out.println("1. Basic instanceof behavior:");
        
        // These work fine - checking against raw types
        System.out.println("stringList instanceof List: " + (stringList instanceof List));
        System.out.println("stringList instanceof ArrayList: " + (stringList instanceof ArrayList));
        System.out.println("stringIntMap instanceof Map: " + (stringIntMap instanceof Map));
        
        // 2. The problem with specific generic types
        System.out.println("\n2. Why instanceof doesn't work with specific generic types:");
        
        // These won't compile:
        // System.out.println(stringList instanceof List<String>); // Compile error!
        // System.out.println(integerList instanceof List<Integer>); // Compile error!
        
        System.out.println("The compiler prevents using instanceof with specific generic types.");
        System.out.println("This is because after type erasure:");
        System.out.println("- List<String> becomes just List");
        System.out.println("- List<Integer> becomes just List");
        System.out.println("So there's no way to distinguish them at runtime.");
        
        // Demonstrating that generic type information is erased
        System.out.println("\nType erasure in action:");
        System.out.println("stringList class: " + stringList.getClass().getName());
        System.out.println("integerList class: " + integerList.getClass().getName());
        System.out.println("Are they the same class? " + (stringList.getClass() == integerList.getClass()));
        
        // 3. Workarounds for checking element types
        System.out.println("\n3. Workarounds for checking element types:");
        
        // Workaround 1: Check first element's type
        System.out.println("\nWorkaround 1: Check first element's type");
        System.out.println("First element of stringList is a String: " + 
                         (!stringList.isEmpty() && stringList.get(0) instanceof String));
        System.out.println("First element of integerList is an Integer: " + 
                         (!integerList.isEmpty() && integerList.get(0) instanceof Integer));
        
        // Workaround 2: Use a type token class
        System.out.println("\nWorkaround 2: Use a type token class");
        GenericTypeChecker<String> stringChecker = new GenericTypeChecker<>(String.class);
        GenericTypeChecker<Integer> intChecker = new GenericTypeChecker<>(Integer.class);
        
        System.out.println("stringList contains Strings: " + stringChecker.checkListType(stringList));
        System.out.println("integerList contains Strings: " + stringChecker.checkListType(integerList));
        System.out.println("integerList contains Integers: " + intChecker.checkListType(integerList));
        
        // 4. Practical example with Object references
        System.out.println("\n4. Practical example with Object references:");
        
        Object obj1 = stringList;
        Object obj2 = integerList;
        
        System.out.println("Both objects are Lists: " + (obj1 instanceof List && obj2 instanceof List));
        System.out.println("But we can't tell at runtime if they're List<String> or List<Integer>");
        
        // Safe processing using instanceof and generic methods
        System.out.println("\nSafe processing using instanceof and casting:");
        if (obj1 instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof String) {
            @SuppressWarnings("unchecked")
            List<String> castedList = (List<String>) list;
            System.out.println("First string: " + castedList.get(0));
        }
        
        // Educational explanation
        System.out.println("\n*** WHY THIS LIMITATION EXISTS ***");
        System.out.println("1. Type erasure removes generic type information at compilation");
        System.out.println("2. At runtime, JVM has no way to distinguish List<String> from List<Integer>");
        System.out.println("3. This was done for backward compatibility with pre-Java 5 code");
        
        // Best practices
        System.out.println("\n*** BEST PRACTICES ***");
        System.out.println("1. Use generics properly at compile time for type safety");
        System.out.println("2. Store type information separately when needed at runtime");
        System.out.println("3. Use Class<?> tokens when you need runtime type checking");
        System.out.println("4. Design APIs to avoid needing runtime generic type checks");
    }
    
    /**
     * A helper class that demonstrates how to check element types at runtime
     * by storing the Class object (type token).
     * 
     * @param <T> The type to check for
     */
    static class GenericTypeChecker<T> {
        private Class<T> type;
        
        public GenericTypeChecker(Class<T> type) {
            this.type = type;
        }
        
        /**
         * Checks if the first element in the list is of type T.
         * 
         * @param list The list to check
         * @return true if the first element is of type T, false otherwise
         */
        public boolean checkListType(List<?> list) {
            if (list.isEmpty()) {
                return false; // Can't determine type of empty list
            }
            
            // Check if the first element is of the expected type
            return type.isInstance(list.get(0));
        }
    }
}

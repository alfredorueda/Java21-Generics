package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Demonstrates generic methods with wildcards and how they solve problems
 * that cannot be addressed with class-level generics alone.
 * 
 * Generic methods allow operations on different types within a non-generic class
 * or with more flexibility than the containing class's type parameters provide.
 */
public class Main_GenericMethodWithWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Generic Methods with Wildcards ===");
        
        // 1. Basic generic method example
        System.out.println("\n1. Basic generic method example:");
        
        // The method can work with any type
        String result1 = identity("Hello");
        Integer result2 = identity(42);
        System.out.println("String identity: " + result1);
        System.out.println("Integer identity: " + result2);
        
        // 2. Generic methods with wildcards
        System.out.println("\n2. Generic methods with wildcards:");
        
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
        List<String> strings = Arrays.asList("a", "b", "c");
        
        // Count elements greater than a value in different numeric lists
        System.out.println("Integers > 3: " + countGreaterThan(integers, 3));
        System.out.println("Doubles > 2.0: " + countGreaterThan(doubles, 2.0));
        
        // This won't compile because String doesn't implement Comparable<String>
        // (it actually implements Comparable<String>, but that's not recognized by the wildcard)
        // System.out.println("Strings > 'b': " + countGreaterThan(strings, "b"));
        
        // 3. Solving the exchange puzzle
        System.out.println("\n3. Solving the exchange puzzle:");
        
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(3, 4));
        
        System.out.println("Before exchange: list1 = " + list1 + ", list2 = " + list2);
        exchange(list1, list2, 0, 0);
        System.out.println("After exchange: list1 = " + list1 + ", list2 = " + list2);
        
        // 4. Transformation example (mapping)
        System.out.println("\n4. Transformation example:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> lengths = transform(names, String::length);
        System.out.println("Names: " + names);
        System.out.println("Lengths: " + lengths);
        
        // 5. Filtering with generics
        System.out.println("\n5. Filtering with generics:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers = filter(numbers, n -> n % 2 == 0);
        System.out.println("Original numbers: " + numbers);
        System.out.println("Even numbers: " + evenNumbers);
        
        // Educational section
        System.out.println("\n*** WHY GENERIC METHODS ARE IMPORTANT ***");
        System.out.println("1. Type Safety: Enable compile-time type checking for flexible methods");
        System.out.println("2. Code Reuse: Write a single method that works with many types");
        System.out.println("3. API Design: Create methods that can be both flexible and type-safe");
        System.out.println("4. Self-contained: Type parameters scope is limited to the method");
        
        System.out.println("\n*** GENERIC METHOD VS. CLASS GENERICS ***");
        System.out.println("- Class generics: Type fixed for the entire instance");
        System.out.println("- Method generics: Type can change for each method call");
        
        System.out.println("\n*** COMMON PATTERNS ***");
        System.out.println("1. <T> T method(T arg): Method with type parameter matching parameter and return");
        System.out.println("2. <T> void method(List<T> list): Method that works with lists of any type");
        System.out.println("3. <T extends Comparable<T>> method(...): Method with bounded type parameter");
        System.out.println("4. <T, U> U method(T input, Function<T, U> function): Transformation pattern");
    }
    
    /**
     * Basic generic method that simply returns its argument.
     * The type parameter T is inferred from the argument type.
     * 
     * This demonstrates the simplest form of a generic method.
     */
    public static <T> T identity(T t) {
        return t;
    }
    
    /**
     * Generic method with bounded wildcard.
     * Counts elements in a list that are greater than a specified element.
     * 
     * This method shows how to use type bounds with generics - we need
     * the element type to be Comparable so we can use the compareTo method.
     */
    public static <T extends Comparable<T>> int countGreaterThan(List<T> list, T element) {
        int count = 0;
        for (T item : list) {
            if (item.compareTo(element) > 0) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Generic method that exchanges elements between two lists.
     * This demonstrates why wildcards are needed for certain operations.
     * 
     * The challenge: We want to exchange elements between two lists, but
     * we don't want to restrict them to having the exact same type.
     */
    public static void exchange(List<?> list1, List<?> list2, int pos1, int pos2) {
        // Won't compile with direct approach due to wildcard capture
        // Object temp = list1.get(pos1);
        // list1.set(pos1, list2.get(pos2));  // Error: can't set <?> from another list
        // list2.set(pos2, temp);             // Error: can't set <?> from Object
        
        // Solution: Use a helper method with type parameters
        exchangeHelper(list1, list2, pos1, pos2);
    }
    
    /**
     * Helper method for exchange that uses type parameters.
     * This demonstrates the "wildcard capture" technique to solve
     * issues with wildcards.
     */
    private static <T, U> void exchangeHelper(List<T> list1, List<U> list2, int pos1, int pos2) {
        T temp1 = list1.get(pos1);
        U temp2 = list2.get(pos2);
        
        // Now we know the actual types, so these operations are safe
        list1.set(pos1, (T) temp2); // Unchecked cast, but conceptually safe
        list2.set(pos2, (U) temp1); // Unchecked cast, but conceptually safe
    }
    
    /**
     * Generic method that transforms a list of one type to a list of another type.
     * This demonstrates how to use Function interface with generics.
     */
    public static <T, R> List<R> transform(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>(list.size());
        for (T element : list) {
            result.add(function.apply(element));
        }
        return result;
    }
    
    /**
     * Generic method that filters a list based on a predicate.
     * This demonstrates how to use Predicate interface with generics.
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T element : list) {
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }
}

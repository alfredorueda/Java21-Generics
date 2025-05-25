package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Demonstrates generic methods with wildcards for OCP Java 21 preparation.
 * 
 * Generic methods are one of the most powerful and flexible features of Java generics,
 * allowing type-safe operations on different types within the same class or method.
 * They can be defined in both generic and non-generic classes, and offer more
 * specialized type safety than is possible with class-level generics alone.
 * 
 * KEY CONCEPTS:
 * 1. Generic method type parameters are independent of class type parameters
 * 2. Type parameters for methods are declared before the return type
 * 3. Generic methods can use wildcards for increased flexibility
 * 4. Generic methods can often solve "wildcard capture" problems
 * 
 * MENTAL MODEL:
 * Think of generic methods as "type-specific mini-factories" within a class.
 * Each call to the method can work with different types, with the compiler
 * ensuring type safety for that specific invocation.
 * 
 * REAL-WORLD ANALOGY:
 * Imagine a universal remote control (the generic method) that can automatically
 * configure itself to work with whatever device you point it at (the type parameter).
 * When you point it at a TV, it becomes a TV remote; when you point it at a DVD player,
 * it becomes a DVD remote - all while ensuring you only press buttons that are valid
 * for that specific device.
 * 
 * OCP EXAM TIP:
 * The exam often tests your understanding of when to use generic methods vs.
 * class-level generics, and how to combine them with wildcards. Pay particular
 * attention to wildcard capture problems and their solutions.
 */
public class Main_GenericMethodWithWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Generic Methods with Wildcards ===");
        
        // 1. Basic generic method example
        // This demonstrates the simplest form of generic methods
        System.out.println("\n1. Basic generic method example:");
        
        // The method can work with any type - the type parameter is inferred
        // from the argument type automatically
        String result1 = identity("Hello");  // T is inferred as String
        Integer result2 = identity(42);     // T is inferred as Integer
        System.out.println("String identity: " + result1);
        System.out.println("Integer identity: " + result2);
        
        // 2. Generic methods with wildcards
        // This demonstrates combining generic methods with bounded type parameters
        System.out.println("\n2. Generic methods with wildcards:");
        
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
        List<String> strings = Arrays.asList("a", "b", "c");
        
        // Count elements greater than a value in different numeric lists
        // The bound <T extends Comparable<T>> ensures we can compare elements
        System.out.println("Integers > 3: " + countGreaterThan(integers, 3));
        System.out.println("Doubles > 2.0: " + countGreaterThan(doubles, 2.0));
        
        // The commented line below would cause a compile error:
        // System.out.println("Strings > 'b': " + countGreaterThan(strings, "b"));
        // This is because String doesn't precisely match Comparable<String>
        // (it actually implements Comparable<String>, but the wildcard constraint is too strict)
        
        // 3. Solving the exchange puzzle
        // This demonstrates the "wildcard capture" problem and its solution
        System.out.println("\n3. Solving the exchange puzzle:");
        
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(3, 4));
        
        System.out.println("Before exchange: list1 = " + list1 + ", list2 = " + list2);
        exchange(list1, list2, 0, 0);  // Exchanges elements between lists
        System.out.println("After exchange: list1 = " + list1 + ", list2 = " + list2);
        
        // 4. Transformation example (mapping)
        // This demonstrates using Function interface with generics for transformation
        System.out.println("\n4. Transformation example:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        // Transform strings to their lengths using a method reference
        List<Integer> lengths = transform(names, String::length);
        System.out.println("Names: " + names);
        System.out.println("Lengths: " + lengths);
        
        // 5. Filtering with generics
        // This demonstrates using Predicate interface with generics for filtering
        System.out.println("\n5. Filtering with generics:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // Filter to keep only even numbers using a lambda expression
        List<Integer> evenNumbers = filter(numbers, n -> n % 2 == 0);
        System.out.println("Original numbers: " + numbers);
        System.out.println("Even numbers: " + evenNumbers);
        
        // Educational section with deeper insights
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
     * 
     * SYNTAX BREAKDOWN:
     * - <T>: Declares a type parameter T for this method only
     * - T: Return type (same as the parameter type)
     * - identity: Method name
     * - (T t): Parameter of type T
     * 
     * TYPE INFERENCE:
     * When you call this method, the compiler automatically infers the type parameter
     * from the argument type, so you don't need to specify it explicitly.
     * 
     * PRACTICAL USAGE:
     * This pattern is used in the Java standard library in methods like
     * Optional.orElse() and in many functional programming contexts.
     * 
     * OCP EXAM TIP:
     * The exam may test your understanding of type inference with generic methods.
     * Remember that the compiler infers the most specific type that works for all arguments.
     * 
     * @param <T> The type of the input and output
     * @param t The value to return
     * @return The same value that was passed in
     */
    public static <T> T identity(T t) {
        return t;
    }
    
    /**
     * Generic method with bounded type parameter.
     * Counts elements in a list that are greater than a specified element.
     * 
     * BOUNDED TYPE PARAMETER:
     * <T extends Comparable<T>> restricts T to types that implement Comparable<T>,
     * which guarantees that we can call compareTo() on elements of type T.
     * 
     * LIMITATIONS:
     * This method has a restrictive bound that works for simple cases like
     * Integer and Double, but fails for types like String which technically
     * implements Comparable<String> but through inheritance.
     * 
     * IMPROVEMENT IDEA:
     * A more flexible version would use <T extends Comparable<? super T>>
     * to support types that implement Comparable through inheritance.
     * 
     * OCP EXAM TIP:
     * The exam often tests understanding of type bounds. Remember that
     * "extends" in generic contexts means "extends or implements".
     * 
     * @param <T> The type of elements in the list, must implement Comparable
     * @param list The list of elements to check
     * @param element The element to compare against
     * @return The count of elements greater than the specified element
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
     * 
     * WILDCARD CAPTURE PROBLEM:
     * This method demonstrates a common problem with wildcards.
     * We want to exchange elements between lists of potentially different types,
     * but using List<?> creates a "wildcard capture" issue - we can get elements
     * out as Objects, but we can't put elements in due to type safety.
     * 
     * SOLUTION APPROACH:
     * We use a helper method with explicit type parameters to "capture" the
     * actual types of the wildcards, allowing us to perform the exchange safely.
     * 
     * REAL-WORLD PARALLEL:
     * This pattern is used in the Collections.swap() method in the JDK.
     * 
     * OCP EXAM TIP:
     * Wildcard capture is a complex topic that appears on the exam.
     * Remember that helper methods with explicit type parameters can
     * often solve wildcard capture problems.
     * 
     * @param list1 The first list
     * @param list2 The second list
     * @param pos1 The position in the first list
     * @param pos2 The position in the second list
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
     * 
     * WILDCARD CAPTURE SOLUTION:
     * By using explicit type parameters T and U, we "capture" the actual types
     * of the wildcards from the calling method. This allows the compiler to
     * verify type safety while still allowing the exchange operation.
     * 
     * TYPE CASTING ISSUE:
     * This method uses unchecked casts, which are generally unsafe.
     * In this specific case, they're conceptually safe because we're
     * exchanging single elements between the lists, but the compiler
     * can't verify this.
     * 
     * PRACTICAL LIMITATION:
     * This approach works but may generate unchecked cast warnings.
     * In production code, you might want to use more type-safe approaches
     * or suppress warnings with @SuppressWarnings("unchecked").
     * 
     * @param <T> The type of elements in the first list
     * @param <U> The type of elements in the second list
     * @param list1 The first list
     * @param list2 The second list
     * @param pos1 The position in the first list
     * @param pos2 The position in the second list
     */
    private static <T, U> void exchangeHelper(List<T> list1, List<U> list2, int pos1, int pos2) {
        T temp1 = list1.get(pos1);
        U temp2 = list2.get(pos2);
        
        // Now we know the actual types, so these operations are safe
        // (though they still require casts that generate warnings)
        list1.set(pos1, (T) temp2); // Unchecked cast, but conceptually safe
        list2.set(pos2, (U) temp1); // Unchecked cast, but conceptually safe
    }
    
    /**
     * Generic method that transforms a list of one type to a list of another type.
     * 
     * FUNCTIONAL PROGRAMMING PATTERN:
     * This method demonstrates using Java's functional interfaces with generics.
     * The Function interface represents a transformation from type T to type R.
     * 
     * GENERIC TYPE FLOW:
     * - Input: List<T> - A list of elements of type T
     * - Transformation: Function<T, R> - Converts each T to an R
     * - Output: List<R> - A list of elements of type R
     * 
     * PRACTICAL APPLICATIONS:
     * This pattern is used extensively in functional programming and streams:
     * - list.stream().map(String::length).collect(Collectors.toList())
     * - is equivalent to our transform(list, String::length)
     * 
     * OCP EXAM TIP:
     * The exam may test your understanding of how generic type parameters
     * work with functional interfaces. Remember that the type parameters
     * in Function<T, R> correspond to the input and output types.
     * 
     * @param <T> The type of elements in the input list
     * @param <R> The type of elements in the output list
     * @param list The input list to transform
     * @param function The transformation function to apply
     * @return A new list containing the transformed elements
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
     * 
     * FUNCTIONAL PROGRAMMING PATTERN:
     * This method demonstrates using Java's Predicate functional interface
     * with generics to create a filter operation.
     * 
     * GENERIC TYPE FLOW:
     * - Input: List<T> - A list of elements of type T
     * - Filter: Predicate<T> - Tests each element for inclusion
     * - Output: List<T> - A filtered list of elements of type T
     * 
     * PRACTICAL APPLICATIONS:
     * This pattern is used extensively in functional programming and streams:
     * - list.stream().filter(n -> n % 2 == 0).collect(Collectors.toList())
     * - is equivalent to our filter(list, n -> n % 2 == 0)
     * 
     * OCP EXAM TIP:
     * The exam may test your understanding of functional interfaces with generics.
     * Remember that Predicate<T> represents a function that takes a T and returns a boolean.
     * 
     * @param <T> The type of elements in the list
     * @param list The list to filter
     * @param predicate The condition to test elements against
     * @return A new list containing only elements that satisfy the predicate
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

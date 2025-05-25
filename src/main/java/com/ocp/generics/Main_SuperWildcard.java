package com.ocp.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates lower-bounded wildcards (? super T) in Java Generics.
 * 
 * Lower-bounded wildcards (? super T) represent "any type that is T or a supertype of T"
 * and are used when:
 * 1. You need to WRITE elements of type T to a structure
 * 2. You want to work with a collection that can accept T or any of its subtypes
 * 
 * Key points for OCP Java 21:
 * - List<? super Integer> can be List<Integer>, List<Number>, List<Object>, etc.
 * - You can ADD elements of type T or its subtypes
 * - When READING, elements come out as Objects (not T)
 */
public class Main_SuperWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Lower-Bounded Wildcard (? super T) Examples ===\n");
        
        // Create lists of different types in the Integer hierarchy
        List<Integer> integerList = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();
        
        // Demonstrate assignment compatibility
        List<? super Integer> integerConsumer;
        integerConsumer = integerList;  // OK: Integer is Integer
        integerConsumer = numberList;   // OK: Number is a supertype of Integer
        integerConsumer = objectList;   // OK: Object is a supertype of Integer
        // List<? super Integer> cannotBeDouble = doubleList; // Error: Double is not a supertype of Integer
        
        // WRITING is allowed - can add Integer or any subtype of Integer
        System.out.println("Writing to List<? super Integer>:");
        integerConsumer.add(Integer.valueOf(10));
        integerConsumer.add(20);  // Autoboxing works too
        System.out.println("After adding integers: " + integerConsumer);
        
        // Cannot add other types that aren't subtypes of Integer
        // integerConsumer.add(Double.valueOf(5.5)); // Compilation error!
        
        // RESTRICTION: Reading is limited - elements come out as Objects
        System.out.println("\nRestrictions when reading from List<? super Integer>:");
        Object element = integerConsumer.get(0);
        System.out.println("Element read as Object: " + element);
        // Integer specificElement = integerConsumer.get(0); // Compilation error!
        
        // Demonstrate super wildcard with methods
        System.out.println("\nAdding elements to different lists:");
        addNumbers(integerList);
        System.out.println("Integer list: " + integerList);
        
        addNumbers(numberList);
        System.out.println("Number list: " + numberList);
        
        addNumbers(objectList);
        System.out.println("Object list: " + objectList);
        
        // Educational explanation
        System.out.println("\n*** WHY YOU CANNOT READ SPECIFIC TYPES FROM List<? super T> ***");
        System.out.println("When we have List<? super Integer>, the compiler only knows that");
        System.out.println("the list's type parameter is Integer or any supertype of Integer.");
        System.out.println("It could be List<Integer>, List<Number>, or even List<Object>.");
        System.out.println("Since we don't know exactly which supertype it is, we can only");
        System.out.println("safely read elements as Objects, not as any specific type.");
        
        // Examples in the real world
        System.out.println("\n*** WHEN TO USE ? super T ***");
        System.out.println("1. When your code needs to write to a collection");
        System.out.println("2. When implementing the 'Consumer' pattern (PECS principle)");
        System.out.println("3. When working with Comparators (they consume the type they compare)");
        System.out.println("4. When you need to add elements to a collection but don't need to read them as a specific type");
        
        // Working with captured wildcards
        System.out.println("\nIf you need to read and process elements from a List<? super T>:");
        processElements(numberList);
    }
    
    /**
     * This method accepts any List that can hold Integers or their supertypes.
     * It demonstrates writing to a list with a lower-bounded wildcard.
     * 
     * @param list A list of Integer or any supertype of Integer
     */
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
        
        // Cannot read as Integer
        // Integer first = list.get(0); // Compilation error!
        Object obj = list.get(0); // Can only read as Object
        System.out.println("First element (as Object): " + obj);
    }
    
    /**
     * This method demonstrates how to process elements from a list
     * with a lower-bounded wildcard using the instanceof operator.
     * 
     * @param list A list of Number or any supertype of Number
     */
    public static void processElements(List<? super Number> list) {
        // Add some elements
        list.add(Integer.valueOf(42));
        list.add(Double.valueOf(3.14));
        
        System.out.println("\nProcessing elements safely:");
        for (Object obj : list) {
            // Use instanceof to safely work with the elements
            if (obj instanceof Integer i) {
                System.out.println("  Integer: " + i + " squared = " + (i * i));
            } else if (obj instanceof Double d) {
                System.out.println("  Double: " + d + " squared = " + (d * d));
            } else {
                System.out.println("  Other type: " + obj);
            }
        }
    }
}

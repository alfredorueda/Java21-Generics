package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates lower-bounded wildcards (List<? super T>) in Java Generics.
 * 
 * Lower-bounded wildcards (? super T) represent "any type that is a supertype of T"
 * and are used when:
 * 1. You need to ADD elements of type T to a structure
 * 2. You want to write T or any subtype of T into a collection
 * 
 * Key concept: List<? super T> represents a CONSUMER of T values (WRITE-FRIENDLY)
 */
public class Main_SuperWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Lower-Bounded Wildcard (? super T) ===");
        
        // Creating lists of different Number types and supertypes
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        
        // This is valid - Number is a supertype of Integer
        List<? super Integer> integerConsumer = numbers;
        
        // WRITING is allowed - can add Integer or any subtype of Integer
        integerConsumer.add(Integer.valueOf(10));
        integerConsumer.add(42); // Autoboxing to Integer
        
        // Cannot add other types that aren't subtypes of Integer
        // integerConsumer.add(Double.valueOf(5.5)); // Compile error!
        
        // READING is limited - elements come out as Objects
        Object element = integerConsumer.get(0);
        System.out.println("First element as Object: " + element);
        // Integer specificElement = integerConsumer.get(0); // Compile error!
        
        // We can pass any list of Integer or Integer supertypes to a method with ? super Integer
        System.out.println("\nAdding integers to different lists:");
        System.out.println("Adding to List<Integer>:");
        addNumbers(integers);
        System.out.println("List content: " + integers);
        
        System.out.println("\nAdding to List<Number>:");
        addNumbers(numbers);
        System.out.println("List content: " + numbers);
        
        System.out.println("\nAdding to List<Object>:");
        addNumbers(objects);
        System.out.println("List content: " + objects);
        
        // *** WHY CAN'T WE READ SPECIFIC TYPES FROM A LIST<? super T>? ***
        System.out.println("\n*** WHY? The Lower-Bounded Wildcard Limitation ***");
        System.out.println("When we have List<? super Integer>, the compiler only knows that");
        System.out.println("the list's type parameter is Integer or any supertype of Integer.");
        System.out.println("It could be List<Integer>, List<Number>, or even List<Object>.");
        System.out.println("When reading, we don't know exactly which supertype we have, so");
        System.out.println("Java can only guarantee that the element is an Object.");
        
        // Example to illustrate:
        List<? super Integer> mysteryList = objects; // A List<Object> assigned to List<? super Integer>
        mysteryList.add(Integer.valueOf(999)); // Safe, since Integer is-a Object
        
        // When reading:
        Object obj = mysteryList.get(0); // We only know it's an Object
        // Integer value = mysteryList.get(0); // Compile error! Might not be an Integer
        
        // If we know more about the context, we can manually cast (but with caution)
        System.out.println("\nUsing instanceof for safer casting:");
        Object someElement = mysteryList.get(0);
        if (someElement instanceof Integer) {
            Integer intValue = (Integer) someElement;
            System.out.println("Retrieved Integer value: " + intValue);
        } else {
            System.out.println("Element is not an Integer, it's: " + someElement.getClass().getSimpleName());
        }
    }
    
    /**
     * This method accepts any List of Integer or Integer supertypes.
     * Note that we can add Integer objects to the list, but when reading,
     * we only get Objects.
     * 
     * This is perfect for a "consumer" scenario - the list consumes Integer values.
     */
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
        
        // Cannot read as Integer
        // Integer first = list.get(0); // Compile error!
        Object first = list.get(0); // Can only read as Object
    }
}

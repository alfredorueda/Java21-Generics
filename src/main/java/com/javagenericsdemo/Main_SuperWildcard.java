package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates lower-bounded wildcards (List<? super T>) in Java Generics for OCP Java 21 preparation.
 * 
 * Lower-bounded wildcards (? super T) represent "any type that is a supertype of T or T itself"
 * and follow the PECS principle: "Producer Extends, Consumer Super".
 * 
 * WHEN TO USE ? super T:
 * 1. When you need to ADD elements of type T (or subtypes) to a collection
 * 2. When your method needs to write to a collection rather than read from it
 * 3. When designing APIs that need to work with T objects but store them in various supertype collections
 * 
 * MENTAL MODEL:
 * Think of List<? super Integer> as a "basket that accepts Integers or stores them as some supertype".
 * You know you can safely put Integers in (because every supertype of Integer can hold an Integer),
 * but you don't know what exact type will come out when you look inside - you only know it's at least an Object.
 * 
 * REAL-WORLD ANALOGY:
 * Imagine a recycling system (consumer) that accepts plastic items. The system might 
 * sort them into different categories (plastic, recyclables, waste), but when you 
 * put in a plastic bottle, you don't need to know which specific category it will end up in.
 * 
 * OCP EXAM TIP:
 * Remember that List<? super T> represents a CONSUMER of T values (WRITE-FRIENDLY collections).
 * The compiler allows adding elements of type T or any subtype of T, but when reading,
 * you can only get Object references without explicit casting.
 * Look for questions that test your understanding of why reading specific types from a List<? super T> is restricted.
 */
public class Main_SuperWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Lower-Bounded Wildcard (? super T) ===");
        
        // Creating lists of different Number types and supertypes
        // Notice the hierarchy: Object > Number > Integer
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        
        // This is valid - Number is a supertype of Integer
        // This is the key aspect of "Consumer Super" - the list will consume Integer values
        List<? super Integer> integerConsumer = numbers;
        
        // WRITING is allowed - can add Integer or any subtype of Integer
        // This is safe because any supertype of Integer must be able to hold an Integer
        integerConsumer.add(Integer.valueOf(10));
        integerConsumer.add(42); // Autoboxing to Integer
        
        // Cannot add other types that aren't subtypes of Integer
        // The commented line below would cause a compile error:
        // integerConsumer.add(Double.valueOf(5.5)); // Compile error!
        
        // READING is limited - elements come out as Objects
        // This is because we only know the list contains "some supertype of Integer"
        Object element = integerConsumer.get(0);
        System.out.println("First element as Object: " + element);
        // The commented line below would cause a compile error:
        // Integer specificElement = integerConsumer.get(0); // Compile error!
        
        // We can pass any list of Integer or Integer supertypes to a method with ? super Integer
        // This demonstrates the flexibility of using super wildcard for writing/adding
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
        // The commented line below would cause a compile error:
        // Integer value = mysteryList.get(0); // Compile error! Might not be an Integer
        
        // If we know more about the context, we can manually cast (but with caution)
        System.out.println("\nUsing instanceof for safer casting:");
        Object someElement = mysteryList.get(0);
        if (someElement instanceof Integer intValue) {
            // Safe to cast since we checked the type
            System.out.println("Retrieved Integer value: " + intValue);
        } else {
            System.out.println("Element is not an Integer, it's: " + someElement.getClass().getSimpleName());
        }
    }
    
    /**
     * This method accepts any List of Integer or Integer supertypes.
     * 
     * PECS Principle in action: The list is a CONSUMER of Integer values that we add,
     * so we use "super" (Consumer Super).
     * 
     * OCP EXAM TIP: Notice that with List<? super Integer>:
     * 1. We CAN add Integer objects (or subtypes if Integer had any)
     * 2. We CANNOT read elements as Integer (only as Object)
     * 3. This pattern is ideal for methods that need to populate collections
     * 
     * PRACTICAL USAGE:
     * This pattern is commonly used in methods that need to write to collections,
     * such as copy operations, collection utilities, or algorithms that generate values.
     * 
     * For example, Collections.copy() uses this pattern to copy elements from a
     * source collection to a destination collection.
     * 
     * @param list A list of any type that is a supertype of Integer (Integer, Number, Object)
     */
    public static void addNumbers(List<? super Integer> list) {
        // We can safely add Integers to the list
        // This works because any supertype of Integer must be able to hold an Integer
        list.add(1);
        list.add(2);
        list.add(3);
        
        // Cannot read as Integer - the commented line below would cause a compile error
        // Integer first = list.get(0); // Compile error!
        
        // Can only read as Object - this is the trade-off for the flexibility of writing
        Object first = list.get(0); // We lose type information when reading
    }
}

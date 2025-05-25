package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates upper-bounded wildcards (List<? extends T>) in Java Generics for OCP Java 21 preparation.
 * 
 * Upper-bounded wildcards (? extends T) represent "any type that is a subtype of T or T itself"
 * and follow the PECS principle: "Producer Extends, Consumer Super".
 * 
 * WHEN TO USE ? extends T:
 * 1. When you need to GET elements from a structure and process them as type T
 * 2. When your method only needs to read from the collection, not modify it
 * 3. When designing APIs that need to accept various subtypes of a class
 * 
 * MENTAL MODEL:
 * Think of List<? extends Number> as a "box of unknown Number subtype" - you know
 * that whatever is inside extends Number, but you don't know exactly what type.
 * You can take things out (they're guaranteed to be Numbers), but you can't put
 * things in (because you don't know the specific type requirements).
 * 
 * REAL-WORLD ANALOGY:
 * Imagine a vending machine (producer) that dispenses different types of snacks. 
 * You know you'll get some kind of snack, but you don't know exactly which type until
 * you retrieve it. You can't put snacks back in through the dispenser slot.
 * 
 * OCP EXAM TIP:
 * Remember that List<? extends T> represents a PRODUCER of T values (READ-ONLY collections).
 * The compiler prevents adding elements (except null) to ensure type safety.
 * Look for questions that test your understanding of why you can't add to a List<? extends T>.
 */
public class Main_ExtendsWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Upper-Bounded Wildcard (? extends T) ===");
        
        // Creating lists of different Number subtypes
        // Each list is parameterized with a specific subtype of Number
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numbers = new ArrayList<>(Arrays.asList(10, 20.5, 30L)); // Mixed Number types
        
        // This is valid - Integer is a subtype of Number
        // This is the key aspect of "Producer Extends" - the list will produce Number values
        List<? extends Number> numberProducer = integers;
        
        // READING is allowed - elements come out as the upper bound type (Number)
        // The compiler guarantees that whatever we get is at least a Number
        Number firstNumber = numberProducer.get(0);
        System.out.println("First number: " + firstNumber);
        
        // WRITING is NOT allowed (except null)
        // The commented lines below would cause compile errors:
        // numberProducer.add(Integer.valueOf(4)); // Compile error!
        // numberProducer.add(Double.valueOf(5.5)); // Compile error!
        numberProducer.add(null); // Only null is allowed because null is compatible with any reference type
        
        // We can pass any list of Number or Number subtypes to a method with ? extends Number
        // This demonstrates the flexibility of using extends wildcard for reading/processing
        System.out.println("\nSum of integers: " + sumNumbers(integers));
        System.out.println("Sum of doubles: " + sumNumbers(doubles));
        System.out.println("Sum of mixed numbers: " + sumNumbers(numbers));
        
        // *** WHY CAN'T WE ADD TO A LIST<? extends T>? ***
        System.out.println("\n*** WHY? The Upper-Bounded Wildcard Limitation ***");
        System.out.println("When we have List<? extends Number>, the compiler only knows that");
        System.out.println("the list contains some type that extends Number, but not exactly which type.");
        System.out.println("It could be List<Integer>, List<Double>, or List<CustomNumber>.");
        
        // Example of what would happen if it were allowed (which it isn't):
        List<? extends Number> mysteryList = doubles; // A List<Double> assigned to List<? extends Number>
        // If this were allowed (it's not):
        // mysteryList.add(Integer.valueOf(10)); // Would add an Integer to a List<Double>!
        // This would break type safety, causing ClassCastException at runtime
        
        // Commonly used for read operations in APIs
        printNumberDetails(integers);
        printNumberDetails(doubles);
    }
    
    /**
     * This method accepts any List of Number or Number subtypes.
     * 
     * PECS Principle in action: The list is a PRODUCER of Number values that we read,
     * so we use "extends" (Producer Extends).
     * 
     * OCP EXAM TIP: Notice that even though we can't add to the list, we can still:
     * 1. Read from it (get elements)
     * 2. Process those elements using methods defined in the upper bound (Number)
     * 3. Iterate over it using the enhanced for loop
     * 
     * @param numbers A list of any type that extends Number (Integer, Double, etc.)
     * @return The sum of all numbers in the list, converted to double
     */
    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number number : numbers) {
            if (number != null) {
                sum += number.doubleValue(); // Safe to call Number methods
                // This works because we know all elements are at least Number type
            }
        }
        return sum;
    }
    
    /**
     * Another example of using List<? extends Number> as a producer.
     * 
     * This method demonstrates how to safely work with collections of unknown
     * but bounded types. We can read values and use them as their upper bound type.
     * 
     * PRACTICAL USAGE:
     * This pattern is commonly used in APIs where you want to accept collections
     * of various subtypes. For example, a charting library might accept
     * List<? extends Number> to plot data from various number types.
     * 
     * @param numbers A list of any Number subtype
     */
    public static void printNumberDetails(List<? extends Number> numbers) {
        // First check if the first element is null before using it
        // This defensive programming is important when working with potentially null elements
        if (!numbers.isEmpty() && numbers.get(0) != null) {
            System.out.println("\nNumber details for " + numbers.get(0).getClass().getSimpleName() + " list:");
        } else {
            System.out.println("\nNumber details for list:");
        }
        
        for (Number number : numbers) {
            if (number != null) {
                System.out.println(" - Value: " + number + 
                                 ", Integer part: " + number.intValue() + 
                                 ", Double part: " + number.doubleValue());
                // Notice we can safely call Number methods (intValue, doubleValue) 
                // on each element because of the upper bound guarantee
            } else {
                System.out.println(" - Value: null");
            }
        }
    }
}

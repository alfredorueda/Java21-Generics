package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates upper-bounded wildcards (List<? extends T>) in Java Generics.
 * 
 * Upper-bounded wildcards (? extends T) represent "any type that is a subtype of T"
 * and are used when:
 * 1. You need to GET elements from a structure and process them as type T or above
 * 2. You want to accept a collection of any subtype of T
 * 
 * Key concept: List<? extends T> represents a PRODUCER of T values (READ-ONLY)
 */
public class Main_ExtendsWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Upper-Bounded Wildcard (? extends T) ===");
        
        // Creating lists of different Number subtypes
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubles = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numbers = new ArrayList<>(Arrays.asList(10, 20.5, 30L));
        
        // This is valid - Integer is a subtype of Number
        List<? extends Number> numberProducer = integers;
        
        // READING is allowed - elements come out as the upper bound type (Number)
        Number firstNumber = numberProducer.get(0);
        System.out.println("First number: " + firstNumber);
        
        // WRITING is NOT allowed (except null)
        // numberProducer.add(Integer.valueOf(4)); // Compile error!
        // numberProducer.add(Double.valueOf(5.5)); // Compile error!
        numberProducer.add(null); // Only null is allowed
        
        // We can pass any list of Number or Number subtypes to a method with ? extends Number
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
     * Note that we can only read from the list, not add to it.
     * This is perfect for a "producer" scenario - the list produces Number values.
     */
    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number number : numbers) {
            if (number != null) {
                sum += number.doubleValue(); // Safe to call Number methods
            }
        }
        return sum;
    }
    
    /**
     * Another example of using List<? extends Number> as a producer.
     */
    public static void printNumberDetails(List<? extends Number> numbers) {
        // First check if the first element is null before using it
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
            } else {
                System.out.println(" - Value: null");
            }
        }
    }
}

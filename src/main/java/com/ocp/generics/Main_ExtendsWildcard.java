package com.ocp.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates upper-bounded wildcards (? extends T) in Java Generics.
 * 
 * Upper-bounded wildcards (? extends T) represent "any type that is T or a subtype of T"
 * and are used when:
 * 1. You need to READ elements from a structure as type T
 * 2. You want to accept a collection of any subtype of T
 * 
 * Key points for OCP Java 21:
 * - List<? extends Number> can hold List<Integer>, List<Double>, List<Number>, etc.
 * - You can READ elements (they come out as the upper bound type - Number in this case)
 * - You CANNOT add elements (except null), even if they're of the upper bound type
 */
public class Main_ExtendsWildcard {
    
    public static void main(String[] args) {
        System.out.println("=== Upper-Bounded Wildcard (? extends T) Examples ===\n");
        
        // Create lists of different Number subtypes
        List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Double> doubleList = new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3));
        List<Number> numberList = new ArrayList<>(Arrays.asList(10, 20.5, 30L));
        
        // Demonstrate assignment compatibility
        List<? extends Number> numberView;
        numberView = intList;      // OK: Integer extends Number
        // numberView = stringList; // Error: String doesn't extend Number
        
        // READING is allowed - elements come out as the upper bound type
        System.out.println("Reading from List<? extends Number>:");
        Number firstNumber = numberView.get(0);  // Safe - we know it's at least a Number
        System.out.println("First number: " + firstNumber);
        
        // RESTRICTION: Cannot add elements (except null)
        System.out.println("\nRestrictions with List<? extends Number>:");
        // numberView.add(Integer.valueOf(42)); // Compilation error!
        // numberView.add(Double.valueOf(42.0)); // Compilation error!
        // numberView.add(new Number() {...});   // Compilation error!
        
        // Only null is allowed
        numberView.add(null);
        System.out.println("After adding null: " + numberView);
        
        // Demonstrate using extends wildcard with methods
        System.out.println("\nCalculating sum with ? extends Number:");
        System.out.println("Sum of integers: " + sumNumbers(intList));
        System.out.println("Sum of doubles: " + sumNumbers(doubleList));
        System.out.println("Sum of mixed numbers: " + sumNumbers(numberList));
        
        // Educational explanation
        System.out.println("\n*** WHY YOU CANNOT ADD TO List<? extends T> ***");
        System.out.println("When we have List<? extends Number>, the compiler only knows that");
        System.out.println("the list contains some type that extends Number, but not exactly which type.");
        System.out.println("It could be List<Integer>, List<Double>, or List<MyCustomNumber>.");
        System.out.println("If Java allowed us to add an Integer to what might be a List<Double>,");
        System.out.println("it would break type safety. So to protect type safety, Java disallows adding elements.");
        
        // Examples in the real world
        System.out.println("\n*** WHEN TO USE ? extends T ***");
        System.out.println("1. When your code only needs to read from a collection");
        System.out.println("2. When you want to accept a collection of any subtype of T");
        System.out.println("3. When implementing the 'Producer' pattern (PECS principle)");
        System.out.println("4. When you need to extract elements and use their methods from type T");
        
        // Demonstrate using Number methods
        System.out.println("\nUsing Number methods with elements from different lists:");
        printNumberDetails(intList);
        printNumberDetails(doubleList);
    }
    
    /**
     * This method accepts any List of Number or Number subtypes.
     * It demonstrates reading from a list with an upper-bounded wildcard.
     * 
     * @param numbers A list of Number or any subtype of Number
     * @return The sum of all numbers in the list
     */
    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue(); // Safe to call Number methods
        }
        return sum;
    }
    
    /**
     * This method demonstrates accessing Number methods from elements
     * in a list with an upper-bounded wildcard.
     * 
     * @param numbers A list of Number or any subtype of Number
     */
    public static void printNumberDetails(List<? extends Number> numbers) {
        if (numbers.isEmpty()) return;
        
        System.out.println("\nDetails for " + numbers.get(0).getClass().getSimpleName() + " list:");
        for (Number number : numbers) {
            System.out.println("  Value: " + number + 
                             ", Int: " + number.intValue() + 
                             ", Double: " + number.doubleValue());
        }
    }
}

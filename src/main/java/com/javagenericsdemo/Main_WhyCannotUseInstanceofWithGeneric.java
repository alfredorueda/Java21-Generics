package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates why you cannot use instanceof with generic types in Java for OCP Java 21 preparation.
 * 
 * This limitation is directly tied to type erasure, one of the fundamental
 * aspects of Java's implementation of generics. Understanding this constraint is
 * critical for designing robust generic APIs and for passing the OCP Java 21 exam.
 * 
 * CORE CONCEPT:
 * Due to type erasure, all generic type information is removed at compile time.
 * At runtime, a List<String> and a List<Integer> are both simply List objects
 * with no trace of their type parameters. Since instanceof is a runtime operator,
 * it cannot distinguish between different parameterized types.
 * 
 * MENTAL MODEL:
 * Think of generic type parameters as "compile-time tags" that get stripped
 * away before the code runs. The instanceof operator can only see what exists
 * at runtime, and the type parameters don't exist at that point.
 * 
 * REAL-WORLD ANALOGY:
 * Imagine different types of vehicles (cars, trucks, motorcycles) all coming
 * off an assembly line with color-coded tags. Once they leave the factory,
 * these tags are removed. Later, if you try to identify them based on these
 * missing tags, you won't be able to - you can only identify them as "vehicles"
 * or by other permanent characteristics.
 * 
 * OCP EXAM TIP:
 * This topic frequently appears on the OCP exam in the following forms:
 * - Questions about what code will/won't compile with instanceof and generics
 * - Questions about runtime type checking of generic types
 * - Understanding alternative approaches to runtime type identification
 * - Recognizing the implications of type erasure in various scenarios
 */
public class Main_WhyCannotUseInstanceofWithGeneric {
    
    public static void main(String[] args) {
        System.out.println("=== Why You Cannot Use instanceof with Generic Types ===");
        
        // Create various generic objects to demonstrate the concept
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        Map<String, Integer> stringIntMap = new HashMap<>();
        
        // 1. Basic instanceof behavior
        // This section shows what IS allowed with instanceof
        System.out.println("\n1. Basic instanceof behavior:");
        
        System.out.println("stringList instanceof List: " + (stringList instanceof List));
        System.out.println("stringList instanceof ArrayList: " + (stringList instanceof ArrayList));
        System.out.println("stringIntMap instanceof Map: " + (stringIntMap instanceof Map));
        
        // 2. The problem with generic types and instanceof
        // This section demonstrates what is NOT allowed and why
        System.out.println("\n2. Why instanceof doesn't work with specific generic types:");
        
        // These won't compile - the compiler will report an error about illegal generic type for instanceof
        // System.out.println(stringList instanceof List<String>); // Compile error!
        // System.out.println(integerList instanceof List<Integer>); // Compile error!
        
        System.out.println("Due to type erasure, at runtime:");
        System.out.println("- List<String> becomes just List");
        System.out.println("- List<Integer> becomes just List");
        System.out.println("- Map<String, Integer> becomes just Map");
        
        // Demonstrating that generic type information is erased
        // This shows concrete evidence of type erasure
        System.out.println("\nType erasure in action:");
        System.out.println("stringList class: " + stringList.getClass().getName());
        System.out.println("integerList class: " + integerList.getClass().getName());
        System.out.println("Are they the same class? " + (stringList.getClass() == integerList.getClass()));
        
        // 3. Workarounds for checking element types
        // This section shows practical alternatives to instanceof with generics
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
        // This section clarifies a common misconception
        System.out.println("\n4. Using instanceof with wildcards (raw types):");
        
        System.out.println("stringList instanceof List<?> is valid in the code, but...");
        System.out.println("...at runtime it's just checking if stringList instanceof List");
        
        // This is the same as checking "instanceof List" due to erasure
        // The wildcard is also erased at runtime
        boolean isListWildcard = stringList instanceof List<?>;
        System.out.println("stringList instanceof List<?>: " + isListWildcard);
        
        // 5. Practical example of the problem
        // This demonstrates why this limitation matters in real code
        System.out.println("\n5. Practical example of why this matters:");
        
        Object obj1 = stringList;
        Object obj2 = integerList;
        
        System.out.println("Both objects are Lists: " + (obj1 instanceof List && obj2 instanceof List));
        System.out.println("But we can't tell at runtime if they're List<String> or List<Integer>");
        
        System.out.println("\nWhen writing generic code, we have to rely on other mechanisms");
        System.out.println("to ensure type safety, such as bounded type parameters and wildcards.");
        
        // Educational section with deeper insights
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
     * 
     * DESIGN PATTERN:
     * This implements the "Type Token" pattern, a common workaround for the
     * limitations of type erasure when runtime type information is needed.
     * 
     * PRACTICAL APPLICATIONS:
     * This pattern is used extensively in Java libraries that need runtime type information:
     * - Jackson/GSON for JSON serialization/deserialization
     * - Spring Framework for dependency injection
     * - JPA implementations for entity mapping
     * 
     * HOW IT WORKS:
     * By explicitly storing the Class<T> object (which does exist at runtime),
     * we retain the type information that would otherwise be erased.
     * 
     * OCP EXAM TIP:
     * The exam might test your understanding of common workarounds for
     * type erasure limitations. This Class<T> type token pattern is one
     * of the most important to understand.
     * 
     * @param <T> The type we want to check for
     */
    static class GenericTypeChecker<T> {
        private Class<T> type;
        
        /**
         * Constructor that takes a Class object representing the type T.
         * 
         * This is how we preserve type information that would otherwise be erased.
         * 
         * @param type The Class object representing type T
         */
        public GenericTypeChecker(Class<T> type) {
            this.type = type;
        }
        
        /**
         * Checks if the first element in the list is of type T.
         * 
         * This method demonstrates how to perform a runtime type check
         * on elements in a generic collection when the collection's
         * generic type parameter is not available.
         * 
         * NOTE:
         * This approach has limitations:
         * 1. It requires a non-empty list
         * 2. It only checks the first element (assumes homogeneous list)
         * 3. It doesn't guarantee all elements are of type T
         * 
         * JAVA 16+ ALTERNATIVE:
         * With pattern matching for instanceof (Java 16+), the check inside this 
         * method could be written more elegantly if we were checking specific types.
         * 
         * @param list A list of unknown element type
         * @return true if the first element is of type T, false otherwise
         */
        public boolean checkListType(List<?> list) {
            if (list.isEmpty()) {
                return false; // Can't determine type of empty list
            }
            
            // Check if the first element is of the expected type
            // isInstance() is the dynamic equivalent of the instanceof operator
            return type.isInstance(list.get(0));
        }
    }
}

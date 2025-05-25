package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates a key concept for OCP Java 21 preparation: the critical difference between
 * {@code List<Object>} and {@code List<?>} and why unbounded wildcards are essential.
 * 
 * <p>This class provides a concrete, hands-on demonstration of why {@code List<String>}
 * cannot be assigned to a {@code List<Object>} variable, but can be assigned to
 * a {@code List<?>} variable. This distinction is fundamental to understanding
 * Java's type system and is frequently tested on the OCP exam.</p>
 * 
 * <p><strong>Key Concept:</strong> In Java generics, {@code List<String>} is NOT a subtype of 
 * {@code List<Object>} even though String is a subtype of Object. This is called
 * "invariance" in generics, and it's designed to prevent runtime type errors.</p>
 * 
 * <p><strong>Mental Model:</strong> Think of a {@code List<Object>} as a list that can have ANY
 * object added to it, while a {@code List<String>} can only have strings added.
 * If Java allowed {@code List<String>} to be treated as a {@code List<Object>},
 * you could add non-String objects to a String list, breaking type safety.</p>
 * 
 * <p><strong>Solution:</strong> The {@code List<?>} (unbounded wildcard) solves this problem by
 * creating a "read-only view" of the list. You can read elements (as Objects),
 * but you cannot add elements (except null) because the actual type is unknown.</p>
 */
public class Main_UnboundedWildcardPitfall {

    public static void main(String[] args) {
        System.out.println("=== Understanding the Difference Between List<Object> and List<?> ===\n");
        
        // Create a list of strings
        List<String> stringList = new ArrayList<>(Arrays.asList("Java", "Generics", "Wildcard"));
        System.out.println("Original list of strings: " + stringList);
        
        // ==================== PART 1: The Problem ====================
        System.out.println("\n=== PART 1: The Problem - Why List<String> cannot be assigned to List<Object> ===");
        
        // This WILL NOT COMPILE - Java prevents this to ensure type safety
        // List<Object> objectList = stringList; // Compile error: incompatible types

        System.out.println("If Java allowed: List<Object> objectList = stringList;");
        System.out.println("Then we could do this:");
        System.out.println("  objectList.add(new Integer(42)); // Adding an Integer to what is actually a String list!");
        System.out.println("  String s = stringList.get(3);    // Runtime ClassCastException when retrieving the Integer as String");
        
        // Let's try to pass our stringList to a method expecting List<Object>
        System.out.println("\nAttempting to call printObjectList(stringList):");
        System.out.println("  This won't compile - Java protects us from this unsafe operation");
        
        // This line would cause a compile error:
        // printObjectList(stringList); // Compile error: incompatible types
        
        // Let's simulate what would happen if Java allowed it (which it doesn't)
        System.out.println("\nIf Java allowed passing List<String> to a method accepting List<Object>:");
        simulateUnsafeOperation();
        
        // ==================== PART 2: The Solution ====================
        System.out.println("\n=== PART 2: The Solution - Using List<?> (Unbounded Wildcard) ===");
        
        // This WILL COMPILE - an unbounded wildcard accepts any parameterized list
        List<?> wildcardList = stringList;
        System.out.println("This works: List<?> wildcardList = stringList;");
        
        // We can safely read from the wildcardList (elements come out as Objects)
        System.out.println("Reading from wildcardList: " + wildcardList.get(0) + " (type: " + 
                           wildcardList.get(0).getClass().getSimpleName() + ")");
        
        // But we CANNOT add elements (except null)
        System.out.println("\nWith List<?>, we can't add elements (except null):");
        System.out.println("  wildcardList.add(\"New String\"); // Won't compile - can't add to List<?>"); 
        System.out.println("  wildcardList.add(new Integer(42)); // Won't compile - can't add to List<?>");
        
        // Only null can be added to a List<?>
        wildcardList.add(null);
        System.out.println("  wildcardList.add(null); // This works - null is compatible with any type");
        System.out.println("List after adding null: " + wildcardList);
        
        // Now let's try passing our stringList to a method accepting List<?>
        System.out.println("\nCalling printWildcardList(stringList):");
        printWildcardList(stringList); // This works!
        
        // We can also pass other types of lists to the same method
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        System.out.println("\nCalling printWildcardList(integerList):");
        printWildcardList(integerList); // This also works!
        
        // ==================== PART 3: The Difference in Practice ====================
        System.out.println("\n=== PART 3: The Difference in Practice ===");
        
        List<Object> realObjectList = new ArrayList<>();
        realObjectList.add("A String");
        realObjectList.add(Integer.valueOf(42));
        realObjectList.add(Double.valueOf(3.14));
        
        System.out.println("A real List<Object> can contain mixed types: " + realObjectList);
        System.out.println("We can add anything to it:");
        System.out.println("  realObjectList.add(\"Another String\"); // Works");
        System.out.println("  realObjectList.add(new Exception()); // Works");
        
        System.out.println("\nA List<?> provides a read-only view of any list:");
        List<?> anotherWildcardList = integerList;
        System.out.println("  Reading: " + anotherWildcardList.get(0) + " (type: " + 
                          anotherWildcardList.get(0).getClass().getSimpleName() + ")");
        System.out.println("  But we can't add to it (except null)");
        
        // ==================== PART 4: Educational Summary ====================
        System.out.println("\n=== PART 4: Key Takeaways ===");
        System.out.println("1. List<String> is NOT a subtype of List<Object>, even though String is a subtype of Object");
        System.out.println("2. This invariance prevents type errors at runtime by enforcing type safety at compile time");
        System.out.println("3. List<?> (unbounded wildcard) creates a read-only view that accepts any parameterized list");
        System.out.println("4. Use List<?> when you only need to read from a list and don't care about the specific type");
        System.out.println("5. The Java compiler prevents operations that could lead to ClassCastException at runtime");
    }
    
    /**
     * This method expects a List<Object> parameter.
     * It demonstrates why methods that accept List<Object> cannot accept List<String>.
     * 
     * @param list a list of any objects
     */
    public static void printObjectList(List<Object> list) {
        System.out.println("Inside printObjectList method: " + list);
        
        // We can add ANY object to a List<Object>
        list.add(Integer.valueOf(42));
        list.add(new Exception("This is an exception"));
        
        System.out.println("After adding objects: " + list);
    }
    
    /**
     * This method uses an unbounded wildcard parameter List<?>.
     * It demonstrates why List<?> can accept any parameterized list type.
     * 
     * @param list a list of any type (using unbounded wildcard)
     */
    public static void printWildcardList(List<?> list) {
        System.out.println("Inside printWildcardList method: " + list);
        
        // We can read from the list (elements come out as Objects)
        if (!list.isEmpty()) {
            Object first = list.get(0);
            System.out.println("First element: " + first + " (type: " + first.getClass().getSimpleName() + ")");
        }
        
        // But we CANNOT add elements (except null) - these would cause compile errors:
        // list.add("Some string"); // Won't compile
        // list.add(new Object()); // Won't compile
        
        // Only null can be added
        // list.add(null); // This would work, but we won't do it in this demo
        
        // We can still perform operations that don't involve adding elements
        System.out.println("List size: " + list.size());
        System.out.println("List is empty? " + list.isEmpty());
    }
    
    /**
     * This method simulates what would happen if Java allowed List<String> to be
     * treated as List<Object> (which it doesn't, for good reason).
     * 
     * We create a contrived example to demonstrate the potential runtime issues.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void simulateUnsafeOperation() {
        // Create a list of strings
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        
        // Using raw type to bypass generic type checking (DON'T DO THIS IN REAL CODE!)
        List rawList = stringList;
        
        // Add a non-String to the list
        rawList.add(Integer.valueOf(42));
        
        System.out.println("After unsafe operation, list contains: " + stringList);
        System.out.println("  stringList contains a String and an Integer!");
        
        // Now try to use the list as a list of strings (which will fail)
        try {
            // This will throw ClassCastException at runtime
            for (String s : stringList) {
                System.out.println("String length: " + s.length());
            }
        } catch (ClassCastException e) {
            System.out.println("  ERROR: " + e.getMessage());
            System.out.println("  This is exactly what Java's type system prevents!");
        }
    }
}
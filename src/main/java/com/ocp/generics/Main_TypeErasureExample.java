package com.ocp.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates type erasure in Java Generics.
 * 
 * Type erasure is the process by which the Java compiler removes generic type information
 * after performing compile-time type checking. After compilation:
 * - Unbounded type parameters (<T>) are replaced with Object
 * - Bounded type parameters (<T extends Bound>) are replaced with the bound
 * 
 * Key points for OCP Java 21:
 * - Generic type information is only available at compile time, not runtime
 * - You cannot create arrays of generic types
 * - You cannot use instanceof with generic types
 * - You cannot overload methods that would have the same erasure
 * - Bridge methods are generated to maintain type safety with inheritance
 */
public class Main_TypeErasureExample {
    
    public static void main(String[] args) {
        System.out.println("=== Type Erasure in Generics Examples ===\n");
        
        // 1. Runtime type information is lost
        System.out.println("1. Runtime Type Information Loss:");
        
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        
        stringList.add("Hello");
        integerList.add(42);
        
        // Both have the same runtime class!
        System.out.println("stringList runtime class: " + stringList.getClass().getName());
        System.out.println("integerList runtime class: " + integerList.getClass().getName());
        System.out.println("Are classes equal? " + (stringList.getClass() == integerList.getClass()));
        
        // 2. Method overloading limitations
        System.out.println("\n2. Method Overloading Limitations:");
        
        // We can only have one of these methods due to type erasure
        System.out.println("We can process strings: ");
        processElements(stringList);
        
        System.out.println("We can process integers with a different method name: ");
        processIntegers(integerList);
        
        // 3. Workarounds for type erasure
        System.out.println("\n3. Type Information Workarounds:");
        
        // Using a wrapper class to preserve type information
        Box<Integer> intBox = new Box<>(42, Integer.class);
        Box<String> stringBox = new Box<>("Hello", String.class);
        
        System.out.println("intBox contains: " + intBox.get() + 
                         " of type: " + intBox.getTypeClass().getSimpleName());
        System.out.println("stringBox contains: " + stringBox.get() + 
                         " of type: " + stringBox.getTypeClass().getSimpleName());
        
        // 4. Bridge methods
        System.out.println("\n4. Bridge Methods Example:");
        
        // Bridge methods are synthetic methods created by the compiler
        Node<Integer> intNode = new IntNode(1);
        Node<String> stringNode = new StringNode("Hello");
        
        System.out.println("intNode data: " + intNode.getData());
        System.out.println("stringNode data: " + stringNode.getData());
        
        // Type erasure consequences
        System.out.println("\n*** WHY TYPE ERASURE EXISTS ***");
        System.out.println("Type erasure was introduced for backward compatibility with");
        System.out.println("pre-Java 5 code. It allows generic code to interoperate with");
        System.out.println("legacy code that doesn't understand generics.");
        
        System.out.println("\n*** LIMITATIONS CAUSED BY TYPE ERASURE ***");
        System.out.println("1. Cannot create arrays of generic types");
        System.out.println("   Wrong: new T[10]; // Compile error!");
        System.out.println("   Right: (T[]) new Object[10]; // Unchecked cast warning");
        
        System.out.println("\n2. Cannot use instanceof with generic types");
        System.out.println("   Wrong: obj instanceof List<String>; // Compile error!");
        System.out.println("   Right: obj instanceof List; // Raw type check only");
        
        System.out.println("\n3. Cannot overload methods with same erasure");
        System.out.println("   void process(List<String>) and void process(List<Integer>)");
        System.out.println("   both become void process(List) after erasure");
        
        System.out.println("\n4. Cannot create instances of type parameters");
        System.out.println("   Wrong: new T(); // Compile error!");
        System.out.println("   Right: Pass a Class<T> or factory method");
    }
    
    /**
     * This method processes a list of strings.
     * Due to type erasure, we cannot have another method with the same name
     * that takes List<Integer> because they would have the same erasure.
     * 
     * @param elements A list of strings
     */
    public static void processElements(List<String> elements) {
        System.out.println("Processing strings: " + elements);
    }
    
    /**
     * This method processes a list of integers.
     * We need a different name because we can't overload based on the generic type.
     * 
     * @param elements A list of integers
     */
    public static void processIntegers(List<Integer> elements) {
        System.out.println("Processing integers: " + elements);
    }
    
    /**
     * This class retains type information at runtime by storing the Class object.
     * This is a common workaround for type erasure.
     */
    static class Box<T> {
        private T value;
        private Class<T> typeClass;
        
        public Box(T value, Class<T> typeClass) {
            this.value = value;
            this.typeClass = typeClass;
        }
        
        public T get() {
            return value;
        }
        
        public Class<T> getTypeClass() {
            return typeClass;
        }
    }
    
    /**
     * Base class to demonstrate bridge methods.
     */
    static class Node<T> {
        protected T data;
        
        public Node(T data) {
            this.data = data;
        }
        
        public T getData() {
            return data;
        }
    }
    
    /**
     * Subclass with Integer type.
     * After erasure, Node.getData() returns Object, but IntNode.getData()
     * returns Integer. The compiler creates a bridge method to handle this.
     */
    static class IntNode extends Node<Integer> {
        public IntNode(Integer data) {
            super(data);
        }
        
        @Override
        public Integer getData() {
            System.out.println("IntNode's getData() called");
            return data;
        }
    }
    
    /**
     * Subclass with String type.
     */
    static class StringNode extends Node<String> {
        public StringNode(String data) {
            super(data);
        }
        
        @Override
        public String getData() {
            System.out.println("StringNode's getData() called");
            return data;
        }
    }
}

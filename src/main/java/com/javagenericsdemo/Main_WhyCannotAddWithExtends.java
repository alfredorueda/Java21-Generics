package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates in detail why you cannot add elements to a List<? extends T>.
 * 
 * This is one of the most confusing aspects of generics for beginners.
 * Understanding the reason helps clarify how type safety works in generics.
 */
public class Main_WhyCannotAddWithExtends {
    
    public static void main(String[] args) {
        System.out.println("=== Why You Cannot Add Elements to List<? extends T> ===");
        
        // Setup example class hierarchy
        System.out.println("\nClass Hierarchy for our example:");
        System.out.println("          Animal");
        System.out.println("         /      \\");
        System.out.println("    Mammal      Bird");
        System.out.println("    /    \\");
        System.out.println("  Dog    Cat");
        
        // Create lists of different animal types
        List<Dog> dogs = new ArrayList<>(Arrays.asList(new Dog("Fido"), new Dog("Rex")));
        List<Cat> cats = new ArrayList<>(Arrays.asList(new Cat("Whiskers"), new Cat("Fluffy")));
        List<Mammal> mammals = new ArrayList<>(Arrays.asList(new Dog("Spot"), new Cat("Mittens")));
        List<Animal> animals = new ArrayList<>(Arrays.asList(new Dog("Rover"), new Bird("Tweety")));
        
        // First, demonstrate what works - Reading from List<? extends Animal>
        System.out.println("\n1. Reading from List<? extends Animal> (works fine):");
        
        printAnimals(dogs);   // List<Dog> is acceptable
        printAnimals(cats);   // List<Cat> is acceptable
        printAnimals(mammals); // List<Mammal> is acceptable
        printAnimals(animals); // List<Animal> is acceptable
        
        // Now, demonstrate why adding doesn't work
        System.out.println("\n2. Why Adding to List<? extends Animal> Doesn't Work:");
        
        // Assign a List<Dog> to a List<? extends Animal> reference
        List<? extends Animal> someAnimals = dogs;
        
        // Let's try adding to it
        // someAnimals.add(new Dog("Bruno")); // Compile error!
        // someAnimals.add(new Cat("Smokey")); // Compile error!
        // someAnimals.add(new Animal("Generic")); // Compile error!
        
        System.out.println("None of the add operations compile! Here's why:");
        System.out.println(" - The variable someAnimals could be pointing to a List<Dog>, List<Cat>,");
        System.out.println("   List<Bird>, or any other List<? extends Animal>.");
        System.out.println(" - If it's a List<Dog>, then adding a Cat would break type safety.");
        System.out.println(" - If it's a List<Cat>, then adding a Dog would break type safety.");
        System.out.println(" - Even adding an Animal isn't safe because we don't know the specific type.");
        
        // The runtime visualization of the problem
        System.out.println("\n3. Visualizing the Problem:");
        System.out.println("Current someAnimals reference points to: " + someAnimals.getClass().getSimpleName());
        System.out.println("Contains: " + someAnimals);
        
        System.out.println("\nIf we could do this (which we can't):");
        System.out.println("someAnimals.add(new Cat(\"Tabby\"));");
        
        System.out.println("\nThen this would happen:");
        System.out.println("Dog dog = dogs.get(2); // ClassCastException: Cat cannot be cast to Dog");
        
        // Simulating with a raw type (don't do this in real code!)
        System.out.println("\n4. Simulating the Problem with Raw Types (bad practice):");
        
        @SuppressWarnings("rawtypes")
        List rawDogs = dogs; // Raw type - no generic type checking
        
        @SuppressWarnings("unchecked")
        boolean added = rawDogs.add(new Cat("Simba")); // This compiles but is UNSAFE!
        
        System.out.println("Added a Cat to dogs list using raw type: " + added);
        System.out.println("Current dogs list: " + dogs);
        System.out.println("Trying to process dogs normally now:");
        
        try {
            processDogs(dogs);
        } catch (ClassCastException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("This is exactly what the compiler prevents with proper generic usage!");
        }
        
        // Solution: Use the correct bounded wildcard
        System.out.println("\n5. Solution: Use the Right Tool for the Job");
        System.out.println("- To READ from a collection: List<? extends T> (Producer)");
        System.out.println("- To WRITE to a collection: List<? super T> (Consumer)");
        System.out.println("- To both READ and WRITE: List<T> (Exact type)");
        
        // Demonstration of proper approach
        List<Animal> animalList = new ArrayList<>();
        addMammals(animalList); // Using super bounded wildcard for adding
        System.out.println("\nAdded mammals to animal list: " + animalList);
    }
    
    /**
     * This method can READ from any list of Animals or their subtypes.
     * It demonstrates the "Producer Extends" part of PECS.
     */
    public static void printAnimals(List<? extends Animal> animals) {
        System.out.println("Animals in list: ");
        for (Animal animal : animals) {
            System.out.println(" - " + animal);
        }
    }
    
    /**
     * This method processes dogs specifically.
     * It will fail at runtime if the list contains non-Dog objects.
     */
    public static void processDogs(List<Dog> dogs) {
        for (Dog dog : dogs) {
            dog.bark();
        }
    }
    
    /**
     * This method can ADD Mammals to any list that accepts Mammals or their supertypes.
     * It demonstrates the "Consumer Super" part of PECS.
     */
    public static void addMammals(List<? super Mammal> list) {
        list.add(new Dog("Buddy"));
        list.add(new Cat("Felix"));
        // list.add(new Bird("Polly")); // Compile error! Bird is not a Mammal
    }
    
    // Class hierarchy for demonstration
    
    static class Animal {
        private String name;
        
        public Animal(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" + name + ")";
        }
    }
    
    static class Mammal extends Animal {
        public Mammal(String name) {
            super(name);
        }
    }
    
    static class Bird extends Animal {
        public Bird(String name) {
            super(name);
        }
    }
    
    static class Dog extends Mammal {
        public Dog(String name) {
            super(name);
        }
        
        public void bark() {
            System.out.println(this + " says: Woof!");
        }
    }
    
    static class Cat extends Mammal {
        public Cat(String name) {
            super(name);
        }
        
        public void meow() {
            System.out.println(this + " says: Meow!");
        }
    }
}
